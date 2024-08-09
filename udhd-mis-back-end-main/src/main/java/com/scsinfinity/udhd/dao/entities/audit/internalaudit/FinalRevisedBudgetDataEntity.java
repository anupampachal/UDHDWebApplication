package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Table(name = "IA_BP_FINAL_REVISED_BUDGET_DATA")
public class FinalRevisedBudgetDataEntity extends IABudgetaryProvisionAndExpenditure implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1769962282261692118L;

	@OneToOne
	private IABudgetaryProvisionsAndExpenditureEntity budget;

}
