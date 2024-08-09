package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class IAVolumeOfTransactionsDTO {

	private Long iaId;

	private Long id;

	@NotBlank
	private String budgetFY;

	@NotBlank
	private String previousYearFY;

	@NotBlank
	private String correspondingPrevYearFY;

	@NotBlank
	private String currentYearFY;

	@NotNull
	private IAVolumeOfTransactionLeafDTO openingBalance;
	@NotNull
	private IAVolumeOfTransactionLeafDTO receipts;
	@NotNull
	private IAVolumeOfTransactionLeafDTO netExpenditure;

}
