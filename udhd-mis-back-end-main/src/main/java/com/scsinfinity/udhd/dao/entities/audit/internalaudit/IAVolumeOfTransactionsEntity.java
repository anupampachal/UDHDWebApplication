package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

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
@Table(name = "IA_VOLUME_OF_TRANSACTION")
public class IAVolumeOfTransactionsEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 5681245017124570266L;

	@OneToOne
	private IAFinanceEntity finance;

	//@NotBlank
	private String budgetFY;

	//@NotBlank
	private String previousYearFY;

	//@NotBlank
	private String correspondingPrevYearFY;

	//@NotBlank
	private String currentYearFY;

	@OneToOne
	private IAVOTOpeningBalanceEntity openingBalance;

	@OneToOne
	private IAVOTReceiptsEntity receipts;

	@OneToOne
	private IAVOTNetExpenditureEntity netExpenditure;

}
