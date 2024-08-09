package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "IA_NON_COMPLIANCE_OF_TDS_VAT")
public class IANonComplianceOfTDSVATNOtherRelevantStatuteEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -3716709134840503209L;

	@OneToOne(mappedBy = "fNonComplianceOfTDSVat")
	private IAAuditObservationPartBEntity auditObservationPartB;

	@OneToOne
	private IANonComplianceTDSEntity nonComplianceTDS;

	@OneToOne
	private IANonComplianceVATGSTEntity nonComplianceVATGST;

	@OneToOne
	private IANonComplianceRoyaltyFeeEntity nonComplianceRoyaltyFee;

	@OneToOne
	private IANonComplianceTDSOnGSTEntity nonComplianceTDSOnGST;

	@OneToOne
	private IANonComplianceLabourCessEntity nonComplianceLaboutCess;

	@OneToMany
	private List<IANonComplianceTDSVATCustomEntity> nonComplianceTDSVATCustom;

}
