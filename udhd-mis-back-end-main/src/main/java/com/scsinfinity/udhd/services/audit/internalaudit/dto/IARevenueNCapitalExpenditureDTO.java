package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IARevenueNCapitalExpenditureDTO {
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
	private IARevenueNCapitalReceiptsAndExpenditureDTO administrativeExpenses;

	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO operationAndMaintenance;

	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO loanRepayment;

	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherExpenditure;

	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO developmentWorks;

	@NotNull
	private IARevenueNCapitalReceiptsAndExpenditureDTO otherCapitalExpenditure;
}
