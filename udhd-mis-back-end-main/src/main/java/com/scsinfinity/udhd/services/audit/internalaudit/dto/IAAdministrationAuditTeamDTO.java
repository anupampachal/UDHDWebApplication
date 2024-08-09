package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IAAdministrationAuditTeamDTO {

	private Long id;
	@NotNull
	private Long iaId;
	@NotBlank
	private String name;
	
	private AdminAuditTeamTypeEnum type;

}
