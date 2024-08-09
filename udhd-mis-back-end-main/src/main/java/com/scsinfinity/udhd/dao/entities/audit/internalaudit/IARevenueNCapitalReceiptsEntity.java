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
@Table(name = "IA_REVENUE_N_CAPITAL_RECEIPTS")
public class IARevenueNCapitalReceiptsEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7004852356175252924L;
	@OneToOne
	private IAFinanceEntity finance;

	@NotBlank
	private String fy1L;
	@NotBlank
	private String fy2L;
	@NotBlank
	private String fy3L;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARevenueAndCapitalReceiptsInfoDetailsEntity details;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRPropertyTaxEntity propertyTax;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROtherTaxEntity otherTax;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRFeesNFinesEntity feesAndFines;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRUserChargesEntity userCharges;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROtherNonTaxRevenueEntity otherNonTaxRevenue;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRIncomeFromInterestEntity incomeFromInterest;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROtherRevenueIncomeEntity otherRevenueIncome;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRStateAssignedReveueEntity stateAssignedRevenue;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRStateFCGrantsEntity stateFCGrants;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROctraiCompensationEntity octraiCompensation;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROtherStateGovtTransfersEntity otherStateGovtTransfers;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRCFCGrantsEntity cfcGrants;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROthersEntity others;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRSaleOfMunicipalLandEntity saleOfMunicipalLand;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRLoansEntity loans;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRStateCapitalAccountsGrantsEntity stateCapitalAccountGrants;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCRCentralCapitalAccountGrantsEntity centralCapitalAccountGrants;

	@OneToOne(mappedBy = "revenueAndCapitalReceipts", cascade = CascadeType.ALL)
	private IARCROtherCapitalReceiptsEntity otherCapitalReceipts;

}
