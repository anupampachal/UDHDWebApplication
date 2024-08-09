package com.scsinfinity.udhd.dao.entities.deas;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

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
@Table(name = "DEAS_BANK_AND_CASH_BALANCE")
public class BankAndCashBalanceEntity extends BaseIdEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -519751561133954766L;
	private String name;
	private Integer code;
	private BigDecimal debitAmount;

	private BigDecimal creditAmount;

	private BigDecimal openingBlnc;

	private BigDecimal closingBlnc;

	@ManyToOne
	private TrialBalanceFileInfoEntity trialBalance;
}
