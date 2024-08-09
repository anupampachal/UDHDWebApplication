package com.scsinfinity.udhd.services.base;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.storage.FileFolderPermissionTypeEnum;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderPermissionEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupPermissionEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.storage.IFolderRepository;
import com.scsinfinity.udhd.exceptions.StorageException;
import com.scsinfinity.udhd.services.base.dto.FolderDTO;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

@Service
@Transactional
public class FolderService implements IFolderService {

	@Value("${scs.setting.folder.createDBFolderIfExists}")
	private String createFolderInDBIfPhysicalFolderExistsAndDBMissing;

	private final IFolderRepository folderRepository;

	public FolderService(IFolderRepository folderRepository) {
		this.folderRepository = folderRepository;
	}

	@Override
	@Transactional
	public FolderDTO createUpdateFolder(FolderEntity folder, FolderUserGroupsEnum currentUserGroup) throws Exception {
		Optional<FolderEntity> parentFolderO = folderRepository.findById(folder.getParent().getId());
		// file handling
		try {

			if (!parentFolderO.isPresent()) {
				throw new Exception("ParentFolderNotPresent");
			} else {
				FolderEntity parentFolder = parentFolderO.get();

				FolderPermissionEntity permission = parentFolder.getFolderPermission();

				if (!checkPermission(permission, currentUserGroup, FileFolderPermissionTypeEnum.WRITE)) {
					throw new Exception("UserPermissionDenied");
				}
				if (folder.getPathToCurrentDir() == null) {

					Path pathToNewFolder = Paths.get(parentFolder.getPathToCurrentDir(), folder.getName());

					folder.setPathToCurrentDir(pathToNewFolder.toString());
					folder.setParent(parentFolder);
				}

				if (Files.notExists(Paths.get(folder.getPathToCurrentDir()))) {
					Files.createDirectories(Paths.get(folder.getPathToCurrentDir()));
					folder = folderRepository.saveAndFlush(folder);
				} else {
					if (Boolean.parseBoolean(createFolderInDBIfPhysicalFolderExistsAndDBMissing))
						folder = folderRepository.saveAndFlush(folder);
					throw new Exception("FolderExists");
				}

			}

		}

		catch (Exception e) {

			switch (e.getMessage()) {
			case "UserPermissionDenied":
				throw new StorageException("Permission Denied");
			case "ParentFolderNotPresent":
				throw new StorageException("ParentFolderNotPresent");
			case "FolderExists":
				throw new StorageException("FolderExists");
			default:
				throw new StorageException("InitializeStorageFailed");
			}

		}

		// db handling
		return folder.getDTO();

	}

	@Override
	public FolderDTO getFolderByFolderId(String folderID, FolderUserGroupsEnum currentUserGroup) throws Exception {

		FolderEntity folder = folderRepository.findByFolderIdIgnoreCase(folderID)
				.orElseThrow(EntityNotFoundException::new);

		FolderPermissionEntity permission = folder.getFolderPermission();

		if (!checkPermission(permission, currentUserGroup, FileFolderPermissionTypeEnum.WRITE)) {
			throw new Exception("UserPermissionDenied");
		}
		return folder.getDTO();
	}

	@Override
	public List<FolderDTO> getChildFolders(String parentFolderID, FolderUserGroupsEnum currentUserInGroup)
			throws EntityNotFoundException, Exception {
		List<FolderDTO> folders = folderRepository.findByParent_FolderIdIgnoreCase(parentFolderID).stream()
				.map(folder -> folder.getDTO()).collect(Collectors.toList());

		FolderEntity folder = folderRepository.findByFolderIdIgnoreCase(parentFolderID)
				.orElseThrow(EntityNotFoundException::new);
		FolderPermissionEntity permission = folder.getFolderPermission();

		if (!checkPermission(permission, currentUserInGroup, FileFolderPermissionTypeEnum.WRITE)) {
			throw new Exception("UserPermissionDenied");
		}

		return folders;
	}

	@Override
	public FolderDTO getFolderByNickname(String foldernickname, FolderUserGroupsEnum currentUserInGroup)
			throws Exception {
		FolderEntity folder = folderRepository.findByFolderNickNameIgnoreCase(foldernickname)
				.orElseThrow(() -> new EntityNotFoundException("folder not found" + foldernickname));

		FolderPermissionEntity permission = folder.getFolderPermission();

		Boolean isAllowed = checkPermission(permission, currentUserInGroup, FileFolderPermissionTypeEnum.WRITE);
		if (!isAllowed) {
			throw new Exception("UserPermissionDenied");
		}
		return folder.getDTO();
	}
	@Override
	public FolderEntity getFolderEntityByNickname(String foldernickname, FolderUserGroupsEnum currentUserInGroup)
			throws Exception {
		FolderEntity folder = folderRepository.findByFolderNickNameIgnoreCase(foldernickname)
				.orElseThrow(() -> new EntityNotFoundException("folder not found" + foldernickname));

		FolderPermissionEntity permission = folder.getFolderPermission();

		Boolean isAllowed = checkPermission(permission, currentUserInGroup, FileFolderPermissionTypeEnum.WRITE);
		if (!isAllowed) {
			throw new Exception("UserPermissionDenied");
		}
		return folder;
	}
	

	@Override
	public FolderEntity getFolderEntityByFolderId(String folderID, FolderUserGroupsEnum currentUserInGroup)
			throws Exception {

		FolderEntity folder = folderRepository.findByFolderIdIgnoreCase(folderID)
				.orElseThrow(() -> new EntityNotFoundException("folder not found" + folderID));

		FolderPermissionEntity permission = folder.getFolderPermission();

		if (!checkPermission(permission, currentUserInGroup, FileFolderPermissionTypeEnum.WRITE)) {
			throw new Exception("UserPermissionDenied");
		}
		return folderRepository.findByFolderIdIgnoreCase(folderID).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Optional<FolderEntity> getRootDir(FolderUserGroupsEnum currentUserInGroup) throws Exception {
		Optional<FolderEntity> folderRO = folderRepository.findByParent_IdIsNull();

		FolderPermissionEntity permission = folderRO.get().getFolderPermission();

		if (!checkPermission(permission, currentUserInGroup, FileFolderPermissionTypeEnum.WRITE)) {
			throw new Exception("UserPermissionDenied");
		}
		return folderRO;
	}

	@Override
	public Boolean checkPermission(FolderPermissionEntity permission, FolderUserGroupsEnum currentUserGroup,
			FileFolderPermissionTypeEnum permissionType) {
		List<FolderUserGroupPermissionEntity> userGroupBasedPermissions = permission.getFolderUserGroupPermissions();
		Optional<FolderUserGroupPermissionEntity> folderUsrGrpPerm = userGroupBasedPermissions.stream()
				.filter(usg -> usg.getFolderUserGroup().equals(currentUserGroup)).findAny();
		if (!folderUsrGrpPerm.isPresent())
			return false;

		List<FileFolderPermissionTypeEnum> permissions = folderUsrGrpPerm.get().getPermissions();
		return permissions.contains(permissionType);
	}

}
