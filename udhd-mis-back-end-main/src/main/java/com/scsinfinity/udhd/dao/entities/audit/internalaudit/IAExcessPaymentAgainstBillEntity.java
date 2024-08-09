package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@Table(name = "IA_EXCESS_PAYMENT_AGAINST_BILL")
public class IAExcessPaymentAgainstBillEntity extends IAAuditObservation implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1050825509434752678L;

}
