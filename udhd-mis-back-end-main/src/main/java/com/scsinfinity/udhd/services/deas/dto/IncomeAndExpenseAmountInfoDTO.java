package com.scsinfinity.udhd.services.deas.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class IncomeAndExpenseAmountInfoDTO {
	@NotNull
	private BigDecimal incomeAmtFromCurrentToPrev;

	@NotNull
	private BigDecimal expenseAmtFromCurrentToPrev;

	@NotNull
	private BigDecimal incomeAmtFromPrevToLast;

	@NotNull
	private BigDecimal expenseAmtFromPrevToLast;

	@NotNull
	private BigDecimal net;
}
