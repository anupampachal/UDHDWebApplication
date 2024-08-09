package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IARevenueNCapitalReceiptsAndExpenditureDTO {

	private Long id;

	@NotNull
	private BigDecimal fy1Amt;
	@NotNull
	private BigDecimal fy2Amt;
	@NotNull
	private BigDecimal fy3Amt;
	@NotNull
	private BigDecimal fy4Amt;
	@NotNull
	private BigDecimal fy5Amt;
	@NotNull
	private BigDecimal fy6Amt;
	// @NotNull
	private IARevenueNCapitalReceiptsEnum type;

	private IARevenueNCapitalExpenditureEnum expenditureType;
}
