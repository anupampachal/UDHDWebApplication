package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = "IA_STATUS_OF_AUDIT_OBSERVATION")
public class IAStatusOfAuditObservationEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1973427792697804988L;

	@NotBlank
	@Size(min = 3, max = 255)
	private String auditParticularsWithDate;

	@NotNull
	private Long totalNosOfAuditParas;

	@NotNull
	private Long totalAuditParasWithCorrectiveActionReq;

	@NotNull
	private Long totalAuditParasWithCashRecProposed;

	@NotNull
	private Long totalAuditParasWithCashRecMade;

	@NotNull
	private BigDecimal totalAmtOfRecovery;

	@NotNull
	private Long totalNosOfParasWithNoAction;

	@NotBlank
	private String nosAndDateOfComplianceReport;

}
