package com.scsinfinity.udhd.services.base.interfaces;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.scsinfinity.udhd.dao.entities.storage.FileFolderPermissionTypeEnum;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderPermissionEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.services.base.dto.FolderDTO;

public interface IFolderService {

	FolderEntity getFolderEntityByFolderId(String folderID, FolderUserGroupsEnum currentUserInGroup) throws Exception;

	Optional<FolderEntity> getRootDir(FolderUserGroupsEnum currentUserInGroup) throws Exception;

	Boolean checkPermission(FolderPermissionEntity permission, FolderUserGroupsEnum currentUserGroup,
			FileFolderPermissionTypeEnum permissionType);

	List<FolderDTO> getChildFolders(String parentFolderID, FolderUserGroupsEnum currentUserInGroup)
			throws EntityNotFoundException, Exception;

	FolderDTO getFolderByFolderId(String folderID, FolderUserGroupsEnum currentUserGroup) throws Exception;

	FolderDTO getFolderByNickname(String foldernickname, FolderUserGroupsEnum currentUserInGroup) throws Exception;

	FolderDTO createUpdateFolder(FolderEntity folder, FolderUserGroupsEnum currentUserGroup) throws Exception;

	FolderEntity getFolderEntityByNickname(String foldernickname, FolderUserGroupsEnum currentUserInGroup)
			throws Exception;

	
}
