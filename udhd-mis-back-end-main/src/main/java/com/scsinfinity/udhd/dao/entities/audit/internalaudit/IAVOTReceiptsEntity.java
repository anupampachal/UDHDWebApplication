package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "IA_VOT_RECEIPT")
public class IAVOTReceiptsEntity extends IAVolumeOfTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8449949693247311167L;

	@OneToOne(mappedBy="receipts")
	private IAVolumeOfTransactionsEntity volumeOfTran;
}
