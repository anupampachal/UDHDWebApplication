package com.scsinfinity.udhd.services.deas.interfaces;

import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceDistrictDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceULBLevelDataDTO;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

public interface IDeasCashAndBankBalanceService {

	DeasCashAndBankBalanceStateLevelDataDTO getCashAndBankBalanceAtStateLevel(FinancialRealDatesDTO dates);

	DeasCashAndBankBalanceDivisionLevelDataDTO getCashAndBankBalanceAtDivisionLevel(Long divisionId,
			FinancialRealDatesDTO dates);

	DeasCashAndBankBalanceDistrictDataDTO getCashAndBankBalanceAtDistrictLevel(Long districtId,
			FinancialRealDatesDTO dates);

	DeasCashAndBankBalanceULBLevelDataDTO getCashAndBankBalanceAtULBLevel(Long ulbId, FinancialRealDatesDTO dates);

}
