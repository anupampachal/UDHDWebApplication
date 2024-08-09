package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "IA_AUDIT_OBSERVATION_PART_B")
public class IAAuditObservationPartBEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 5340039055896011542L;

	@OneToOne(mappedBy = "partB")
	private IAAuditObservationEntity auditObservation;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> aNonMaintenanceOfBooksOfAccounts;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> bIrregularatiesInProcurementProcess;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> cNonComplianceOfDirectivesOfUDHD;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> dNonComplianceOfActsNRulesOfUDHD;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> eLackOfInternalControlMeasures;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> gDeficiencyInPayrollSystem;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> iPhysicalVerificationOfStores;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> jAdvancesAndAdjustment;

	@OneToMany(cascade = CascadeType.ALL)
	private List<IAAuditObjectsWithMonetaryIrregularitiesEntity> kAnyOtherMatter;

	@OneToOne( cascade = CascadeType.ALL)
	private IANonComplianceOfTDSVATNOtherRelevantStatuteEntity fNonComplianceOfTDSVat;

	@OneToOne
	private IAUtilisationOfGrantsEntity hUtilisationOfGrants;
}
