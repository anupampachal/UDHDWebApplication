package com.scsinfinity.udhd.services.settings;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.base.AuthorityEntity;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.repositories.base.IAuthorityRepository;
import com.scsinfinity.udhd.services.settings.interfaces.IAuthorityService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorityService implements IAuthorityService {

	private final IAuthorityRepository authorityRepository;

	@Override
	public List<AuthorityEntity> getAllAuthorities() {
		log.debug("Authorities List Requested");
		return authorityRepository.findAll();
	}

	@Override
	public List<AuthorityEntity> getAuthoritiesForType(AuthorityTypeEnum type) {
		log.debug("Requested authorities list for type", type);
		return authorityRepository.findByAuthorityType(type);
	}

	@Override
	public Optional<AuthorityEntity> findByName(String authorityName) {
		log.debug("Requested Authority Information for authority:", authorityName);
		return authorityRepository.findByName(authorityName);
	}

}
