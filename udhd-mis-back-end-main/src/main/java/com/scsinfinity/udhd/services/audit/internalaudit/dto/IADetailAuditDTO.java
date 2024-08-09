package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IADetailAuditDTO {
	private Long iaId;
	private List<IAStatusOfAuditObservationDTO> auditStatuses;
	private List<IADetailAuditParaInfoDTO> auditParaInfos;

}
