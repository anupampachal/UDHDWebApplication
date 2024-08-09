package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class IAExecutiveSummaryIntroductionRequestDTO {
	@NotBlank
	private String periodCovered;
	@NotBlank
	private String executiveOfficerNameForPeriod;

	@NotNull
	private Long ia;

}
