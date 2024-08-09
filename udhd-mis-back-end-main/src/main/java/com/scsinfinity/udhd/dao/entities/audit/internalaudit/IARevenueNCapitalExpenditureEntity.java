package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_REVENUE_N_CAPITAL_EXPENDITURE")
public class IARevenueNCapitalExpenditureEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6802408293815840187L;

	@NotBlank
	private String fy1L;
	@NotBlank
	private String fy2L;
	@NotBlank
	private String fy3L;

	@OneToOne
	private IAFinanceEntity finance;

	@OneToOne(mappedBy = "revenueAndCapitalExpenditure", cascade = CascadeType.ALL)
	private IARevenueAndCapitalReceiptsInfoDetailsEntity details;

	@OneToOne(cascade = CascadeType.ALL)
	private IAAdministrativeExpensesEntity administrativeExpenses;

	@OneToOne(cascade = CascadeType.ALL)
	private IAOperationAndMaintenanceEntity operationAndMaintenance;

	@OneToOne(cascade = CascadeType.ALL)
	private IALoanRepaymentEntity loanRepayment;

	@OneToOne(cascade = CascadeType.ALL)
	private IAOtherExpenditureEntity otherExpenditure;

	@OneToOne(cascade = CascadeType.ALL)
	private IAAllDevelopmentWorksEntity developmentWorks;

	@OneToOne(cascade = CascadeType.ALL)
	private IAOtherCapitalExpenditureEntity otherCapitalExpenditure;
}
