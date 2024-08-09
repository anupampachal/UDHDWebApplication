package com.scsinfinity.udhd.dao.repositories.base;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.base.AuthorityEntity;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;

@Repository
public interface IAuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
	Optional<AuthorityEntity> findByName(String name);

	List<AuthorityEntity> findByAuthorityType(AuthorityTypeEnum authorityType);
}
