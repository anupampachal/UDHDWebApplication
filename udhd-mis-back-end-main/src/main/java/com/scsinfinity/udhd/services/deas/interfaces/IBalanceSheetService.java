package com.scsinfinity.udhd.services.deas.interfaces;

import java.time.LocalDate;

import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetDistrictLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetULBLevelDataDTO;

public interface IBalanceSheetService {

	DeasBalanceSheetStateLevelDataDTO getBalanceSheetAtStateLevel(LocalDate date);

	DeasBalanceSheetDivisionLevelDataDTO getBalanceSheetAtDivisionLevel(Long divisionId, LocalDate date);

	DeasBalanceSheetDistrictLevelDataDTO getBalanceSheetAtDistrictLevel(Long districtId, LocalDate date);

	DeasBalanceSheetULBLevelDataDTO getBalanceSheetAtULBLevel(Long ulbId, LocalDate date);

}
