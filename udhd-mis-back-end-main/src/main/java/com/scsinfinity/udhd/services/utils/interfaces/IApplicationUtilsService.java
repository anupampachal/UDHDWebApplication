package com.scsinfinity.udhd.services.utils.interfaces;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;
import com.scsinfinity.udhd.services.utils.dto.FinancialYearDTO;

public interface IApplicationUtilsService {

	GenericResponseObject<FinancialYearDTO> getFinancialYearDTO(Pagination<FinancialYearDTO> data);

	FinancialRealDatesDTO interpretDate(FinancialYearDTO finDate);
}
