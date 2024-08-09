package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class IAExecSummaryStrengthWeaknessOrRecommendationInputDTO {

	private Long sworId;

	@NotBlank
	@Size(min = 3, max = 2000)
	private String text;

	@NotNull
	private Long ia;
	
	@NotNull
	private IAExecutiveSummarySWOREnum type;
}

