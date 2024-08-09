package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IAUtilisationOfGrantsLineItemDTO {

	@NotNull
	private Long iaId;
	
	private Long id;
	
	@NotBlank
	private String schemeName;

	@NotNull
	private BigDecimal fundReceived;

	@NotNull
	private BigDecimal expenses;

	@NotNull
	private Long pendingUC;

	@NotNull
	private Long submittedUC;

	@NotBlank
	private String letterNoNDate;
}
