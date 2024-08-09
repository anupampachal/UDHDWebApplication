package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.AmountReconciledEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IABankReconciliationLineItemDTO {

	@NotNull
	private Long iaId;

	private Long id;

	@NotBlank
	private String bankName;

	@NotBlank
	private String projectSchemeName;

	@NotBlank
	private String accountNumber;

	@NotNull
	private BigDecimal bankBalancePerPassbook;

	@NotNull
	private BigDecimal cashBalancePerCashbook;

	@NotNull
	private AmountReconciledEnum amountReconciled;

	@NotNull
	private Boolean BRSStatus;
}
