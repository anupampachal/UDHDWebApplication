package com.scsinfinity.udhd.configurations.dbinitializers;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import com.scsinfinity.udhd.dao.entities.storage.FileFolderPermissionTypeEnum;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderPermissionEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupPermissionEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.storage.IFolderPermissionRepository;
import com.scsinfinity.udhd.dao.repositories.storage.IFolderRepository;
import com.scsinfinity.udhd.exceptions.StorageException;
import com.scsinfinity.udhd.services.base.RandomGeneratorService;
import com.scsinfinity.udhd.services.base.dto.FolderDTO;

@Component
@Transactional
public class FolderInitialiazer {
	@Value("${scs.setting.folder.root.name}")
	private String rootDirName;

	@Value("${scs.setting.folder.root.nickname}")
	private String rootNickName;

	@Value("${scs.setting.folder.reports.name}")
	private String reportDirName;

	@Value("${scs.setting.folder.reports.nickname}")
	private String reportNickName;

	@Value("${scs.setting.folder.logoAndImages.name}")
	private String logoAndImageFolderName;

	@Value("${scs.setting.folder.logoAndImages.nickname}")
	private String logoAndImageNickName;

	@Value("${scs.setting.folder.internalDocs.name}")
	private String internalDocsFolderName;

	@Value("${scs.setting.folder.internalDocs.nickname}")
	private String internalDocsFolderNickName;

	@Value("${scs.setting.folder.externalDocs.name}")
	private String externalDocsFolderName;

	@Value("${scs.setting.folder.externalDocs.nickname}")
	private String externalDocsFolderNickName;

	@Value("${scs.setting.folder.profile.name}")
	private String profilePicFolderName;

	@Value("${scs.setting.folder.profile.nickname}")
	private String profilePicNickName;

	@Value("${scs.setting.folder.deas-data.name}")
	private String deasFolderName;

	@Value("${scs.setting.folder.deas-data.nickname}")
	private String deasFolderNickname;

	@Value("${scs.setting.folder.ia-data.name}")
	private String iaFolderName;

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String iaFolderNickName;

	@Value("${scs.setting.folder.createDBFolderIfExists}")
	private String createFolderInDBIfPhysicalFolderExistsAndDBMissing;

	private final RandomGeneratorService randomGeneratorService;
	// private final IFolderService folderService;
	private final IFolderRepository folderRepo;
	private final IFolderPermissionRepository folderPerm;

	public FolderInitialiazer(RandomGeneratorService randomGeneratorService,
			// IFolderService folderService,
			IFolderRepository folderRepo, IFolderPermissionRepository folderPerm) {
		super();
		// this.folderService = folderService;
		this.randomGeneratorService = randomGeneratorService;
		this.folderRepo = folderRepo;
		this.folderPerm = folderPerm;
	}

	public void intializeStorage() throws Exception {
		// deleteAllRecursively();

		initializeInitialFolders();

	}

	private void initializeInitialFolders() {

		try {

			List<FileFolderPermissionTypeEnum> readPerm = new ArrayList<>();
			List<FileFolderPermissionTypeEnum> writePerm = new ArrayList<>();
			List<FileFolderPermissionTypeEnum> readWritePerm = new ArrayList<>();

			readPerm.add(FileFolderPermissionTypeEnum.READ);
			writePerm.add(FileFolderPermissionTypeEnum.WRITE);
			readWritePerm.add(FileFolderPermissionTypeEnum.READ);
			readWritePerm.add(FileFolderPermissionTypeEnum.WRITE);

			// root
			FolderUserGroupPermissionEntity folderUserGroupPermissionsForRoot = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readWritePerm).build();
			List<FolderUserGroupPermissionEntity> rootFolderUserGroupPerm = new ArrayList<>();
			rootFolderUserGroupPerm.add(folderUserGroupPermissionsForRoot);
			FolderPermissionEntity rootAuthenticatedPermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(rootFolderUserGroupPerm).build();
			FolderEntity rootE = FolderEntity.builder().folderNickName(rootNickName).name(rootDirName).parent(null)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).pathToCurrentDir(rootDirName)
					.folderPermission(rootAuthenticatedPermission).build();

			rootAuthenticatedPermission.setFolder(rootE);
			rootE = createRootFolder(rootE);
			folderPerm.save(rootAuthenticatedPermission);

			// Logo and Images
			Path pathTologoAndImages = Paths.get(rootE.getPathToCurrentDir(), logoAndImageFolderName);

			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForLogo = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readPerm).build();
			FolderUserGroupPermissionEntity folderUnAuthenitcatedUserGroupPermissionsForLogo = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.UNAUTHENTICATED).permissions(readPerm).build();

			List<FolderUserGroupPermissionEntity> logoUserGroupPermission = new ArrayList<>();
			logoUserGroupPermission.addAll(Arrays.asList(folderAuthenitcatedUserGroupPermissionsForLogo,
					folderUnAuthenitcatedUserGroupPermissionsForLogo));

			FolderPermissionEntity logoPermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(logoUserGroupPermission).build();

			FolderEntity logoAndImages = FolderEntity.builder().folderNickName(logoAndImageNickName)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).name(logoAndImageFolderName)
					.parent(rootE).pathToCurrentDir(pathTologoAndImages.toString()).folderPermission(logoPermission)
					.build();
			logoPermission.setFolder(logoAndImages);
			logoAndImages = createFolder(logoAndImages);
			folderPerm.save(logoPermission);

			// profilePic
			Path pathToProfilePics = Paths.get(rootE.getPathToCurrentDir(), profilePicFolderName);
			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForProfile = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readWritePerm).build();
			//FolderUserGroupPermissionEntity folderUnAuthenitcatedUserGroupPermissionsForProfile = FolderUserGroupPermissionEntity
			//		.builder().folderUserGroup(FolderUserGroupsEnum.UNAUTHENTICATED).permissions(readPerm).build();

			List<FolderUserGroupPermissionEntity> profileUserGroupPermission = new ArrayList<>();
			profileUserGroupPermission.addAll(Arrays.asList(folderAuthenitcatedUserGroupPermissionsForProfile
					//,folderUnAuthenitcatedUserGroupPermissionsForProfile
					));
			FolderPermissionEntity profilePermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(profileUserGroupPermission).build();
			FolderEntity profileImagesFolder = FolderEntity.builder().folderNickName(profilePicNickName)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).name(profilePicFolderName)
					.parent(rootE).pathToCurrentDir(pathToProfilePics.toString()).folderPermission(profilePermission)
					.build();
			profilePermission.setFolder(profileImagesFolder);
			profileImagesFolder = createFolder(profileImagesFolder);
			folderPerm.save(profilePermission);

			// external docs
			Path pathToExternalDocs = Paths.get(rootE.getPathToCurrentDir(), externalDocsFolderName);
			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForExternalDocs = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readPerm).build();
			FolderUserGroupPermissionEntity folderUnAuthenitcatedUserGroupPermissionsForExternalDocs = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.UNAUTHENTICATED).permissions(readPerm).build();

			List<FolderUserGroupPermissionEntity> externalDocsUserGroupPermission = new ArrayList<>();
			externalDocsUserGroupPermission.addAll(Arrays.asList(folderAuthenitcatedUserGroupPermissionsForExternalDocs,
					folderUnAuthenitcatedUserGroupPermissionsForExternalDocs));

			FolderPermissionEntity externalDocsPermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(externalDocsUserGroupPermission).build();

			FolderEntity externalDocsFolder = FolderEntity.builder().folderNickName(externalDocsFolderNickName)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).name(externalDocsFolderName)
					.parent(rootE).pathToCurrentDir(pathToExternalDocs.toString())
					.folderPermission(externalDocsPermission).build();

			externalDocsPermission.setFolder(externalDocsFolder);
			externalDocsFolder = createFolder(externalDocsFolder);
			folderPerm.save(externalDocsPermission);

			// internal docs
			Path pathToInternalDocs = Paths.get(rootE.getPathToCurrentDir(), internalDocsFolderName);
			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForInternalDocs = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readPerm).build();

			List<FolderUserGroupPermissionEntity> internalDocsUserGroupPermission = new ArrayList<>();
			internalDocsUserGroupPermission
					.addAll(Arrays.asList(folderAuthenitcatedUserGroupPermissionsForInternalDocs));

			FolderPermissionEntity internalDocsPermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(internalDocsUserGroupPermission).build();

			FolderEntity internalDocsFolder = FolderEntity.builder().folderNickName(internalDocsFolderNickName)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).name(internalDocsFolderName)
					.parent(rootE).pathToCurrentDir(pathToInternalDocs.toString())
					.folderPermission(internalDocsPermission).build();

			internalDocsPermission.setFolder(internalDocsFolder);
			internalDocsFolder = createFolder(internalDocsFolder);
			folderPerm.save(internalDocsPermission);

			// report folder
			Path pathToReportFolder = Paths.get(rootE.getPathToCurrentDir(), reportDirName);
			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForReportFolder = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(writePerm).build();
			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForReportFolderRead = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readPerm).build();
			FolderUserGroupPermissionEntity folderUnAuthenitcatedUserGroupPermissionsForReport = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.UNAUTHENTICATED).permissions(readPerm).build();

			List<FolderUserGroupPermissionEntity> reportUserGroupPermission = new ArrayList<>();
			reportUserGroupPermission.addAll(Arrays.asList(folderAuthenitcatedUserGroupPermissionsForReportFolder,
					folderUnAuthenitcatedUserGroupPermissionsForReport,
					folderAuthenitcatedUserGroupPermissionsForReportFolderRead));

			FolderPermissionEntity reportDocsPermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(reportUserGroupPermission).build();

			FolderEntity reportDocsFolder = FolderEntity.builder().folderNickName(reportNickName)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).name(reportDirName).parent(rootE)
					.pathToCurrentDir(pathToReportFolder.toString()).folderPermission(reportDocsPermission).build();

			reportDocsPermission.setFolder(reportDocsFolder);
			internalDocsFolder = createFolder(reportDocsFolder);
			folderPerm.save(reportDocsPermission);

			// deas folder
			Path pathToDeas = Paths.get(rootE.getPathToCurrentDir(), deasFolderName);
			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForDEAS = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readWritePerm).build();

			List<FolderUserGroupPermissionEntity> deasUserGroupPermission = new ArrayList<>();
			deasUserGroupPermission.addAll(Arrays.asList(folderAuthenitcatedUserGroupPermissionsForDEAS));

			FolderPermissionEntity deasPermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(deasUserGroupPermission).build();

			FolderEntity deasFolder = FolderEntity.builder().folderNickName(deasFolderNickname)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).name(deasFolderName).parent(rootE)
					.pathToCurrentDir(pathToDeas.toString()).folderPermission(deasPermission).build();

			deasPermission.setFolder(deasFolder);
			internalDocsFolder = createFolder(deasFolder);
			folderPerm.save(deasPermission);

			Path pathToIA = Paths.get(rootE.getPathToCurrentDir(), iaFolderName);
			FolderUserGroupPermissionEntity folderAuthenitcatedUserGroupPermissionsForIA = FolderUserGroupPermissionEntity
					.builder().folderUserGroup(FolderUserGroupsEnum.AUTHENTICATED).permissions(readWritePerm).build();

			List<FolderUserGroupPermissionEntity> iaUserGroupPermission = new ArrayList<>();
			iaUserGroupPermission.addAll(Arrays.asList(folderAuthenitcatedUserGroupPermissionsForIA));

			FolderPermissionEntity iaPermission = FolderPermissionEntity.builder()
					.folderUserGroupPermissions(iaUserGroupPermission).build();

			FolderEntity iaFolder = FolderEntity.builder().folderNickName(iaFolderNickName)
					.folderId(randomGeneratorService.generateRandomAlphaNumeric()).name(deasFolderName).parent(rootE)
					.pathToCurrentDir(pathToIA.toString()).folderPermission(deasPermission).build();

			iaPermission.setFolder(iaFolder);
			internalDocsFolder = createFolder(iaFolder);
			folderPerm.save(iaPermission);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void deleteAllRecursively() throws FileSystemException {
		Path directory = Paths.get(rootDirName);

		try {
			FileSystemUtils.deleteRecursively(directory);
		} catch (IOException e) {
			throw new FileSystemException("Unable to delete folder recursively");
		}

	}

	private FolderEntity createRootFolder(FolderEntity root) throws Exception {
		try {
			Files.createDirectories(Paths.get(root.getPathToCurrentDir()));
			root = folderRepo.saveAndFlush(root);

		} catch (IOException io) {
			throw new Exception("Could not initialize storage");
		}
		return root;
	}

	private FolderEntity createFolder(FolderEntity folder) throws Exception {
		FolderEntity parentFolder = null;
		if (folder.getParent() != null && folder.getParent().getId() != null) {
			parentFolder = folderRepo.findById(folder.getParent().getId()).orElseThrow(EntityNotFoundException::new);
		}

		try {

			if (parentFolder == null) {
				throw new Exception("ParentFolderNotPresent");
			} else {

				if (folder.getPathToCurrentDir() == null) {

					Path pathToNewFolder = Paths.get(parentFolder.getPathToCurrentDir(), folder.getName());

					folder.setPathToCurrentDir(pathToNewFolder.toString());
					folder.setParent(parentFolder);
				}

				if (Files.notExists(Paths.get(folder.getPathToCurrentDir()))) {
					Files.createDirectories(Paths.get(folder.getPathToCurrentDir()));
					/// folder = folderRepository.saveAndFlush(folder);
				} else {
					if (Boolean.parseBoolean(createFolderInDBIfPhysicalFolderExistsAndDBMissing))
						/// folder = folderRepository.saveAndFlush(folder);
						throw new Exception("FolderExists");
				}

			}

		}

		catch (Exception e) {

			switch (e.getMessage()) {
			case "ParentFolderNotPresent":
				throw new StorageException("ParentFolderNotPresent");
			case "FolderExists":
				// throw new StorageException("FolderExists");
				// throw new BadRequestAlertException("FolderExists", "FolderExists",
				// "FolderExists");
			default:
				// throw new StorageException("InitializeStorageFailed");
			}

		}

		return folderRepo.saveAndFlush(folder);

	}

	private FolderEntity getRootDir() {
		return folderRepo.findByParent_IdIsNull().orElseThrow(EntityNotFoundException::new);
	}

}
