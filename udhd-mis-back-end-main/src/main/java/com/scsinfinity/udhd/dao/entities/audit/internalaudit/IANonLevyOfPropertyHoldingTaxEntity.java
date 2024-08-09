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
@Table(name = "IA_NON_LEVY_OF_PROPERTY_N_HOLDING_TAX")
public class IANonLevyOfPropertyHoldingTaxEntity extends IAAuditObservation implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1595203015624607441L;

	
}
