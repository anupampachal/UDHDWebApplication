package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_BANK_RECONCILIATION_LINE_ITEM")
public class IABankReconciliationLineItemEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873328818089868384L;
	@ManyToOne
	private IABankReconciliationEntity bankReconciliation;

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
