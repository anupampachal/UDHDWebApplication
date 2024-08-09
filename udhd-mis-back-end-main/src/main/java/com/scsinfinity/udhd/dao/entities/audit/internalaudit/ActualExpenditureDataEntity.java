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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "IA_BP_ACTUAL_EXPENDITURE_DATA")
public class ActualExpenditureDataEntity extends IABudgetaryProvisionAndExpenditure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3811456657693713574L;

	@OneToOne
	private IABudgetaryProvisionsAndExpenditureEntity budget;
}
