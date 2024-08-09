package com.scsinfinity.udhd.web.audit.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class IADetailedAuditRequest {
    private final Long internalAuditId;
    private final List<IAStatusOfAuditObservationRequest> auditStatuses;
    private final List<IADetailAuditParaInfoRequest> auditParaInfos;
}
