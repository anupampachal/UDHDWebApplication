package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@Table(name = "IA_REPORT_ON_FINDINGS_OF_FIELD_SURVEY_OF_PROPERTY_TAX")
public class IAReportOnFindingsOfFieldEntity extends IAAuditObservation implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 81931783155986042L;

	}
