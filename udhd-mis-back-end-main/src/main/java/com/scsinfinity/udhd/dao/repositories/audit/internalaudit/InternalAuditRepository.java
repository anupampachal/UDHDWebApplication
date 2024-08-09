package com.scsinfinity.udhd.dao.repositories.audit.internalaudit;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalAuditRepository extends JpaRepository<InternalAuditEntity, Long> {
}