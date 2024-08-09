package com.scsinfinity.udhd.services.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FinancialYearDTO {

	private String financialYearInputData;
	private String financialYearStartYear;
	private String financialYearEndDate;
	
}
