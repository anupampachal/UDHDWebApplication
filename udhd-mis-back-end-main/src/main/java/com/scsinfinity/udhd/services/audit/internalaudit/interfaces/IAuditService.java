package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import com.scsinfinity.udhd.dao.entities.audit.AuditEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditCreationDto;
import com.scsinfinity.udhd.services.audit.dto.AuditDTO;

public interface IAuditService {
    AuditDTO createAudit(AuditCreationDto auditCreationDto);

    AuditDTO retrieveAudit(Long id);

    void deleteAudit(Long id);

	AuditEntity createUpdateAuditEntity(AuditCreationDto auditCreationDto);
}
