package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IARevenueNCapitalReceiptDTO {

	@NotNull
	private Long iaId;

	private Long id;

	@NotBlank
	private String fy1L;
	@NotBlank
	private String fy2L;
	@NotBlank
	private String fy3L;

	@NotNull
	private IARevenueAndCapitalReceiptsInfoDetailsDTO details;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO propertyTax;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherTax;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO feesAndFines;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO userCharges;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherNonTaxRevenue;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO incomeFromInterest;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherRevenueIncome;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO stateAssignedRevenue;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO stateFCGrants;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO octraiCompensation;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherStateGovtTransfers;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO cfcGrants;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherCentralGovtTransfers;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO others;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO saleOfMunicipalLand;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO loans;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO stateCapitalAccountGrants;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO centralCapitalAccountGrants;
	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherCapitalReceipts;

}
