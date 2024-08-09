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
@Table(name = "IA_DELAY_DEPOSIT_TAX_COLLECTED")
public class IADelayDepositTaxCollectedEntity extends IAAuditObservation implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3620973926813826621L;
}
