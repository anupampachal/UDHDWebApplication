package com.scsinfinity.udhd.services.deas.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class DeasIncomeAndExpensesAbstractDataDTO {
	@NotNull
	private IncomeAndExpenseNetDTO mainIncomeAndExpenseComparativeData;
	private List<IncomeAndExpenseNetDTO> childLevelIncomeAndExpenseData;
	@NotNull
	private List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelIncomeInfo;
	@NotNull
	private List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelExpenseInfo;
}
