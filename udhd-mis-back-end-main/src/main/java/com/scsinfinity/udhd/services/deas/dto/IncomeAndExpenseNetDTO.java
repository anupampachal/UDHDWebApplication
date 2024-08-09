package com.scsinfinity.udhd.services.deas.dto;

import javax.validation.constraints.NotBlank;
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
public class IncomeAndExpenseNetDTO extends IncomeAndExpenseAmountInfoDTO {

	@NotBlank
	private String currentPeriod;

	@NotBlank
	private String previousPeriod;

	@NotNull
	private DEASDataLevelEnum level;

	//@NotBlank
	private String geographyName;

	//@NotNull
	private Long geographyId;
}
