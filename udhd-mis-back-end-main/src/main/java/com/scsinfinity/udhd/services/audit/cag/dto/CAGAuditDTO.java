package com.scsinfinity.udhd.services.audit.cag.dto;

import com.scsinfinity.udhd.services.audit.dto.AuditDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CAGAuditDTO extends AuditDTO {

	private Long cagAuditId;

	private String ulbName;
	private Long ulbId;

	private Boolean isAssigned;
}
