package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@Table(name = "IA_ADVERTISEMENT_TAX")
public class IAAdvertisementTaxEntity extends IAAuditObservation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3443340877048403871L;

}
