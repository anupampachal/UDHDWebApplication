package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.ledger.BaseHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.DetailHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MajorHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MinorHeadEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalanceEntity extends BaseIdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1514989085615829906L;

	private BigDecimal balance;

	@ManyToOne
	private MajorHeadEntity majorHead;

	@ManyToOne
	private MinorHeadEntity minorHead;

	@ManyToOne
	private DetailHeadEntity detailHead;

	@ManyToOne
	private BaseHeadEntity baseHead;

	@ManyToOne
	private LedgerTransactionEntity transaction;

}
