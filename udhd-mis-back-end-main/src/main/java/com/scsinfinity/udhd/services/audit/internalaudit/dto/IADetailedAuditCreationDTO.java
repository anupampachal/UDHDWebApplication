package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IADetailedAuditCreationDTO {
    private Long internalAuditId;
    private List<Long> auditStatusIdList;
}
