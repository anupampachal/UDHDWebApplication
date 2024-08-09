package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO {

	@NotNull
	private Long iaId;
	@NotBlank
	@Size(min = 6, max = 26)
	private String fy1;
	@NotBlank
	@Size(min = 6, max = 26)
	private String fy2;
	@NotBlank
	@Size(min = 6, max = 26)
	private String fy3;
	@NotNull
	private BigDecimal finRevBudgetDataFy1;
	@NotNull
	private BigDecimal finRevBudgetDataFy2;
	@NotNull
	private BigDecimal finRevBudgetDataFy3;
	@NotNull
	private BigDecimal actualExpenditureDataFy1;
	@NotNull
	private BigDecimal actualExpenditureDataFy2;
	@NotNull
	private BigDecimal actualExpenditureDataFy3;
}
