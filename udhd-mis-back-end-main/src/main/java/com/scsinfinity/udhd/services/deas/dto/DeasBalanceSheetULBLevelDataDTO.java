package com.scsinfinity.udhd.services.deas.dto;

import java.util.List;

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
public class DeasBalanceSheetULBLevelDataDTO {
	@NotNull
	private DeasBalanceSheetLineItemDTO ulbLevelBalanceSheet;
	@NotNull
	private List<DeasBalanceSheetMajorCodeLevelDataDTO> majorHeadLevelBalanceDetails;

}
