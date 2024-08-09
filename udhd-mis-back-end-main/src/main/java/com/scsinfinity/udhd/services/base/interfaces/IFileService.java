package com.scsinfinity.udhd.services.base.interfaces;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.base.dto.FileDTO;
import com.scsinfinity.udhd.services.base.dto.FileMultipartDTO;

public interface IFileService {

	FileDTO getFileDBInfoByID(String fileId) throws EntityNotFoundException, BadRequestAlertException;

	FileDTO saveFile(MultipartFile file, String parentFolderId) throws Exception;

	Resource getFile(String fileId) throws Exception;

	public Boolean deleteFileById(String fileId) throws Exception;

	FileDTO saveFileWithMultipartAndParentNickNameOrId(FileMultipartDTO multipartDTO) throws Exception;

	FileEntity saveFileWithMultipartAndParentNickNameOrIdAndEntity(FileMultipartDTO multipartDTO) throws Exception;

	FileEntity saveDTOFile(FileDTO file, FolderEntity parentFolder);

	FolderUserGroupsEnum getCurrentUserGroup();

	FileDTO getFileDTO(FileEntity file);

	FileEntity getEntity(FileDTO file, MultipartFile fileM) throws EntityNotFoundException, Exception;

	FileEntity saveFileAndReturnEntity(MultipartFile file, String parentFolderId);

	FileEntity getFileEntityById(String fileId) throws EntityNotFoundException, BadRequestAlertException;

	FileEntity copyFile(FileEntity fileEntity,String nameToAddToFileOriginalName, String parentFolderId) throws IOException;
}
