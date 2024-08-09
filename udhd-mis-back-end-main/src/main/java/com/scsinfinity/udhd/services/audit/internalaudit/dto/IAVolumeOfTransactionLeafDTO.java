package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@Setter
public class IAVolumeOfTransactionLeafDTO {

	private Long id;

	@NotBlank
	private BigDecimal budgetFY;

	@NotBlank
	private BigDecimal previousYearFY;

	@NotBlank
	private BigDecimal correspondingPrevYearFY;

	@NotBlank
	private BigDecimal currentYearFY;

	@NotBlank
	private BigDecimal cumulativeForCurrentPeriod;

	@NotNull
	private VolumeOfTransactionTypeEnum type;

}
