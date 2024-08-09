package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@SuperBuilder
@Table(name = "IA_RENT_ON_MUNICIPAL_PROPERTIES")
public class IARentOnMunicipalPropertiesEntity extends IAAuditObservation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1834501276557241055L;
}
