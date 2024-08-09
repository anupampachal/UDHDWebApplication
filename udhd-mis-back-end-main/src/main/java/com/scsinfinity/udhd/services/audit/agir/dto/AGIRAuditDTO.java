package com.scsinfinity.udhd.services.audit.agir.dto;

import com.scsinfinity.udhd.services.audit.dto.AuditDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class AGIRAuditDTO extends AuditDTO {

	private Long agirAuditId;

	private String ulbName;
	private Long ulbId;

	private Boolean isAssigned;
}
