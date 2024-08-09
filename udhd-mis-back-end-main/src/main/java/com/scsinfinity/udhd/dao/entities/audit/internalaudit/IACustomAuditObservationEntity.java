package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "IA_CUSTOM_AUDIT_OBSERVATION")
public class IACustomAuditObservationEntity extends IAAuditObservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5771250168879503172L;

	//private IAAuditObservationEntity auditObservation;

}
