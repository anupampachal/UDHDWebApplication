package com.scsinfinity.udhd.services.deas.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeasCashAndBankBalanceLineItemDTO {

	private Integer accountCode;
	
	@NotBlank
	private String particulars;

	@NotNull
	private BigDecimal openingBalance;

	@NotNull
	private BigDecimal debitAmt;

	@NotNull
	private BigDecimal creditAmt;

	@NotNull
	private BigDecimal closingBalance;
}
