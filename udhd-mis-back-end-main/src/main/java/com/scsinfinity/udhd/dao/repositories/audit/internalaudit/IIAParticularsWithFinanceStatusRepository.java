package com.scsinfinity.udhd.dao.repositories.audit.internalaudit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAParticularsWithFinanceStatusEntity;

@Repository
public interface IIAParticularsWithFinanceStatusRepository
		extends JpaRepository<IAParticularsWithFinanceStatusEntity, Long> {

}
