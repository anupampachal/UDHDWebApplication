package com.scsinfinity.udhd.dao.repositories.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;

@Repository
public interface IFolderRepository extends JpaRepository<FolderEntity, Long> {
	Optional<FolderEntity> findByFolderIdIgnoreCase(String folderId);

	Optional<FolderEntity> findByFolderNickNameIgnoreCase(String folderNickName);

	List<FolderEntity> findByParent_FolderIdIgnoreCase(String parentFolderId);
	
	Optional<FolderEntity> findByParent_IdIsNull();
}
