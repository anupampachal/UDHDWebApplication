package com.scsinfinity.udhd.dao.repositories.base;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.base.UserEntity;

@Repository
@Transactional
@CacheConfig(cacheNames = { "user-cache" })
public interface IUserRepository extends JpaRepository<UserEntity, Long>
//, JpaSpecificationExecutor<UserEntity> 
{

	@Cacheable(key = "#username",unless="#result == null")
	Optional<UserEntity> findByUsernameIgnoreCase(String username);

	Optional<UserEntity> findByMobileNo(String mobileNo);
	Optional<UserEntity> findByUserProfile_Id(Long userProfileId);

	Optional<UserEntity> findByUserUIDAndAuthority_Name(String userId, String authority);
	
	Optional<UserEntity> findByUsernameIgnoreCaseOrMobileNo(String username, String mobileNo);

	Page<UserEntity> findAllByAuthority_Name(Pageable pageable, String authority);

	Page<UserEntity> findAllByAuthority_NameAndUsernameIgnoreCase(Pageable pageable, String authority, String username);

	Page<UserEntity> findAllByUsernameIgnoreCaseContainingOrNameIgnoreCaseContainingOrMobileNoIgnoreCaseContainingOrAuthority_Name(Pageable pageable,
			String username, String name, String mobileNo, String authority);
	
	Page<UserEntity> findByIdNotInAndAccountNonExpiredAndAccountNonLockedAndCredentialsNonExpiredAndEnabled(List<Long>id,Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled,
			Pageable page);
	
}
