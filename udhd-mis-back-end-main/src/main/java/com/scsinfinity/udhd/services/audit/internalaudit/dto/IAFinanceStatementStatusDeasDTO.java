package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IAFinanceStatementStatusDeasDTO {

	@NotNull
	private Long iaId;

	//@NotNull
	private Long statusOfDeasId;

	private Long id;

	@NotBlank
	private String particulars;
	@NotBlank
	private String propertyTaxRegister;
	@NotBlank
	private String fixedAssetsRegister;
	@NotBlank
	private String openingBalanceRegister;
	@NotBlank
	private String annualFinanceStatement;
}
