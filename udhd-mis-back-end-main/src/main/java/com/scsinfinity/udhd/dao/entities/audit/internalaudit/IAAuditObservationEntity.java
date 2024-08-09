package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "IA_AUDIT_OBSERVATION")
public class IAAuditObservationEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -8272605815655541426L;
	@OneToOne
	private InternalAuditEntity internalAudit;

	@OneToOne
	private IANonLevyOfPropertyHoldingTaxEntity propertyHoldingTax;

	@OneToOne
	private IADelayDepositTaxCollectedEntity delayDepositTaxCollected;

	@OneToOne
	private IAMobileTowerTaxEntity mobileTowerTax;

	@OneToOne
	private IARentOnMunicipalPropertiesEntity rentOnMunicipalProperties;

	@OneToOne
	private IAAdvertisementTaxEntity advertisementTax;

	@OneToOne
	private IAExcessPaymentAgainstBillEntity excessPaymentAgainstBill;

	@OneToOne
	private IAReportOnFindingsOfFieldEntity reportOnFindingsOfField;

	@OneToMany(fetch = FetchType.EAGER)
	private List<IACustomAuditObservationEntity> customAuditObservation;

	@OneToOne
	private IAAuditObservationPartBEntity partB;

	@OneToOne
	private IAPartCAuditObservationEntity partCDetails;
}
