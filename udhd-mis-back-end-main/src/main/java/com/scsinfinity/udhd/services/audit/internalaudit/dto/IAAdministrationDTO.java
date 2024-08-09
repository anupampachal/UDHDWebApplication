package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAdministrationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IAAdministrationDTO {

	@NotNull
	private Long internalAuditId;
	@NotBlank
	@Size(min = 3, max = 255)
	private String nameOfChairmanMayor;
	@NotBlank
	@Size(min = 3, max = 255)
	private String periodOfServiceChairman;
	@NotBlank
	@Size(min = 3, max = 255)
	private String nameOfCMO;
	@NotBlank
	@Size(min = 3, max = 255)
	private String periodOfServiceCMO;

	public IAAdministrationEntity getAdministrationEntityWithInternalAudit(Long id, InternalAuditEntity internalAudit) {
		return IAAdministrationEntity.builder().id(id).internalAudit(internalAudit)
				.nameOfChairmanMayor(nameOfChairmanMayor).periodOfServiceChairman(periodOfServiceChairman)
				.nameOfCMO(nameOfCMO).periodOfServiceCMO(periodOfServiceCMO).build();
	}
}
