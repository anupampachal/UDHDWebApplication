package com.scsinfinity.udhd.services.deas.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public abstract class DeasAssetsAndLiabilitiesDTO {

	@NotNull
	private BigDecimal latestAssets;

	@NotNull
	private BigDecimal previousAssets;

	@NotNull
	private BigDecimal latestLiabilities;

	@NotNull
	private BigDecimal previousLiabilities;

	@NotNull
	private LocalDate asCurrentOn;

	@NotBlank
	private LocalDate asPreviousOn;
}
