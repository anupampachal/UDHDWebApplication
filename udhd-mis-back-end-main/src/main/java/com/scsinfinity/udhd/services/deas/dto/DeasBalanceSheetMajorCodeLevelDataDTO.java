package com.scsinfinity.udhd.services.deas.dto;

import java.math.BigDecimal;

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
public class DeasBalanceSheetMajorCodeLevelDataDTO {
	private String code;
	private String alias;
	private BigDecimal currentAmout;
	private BigDecimal previousAmount;
	private BalanceSheetAssetOrLiabilityEnum assetOrLiabilityEnum;
}
