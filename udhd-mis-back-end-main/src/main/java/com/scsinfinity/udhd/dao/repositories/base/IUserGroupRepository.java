package com.scsinfinity.udhd.dao.repositories.base;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;

@Repository
public interface IUserGroupRepository extends JpaRepository<UserGroupEntity, Long> {

	Optional<UserGroupEntity> findByUserGroupIdIgnoreCase(String userGroupId);

	Page<UserGroupEntity> findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(
			String userGroupName,  String description, Pageable pageable);
	Page<UserProfileEntity> findByUserGroupId(String userGroupId,Pageable pageable);
	Page<UserGroupEntity> findAll(Pageable pageable);

	Optional<UserGroupEntity> findByNameIgnoreCaseContaining(String UserGroupName);

}
