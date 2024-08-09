package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.ledger.BaseHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.DetailHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MajorHeadEntity;
import com.scsinfinity.udhd.dao.entities.ledger.MinorHeadEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "DEAS_LEDGER_TRANSACTION")
public class LedgerTransactionEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8155655861759932616L;

	@NotNull
	private String accountCode;

	private String name;

	private BigDecimal openingBalance;

	private BigDecimal closingBalance;
	
	private BigDecimal balance;

	@ManyToOne
	private MajorHeadEntity majorHead;

	@ManyToOne
	private MinorHeadEntity minorHead;

	@ManyToOne
	private DetailHeadEntity detailHead;

	@ManyToOne
	private BaseHeadEntity baseHead;


	private BigDecimal amountDebited;

	private BigDecimal amountCredited;
	
	

	@ManyToOne
	private TrialBalanceFileInfoEntity trialBalance;

}
