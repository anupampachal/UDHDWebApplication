package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IAStatusOfAuditObservationDTO {

	@NotNull
	private Long iaId;
	
	private Long id;
	@NotBlank
	@Size(min = 3, max = 255)
	private String auditParticularsWithDate;

	@NotNull
	private Long totalNosOfAuditParas;

	@NotNull
	private Long totalAuditParasWithCorrectiveActionReq;

	@NotNull
	private Long totalAuditParasWithCashRecProposed;

	@NotNull
	private Long totalAuditParasWithCashRecMade;

	@NotNull
	private BigDecimal totalAmtOfRecovery;

	@NotNull
	private Long totalNosOfParasWithNoAction;

	@NotBlank
	private String nosAndDateOfComplianceReport;
}
