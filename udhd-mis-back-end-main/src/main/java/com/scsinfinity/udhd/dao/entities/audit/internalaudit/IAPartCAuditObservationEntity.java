package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_PARTC_AUDIT_OBSERVATION")
public class IAPartCAuditObservationEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3303842479073273259L;

	private String aAuditorShouldReportNonCompliance;

	private String bAuditorShouldOpenImplementationOfSAS;

	private String cAuditorShouldReportBiharMunicipalAccounting;

	private String dReportOnComplianceOfFinancialGuidelines;

	private String eAuditorShouldReportAllMajorRevenueLoss;

	private String fAuditorShouldReportOnAdequacy;

	private String gAuditorShouldReportOnProcurementThroughETendering;

	private String hAuditorShouldReportOnAvailabilityOfUC;

	private String iAuditorShouldReportOnInstanceOfLosses;

	private String jAuditorShouldReportOnPaymentTerms;

	private String kAuditorShouldReportOnEachPaymentTermsAndConditions;

	private String lAuditorShouldReportOnFixedDeposit;

	private String mAuditorShouldReportOnMajorLossesOfULBs;

	private String nAuditorShouldReportOnAllTypesOfTaxDeductions;

	private String oInternalAuditorEnsuresAllCAGAndAGAudit;

}
