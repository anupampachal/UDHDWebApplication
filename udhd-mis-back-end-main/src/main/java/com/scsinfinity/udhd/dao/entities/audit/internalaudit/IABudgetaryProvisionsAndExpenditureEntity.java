package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "IA_BUDGETARY_PROVISION_N_EXPENDITURE")
public class IABudgetaryProvisionsAndExpenditureEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -5788475123125262417L;

	@OneToOne
	private IAFinanceEntity finance;

	@NotBlank
	private String fy1;

	@NotBlank
	private String fy2;

	@NotBlank
	private String fy3;

	@OneToOne(mappedBy = "budget",cascade = CascadeType.PERSIST)
	private FinalRevisedBudgetDataEntity finalBudgetData;

	@OneToOne(mappedBy = "budget",cascade = CascadeType.PERSIST)
	private ActualExpenditureDataEntity actualExpenditureData;
}
