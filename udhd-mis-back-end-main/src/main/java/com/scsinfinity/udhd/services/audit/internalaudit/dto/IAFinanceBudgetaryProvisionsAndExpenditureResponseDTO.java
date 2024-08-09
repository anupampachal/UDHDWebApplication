package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO {

	@NotNull
	private Long iaId;

	@NotBlank
	private String fy1;

	@NotBlank
	private String fy2;

	@NotBlank
	private String fy3;

	private IAFinalRevisedBudgetDataDTO finalBudgetData;

	private IAActualExpenditureDataDTO actualExpenditureData;
}
