package com.scsinfinity.udhd.dao.repositories.audit.internalaudit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAdministrativeExpensesEntity;

@Repository
public interface IIAAdministrativeExpensesRepository extends JpaRepository<IAAdministrativeExpensesEntity, Long> {

}
