package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.AuditParaDetailActionStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IADetailAuditParaInfoDTO {
	private Long id;
	@NotNull
	private Long iaId;
	@NotBlank
	@Size(min = 1, max = 255)
	private String auditParaNo;

	@NotBlank
	@Size(min = 5, max = 1000)
	private String auditParaHeading;

	@NotNull
	private BigDecimal amountInvolved;

	@NotNull
	private BigDecimal recoveryProposed;

	@NotNull
	private BigDecimal recoveryCompleted;

	@NotNull
	private AuditParaDetailActionStatusEnum actionStatus;

	private String fileId;
}
