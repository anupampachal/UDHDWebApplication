package com.scsinfinity.udhd.dao.repositories.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.storage.FolderPermissionEntity;

@Repository
public interface IFolderPermissionRepository extends JpaRepository<FolderPermissionEntity, Long> {

}
