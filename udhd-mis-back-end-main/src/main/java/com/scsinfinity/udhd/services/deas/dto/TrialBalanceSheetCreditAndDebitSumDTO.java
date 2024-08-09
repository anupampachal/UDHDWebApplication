package com.scsinfinity.udhd.services.deas.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrialBalanceSheetCreditAndDebitSumDTO {
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
}
