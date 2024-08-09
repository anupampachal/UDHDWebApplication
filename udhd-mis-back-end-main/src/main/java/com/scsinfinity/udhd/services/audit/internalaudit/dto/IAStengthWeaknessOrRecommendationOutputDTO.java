package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@Getter
public class IAStengthWeaknessOrRecommendationOutputDTO {

	@NotBlank
	private String text;

	@NotNull
	private Long sworId;

	@NotNull
	private Long ia;

	@NotNull
	private IAExecutiveSummarySWOREnum type;
}
