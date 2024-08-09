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
@Table(name = "IA_MOBILE_TOWER_TAX")
public class IAMobileTowerTaxEntity extends IAAuditObservation implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -471278112884764255L;

}
