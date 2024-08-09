package com.scsinfinity.udhd.dao.repositories.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;

@Repository
public interface IUserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
	/*Page<UserProfileEntity> findByUserGroups_UserGroupIdAndUser_AccountNonExpiredAndUser_AccountNonLockedAndUser_CredentialsNonExpiredAndUser_Enabled(
			String userGroupId, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired,
			Boolean enabled, Pageable page);*/

	
	List<UserProfileEntity> findAllByIdIn(List<Long>ids);
}
