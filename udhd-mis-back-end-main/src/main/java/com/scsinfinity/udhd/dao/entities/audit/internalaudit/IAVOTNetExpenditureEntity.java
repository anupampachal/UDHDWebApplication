package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "IA_VOT_NET_EXPENDITURE")
public class IAVOTNetExpenditureEntity extends IAVolumeOfTransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8449949693247311167L;

	@OneToOne(mappedBy = "netExpenditure")
	private IAVolumeOfTransactionsEntity volumeOfTran;
}