package com.scsinfinity.udhd.services.base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileFolderPermissionTypeEnum;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderPermissionEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupPermissionEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.storage.IFileRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.exceptions.StorageException;
import com.scsinfinity.udhd.services.base.dto.FileDTO;
import com.scsinfinity.udhd.services.base.dto.FileMultipartDTO;
import com.scsinfinity.udhd.services.base.dto.FolderDTO;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

@Service
@Transactional
public class FileService implements IFileService {

	@Value("${scs.setting.file.override}")
	private String allowFileOverride;

	private final IFolderService folderService;
	private final IFileRepository fileRepository;
	private final SecuredUserInfoService securedUserService;
	private final RandomGeneratorService randomGeneratorService;

	public FileService(IFolderService folderService, IFileRepository fileRepository,
			SecuredUserInfoService securedUserService, RandomGeneratorService randomGeneratorService) {
		super();
		this.folderService = folderService;
		this.fileRepository = fileRepository;
		this.securedUserService = securedUserService;
		this.randomGeneratorService = randomGeneratorService;
	}

	@Override
	public FileDTO getFileDBInfoByID(String fileId) throws EntityNotFoundException, BadRequestAlertException {
		FileEntity file = fileRepository.findByFileId(fileId).orElseThrow(EntityNotFoundException::new);

		if (evaluateIfActionAllowed(file.getParentFolder(), FileFolderPermissionTypeEnum.READ)) {
			return file.getDTO();
		} else {
			throw new BadRequestAlertException("File read permission invalid", "File", fileId);
		}
	}

	@Override
	public FileEntity saveDTOFile(FileDTO file, FolderEntity parentFolder) {

		if (evaluateIfActionAllowed(parentFolder, FileFolderPermissionTypeEnum.READ)) {
			return fileRepository.save(FileEntity.builder().file(file.getFile()).fileId(file.getFileId())
					.fileServerName(file.getFileServerName()).name(file.getName()).parentFolder(parentFolder).build());
		} else {
			throw new BadRequestAlertException("File read permission invalid", "File", file.getFileId());
		}
	}

	@Override
	public FileDTO saveFile(MultipartFile file, String parentFolderId) {
		FileEntity savedFile = null;
		try {
			savedFile = saveFileAndReturnEntity(file, parentFolderId);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		if (savedFile != null)
			return savedFile.getDTO();
		return null;

	}

	@Override
	public FileEntity saveFileAndReturnEntity(MultipartFile file, String parentFolderId) {
		FileEntity savedFile = null;
		try {
			FolderUserGroupsEnum currentUserGroup = getCurrentUserGroup();

			FolderEntity folderToUploadTo = folderService.getFolderEntityByFolderId(parentFolderId.toString(),
					currentUserGroup);
			if (!evaluateIfActionAllowed(folderToUploadTo, FileFolderPermissionTypeEnum.WRITE))
				throw new StorageException("NOT PERMITTED FOR THIS ACTION");

			if (file.isEmpty())
				throw new StorageException("FAILED TO STORE EMPTY FILE");

			if (file.getName().contains(".."))
				throw new StorageException("Cannot store file with relative path outside current directory");

			String extension = FilenameUtils.getExtension(file.getOriginalFilename());

			InputStream inputStream = file.getInputStream();

			/*
			 * FileDTO fileDto = FileDTO.builder().file(file.getFile()) .fileServerName(
			 * "File_" + randomGeneratorService.generateRandomNumberForFilename() + "." +
			 * extension) .fileId(randomGeneratorService.generateRandomNumberForFilename())
			 * .name(file.getFile().getOriginalFilename()).parentFolderId(folderToUploadTo.
			 * getFolderId()).build();
			 */

			FileEntity fileToSave = FileEntity.builder().file(file)
					.fileId(randomGeneratorService.generateRandomNumberForFilename())
					.fileServerName(
							"File_" + randomGeneratorService.generateRandomNumberForFilename() + "." + extension)
					.extension(extension).name(file.getOriginalFilename()).parentFolder(folderToUploadTo).build();

			Path pathForStorage = Paths.get(folderToUploadTo.getPathToCurrentDir());
			Path originalPath = pathForStorage.resolve(fileToSave.getFileServerName());

			Boolean overrideIfFileExists = Boolean.parseBoolean(allowFileOverride);

			if (!(overrideIfFileExists || !Files.exists(originalPath))) {
				throw new FileAlreadyExistsException("File exists");

			}
			Files.copy(inputStream, originalPath, StandardCopyOption.REPLACE_EXISTING);

			savedFile = fileRepository.saveAndFlush(fileToSave);

		} catch (StorageException e) {
			e.printStackTrace();
		} catch (EntityNotFoundException e) {

		} catch (IOException io) {
			io.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (savedFile != null) {
			return savedFile;
		} else
			return null;
	}

	@Override
	public Resource getFile(String fileId) throws Exception {
		Resource resource = null;
		try {
			FileEntity file = fileRepository.findByFileId(fileId).orElseThrow(EntityNotFoundException::new);

			if (!evaluateIfActionAllowed(file.getParentFolder(), FileFolderPermissionTypeEnum.READ))
				throw new StorageException("NOT PERMITTED FOR THIS ACTION");

			Path pathForStorage = Paths.get(file.getParentFolder().getPathToCurrentDir());
			Path originalPath = pathForStorage.resolve(file.getFileServerName());
			resource = new UrlResource(originalPath.toUri());

			if (!resource.exists() || !resource.isReadable())
				throw new StorageException("File doesn't exist physically or is not readable");

			return resource;

		} catch (EntityNotFoundException en) {
			en.printStackTrace();
		} catch (StorageException st) {
			st.printStackTrace();
		}

		return resource;
	}

	@Override
	public Boolean deleteFileById(String fileId) throws Exception {
		Boolean isDeleted = false;
		try {
			FileEntity fileFromRepo = fileRepository.findByFileId(fileId).orElseThrow(EntityNotFoundException::new);
			if (!evaluateIfActionAllowed(fileFromRepo.getParentFolder(), FileFolderPermissionTypeEnum.WRITE))
				throw new StorageException("NOT PERMITTED FOR THIS ACTION");

			Path pathForStorage = Paths.get(fileFromRepo.getParentFolder().getPathToCurrentDir());
			Path originalPath = pathForStorage.resolve(fileFromRepo.getFileServerName());
			isDeleted = Files.deleteIfExists(originalPath);
			fileRepository.delete(fileFromRepo);

		} catch (EntityNotFoundException en) {
			en.printStackTrace();
		} catch (StorageException st) {
			st.printStackTrace();
		}

		return isDeleted;

	}

	@SuppressWarnings("unused")
	@Override
	public FileEntity saveFileWithMultipartAndParentNickNameOrIdAndEntity(FileMultipartDTO multipartDTO)
			throws Exception {
		FolderDTO folderToUploadTo = null;
		if (multipartDTO.getParentFolderId() != null) {
			folderToUploadTo = folderService.getFolderByFolderId(multipartDTO.getParentFolderId(),
					getCurrentUserGroup());
		} else if (multipartDTO.getParentFolderNickname() != null) {
			folderToUploadTo = folderService.getFolderByNickname(multipartDTO.getParentFolderNickname(),
					getCurrentUserGroup());
		} else {
			throw new BadRequestAlertException("Folder to upload to, is missing in request", "File",
					"FOLDER_TO_UPLOAD_MISSING");
		}

		return saveFileAndReturnEntity(multipartDTO.getFile(), folderToUploadTo.getFolderId());
	}

	@Override
	public FileDTO saveFileWithMultipartAndParentNickNameOrId(FileMultipartDTO multipartDTO) throws Exception {
		FolderDTO folderToUploadTo = null;

		if (multipartDTO.getParentFolderId() != null) {
			folderToUploadTo = folderService.getFolderByFolderId(multipartDTO.getParentFolderId(),
					getCurrentUserGroup());
		} else if (multipartDTO.getParentFolderNickname() != null) {
			folderToUploadTo = folderService.getFolderByNickname(multipartDTO.getParentFolderNickname(),
					getCurrentUserGroup());
		} else {
			throw new BadRequestAlertException("Folder to upload to, is missing in request", "File",
					"FOLDER_TO_UPLOAD_MISSING");
		}

		return saveFile(multipartDTO.getFile(), folderToUploadTo.getFolderId());

	}

	private Boolean evaluateIfActionAllowed(FolderEntity folder, FileFolderPermissionTypeEnum action) {
		FolderUserGroupsEnum currentUserGroup = getCurrentUserGroup();

		FolderPermissionEntity folderPermission = folder.getFolderPermission();
		List<FolderUserGroupPermissionEntity> folderUserGroupPermissions = folderPermission
				.getFolderUserGroupPermissions();

		List<FileFolderPermissionTypeEnum> permissions = folderUserGroupPermissions.stream()
				.filter(folderPerm -> folderPerm.getFolderUserGroup().equals(currentUserGroup))
				.map(fug -> fug.getPermissions()).flatMap(List::stream).collect(Collectors.toList());
		return permissions.contains(action);
	}

	@Override
	public FolderUserGroupsEnum getCurrentUserGroup() {
		try {
			UserEntity user = securedUserService.getCurrentUserInfo();
			if (user != null && user.getEnabled()) {
				return FolderUserGroupsEnum.AUTHENTICATED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return FolderUserGroupsEnum.UNAUTHENTICATED;
		}
		return FolderUserGroupsEnum.UNAUTHENTICATED;
	}

	@Override
	public FileDTO getFileDTO(FileEntity file) {
		return FileDTO.builder().fileId(file.getFileId()).fileServerName(file.getFileServerName()).name(file.getName())
				.parentFolderId(file.getParentFolder().getFolderId()).build();
	}

	@Override
	public FileEntity getEntity(FileDTO file, MultipartFile fileM) throws EntityNotFoundException, Exception {
		FolderEntity folderToUploadTo = folderService.getFolderEntityByFolderId(file.getParentFolderId(),
				getCurrentUserGroup());
		return FileEntity.builder().file(fileM).fileId(file.getFileId()).fileServerName(file.getFileServerName())
				.name(file.getName()).parentFolder(folderToUploadTo).build();
	}

	@Override
	public FileEntity getFileEntityById(String fileId) throws EntityNotFoundException, BadRequestAlertException {
		FileEntity file = fileRepository.findByFileId(fileId).orElseThrow(EntityNotFoundException::new);

		if (evaluateIfActionAllowed(file.getParentFolder(), FileFolderPermissionTypeEnum.READ)) {
			return file;
		} else {
			throw new BadRequestAlertException("File read permission invalid", "File", fileId);
		}

	}

	@Override
	public FileEntity copyFile(FileEntity fileEntity, String nameToAddToFileOriginalName, String parentFolderId)
			throws IOException {
		Path pathForStorage = Paths.get(fileEntity.getParentFolder().getPathToCurrentDir());
		Path originalPath = pathForStorage.resolve(fileEntity.getFileServerName());
		String fileServerNameForResponseFile="File_" + randomGeneratorService.generateRandomNumberForFilename() + "."
				+ fileEntity.getExtension();
		Path copiedPath = pathForStorage
				.resolve(fileServerNameForResponseFile);
		try {
			FolderEntity parentFolder = folderService.getFolderEntityByFolderId(parentFolderId, getCurrentUserGroup());
			Files.copy(originalPath, copiedPath, StandardCopyOption.REPLACE_EXISTING);
			FileEntity fileToSave = FileEntity.builder()
					.fileId(randomGeneratorService.generateRandomNumberForFilename())
					.fileServerName(fileServerNameForResponseFile)
					.name(FilenameUtils.removeExtension(fileEntity.getName()).concat("_response").concat(".")
							.concat(fileEntity.getExtension()))
					.parentFolder(parentFolder).extension(fileEntity.getExtension()).build();
			return fileRepository.save(fileToSave);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
