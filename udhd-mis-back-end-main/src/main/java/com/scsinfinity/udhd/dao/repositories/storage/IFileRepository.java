package com.scsinfinity.udhd.dao.repositories.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

@Repository
public interface IFileRepository extends JpaRepository<FileEntity, Long> {

	Optional<FileEntity> findByFileId(String fileId);

	List<FileEntity> findByParentFolder_FolderId(String parentFolderId);

}
