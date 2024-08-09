package com.scsinfinity.udhd.services.deas.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
public class DeasBalanceSheetLineItemDTO extends DeasAssetsAndLiabilitiesDTO {

	@NotBlank
	private String particulars;
	@NotNull
	private Long id;
}
