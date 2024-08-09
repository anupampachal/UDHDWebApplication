package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IAFinalRevisedBudgetDataDTO {

	private Long id;

	@NotNull
	private BigDecimal fy1;

	@NotNull
	private BigDecimal fy2;

	@NotNull
	private BigDecimal fy3;
}
