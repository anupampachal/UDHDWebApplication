package com.scsinfinity.udhd.services.deas.interfaces;

import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseDistrictLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpensesULBDataDTO;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

public interface IDeasIncomeAndExpenseDataService {

	DeasIncomeAndExpenseStateLevelDataDTO getIncomeAndExpenseAtStateLevel(FinancialRealDatesDTO dates);

	DeasIncomeAndExpenseDivisionLevelDataDTO getIncomeAndExpenseAtDivisionLevel(Long divisionId,
			FinancialRealDatesDTO dates);

	DeasIncomeAndExpenseDistrictLevelDataDTO getIncomeAndExpenseAtDistrictLevel(Long districtId,
			FinancialRealDatesDTO dates);

	DeasIncomeAndExpensesULBDataDTO getIncomeAndExpenseAtULBLevel(Long ulbId, FinancialRealDatesDTO dates);

}
