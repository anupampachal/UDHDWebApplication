package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "IA_RCR_STATE_ASSIGNED_REVENUE")
public class IARCRStateAssignedReveueEntity extends IARevenueNCapitalReceiptsAndExpenditure implements Serializable{/**
		/**
	 * 
	 */
	private static final long serialVersionUID = -1L;
	@OneToOne
	private IARevenueNCapitalReceiptsEntity revenueAndCapitalReceipts;
}
