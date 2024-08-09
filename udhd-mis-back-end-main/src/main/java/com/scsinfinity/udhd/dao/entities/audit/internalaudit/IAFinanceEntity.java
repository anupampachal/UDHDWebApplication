package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "IA_FINANCE_INFO")
public class IAFinanceEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2032493923173532971L;

	@OneToOne
	private InternalAuditEntity internalAudit;

	@OneToOne(mappedBy = "finance")
	private IABudgetaryProvisionsAndExpenditureEntity budgetaryProvisions;

	@OneToOne(mappedBy = "finance")
	private IAVolumeOfTransactionsEntity volumeOfTransactions;

	@OneToOne(mappedBy = "finance")
	private IABankReconciliationEntity bankReconciliation;

	@OneToOne(mappedBy = "finance")
	private IARevenueNCapitalReceiptsEntity revenueNCapitalReceipts;

	@OneToOne(mappedBy = "finance")
	private IARevenueNCapitalExpenditureEntity revenueNCapitalExpenditure;
	
	@OneToOne(mappedBy = "finance")
	private IAStatusOfImplementationOfDeasEntity deasImplementationStatus;
}
