package com.scsinfinity.udhd.dao.repositories.audit;

import com.scsinfinity.udhd.dao.entities.audit.AuditActionsStepEnumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuditActionStepEnumRepository extends JpaRepository<AuditActionsStepEnumEntity, Long> {
}
