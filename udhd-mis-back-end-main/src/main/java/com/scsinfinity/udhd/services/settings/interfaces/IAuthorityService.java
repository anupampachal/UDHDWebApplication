package com.scsinfinity.udhd.services.settings.interfaces;

import java.util.List;
import java.util.Optional;

import com.scsinfinity.udhd.dao.entities.base.AuthorityEntity;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;

public interface IAuthorityService {

	List<AuthorityEntity> getAllAuthorities();

	List<AuthorityEntity> getAuthoritiesForType(AuthorityTypeEnum type);
	
	Optional<AuthorityEntity> findByName(String authorityName);
	
}
