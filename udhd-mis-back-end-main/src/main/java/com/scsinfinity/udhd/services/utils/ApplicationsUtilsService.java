package com.scsinfinity.udhd.services.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;
import com.scsinfinity.udhd.services.utils.dto.FinancialYearDTO;
import com.scsinfinity.udhd.services.utils.interfaces.IApplicationUtilsService;

@Service
public class ApplicationsUtilsService implements IApplicationUtilsService {

	@Override
	public GenericResponseObject<FinancialYearDTO> getFinancialYearDTO(Pagination<FinancialYearDTO> data) {
		if ((data.getPageNo() * data.getPageSize() > 50) || (data.getPageNo() * data.getPageSize() < -50))
			throw new BadRequestAlertException("Illegal years", "Years out of range", "YEARS_OUT_OF_RANGE");
		LocalDate date = LocalDate.now();
		List<FinancialYearDTO> dateList = getFinancialYear(date, data.getPageNo(), data.getPageSize());
		final Page<FinancialYearDTO> page = new PageImpl<>(dateList);

		GenericResponseObject<FinancialYearDTO> genericDatePage = new GenericResponseObject<FinancialYearDTO>(
				Long.parseLong(data.getPageSize() + ""), page, data.getPageNo(), data.getPageSize());
		return genericDatePage;
	}

	private List<FinancialYearDTO> getFinancialYear(LocalDate date, Integer pageNo, Integer pageSize) {
		//System.out.println("\nDate=" + date);
		Integer month = date.getMonthValue();
		int advance = (month < 4) ? 0 : 1;
		LocalDate correctLocDate = date.plus(pageNo * pageSize + advance, ChronoUnit.YEARS);
		List<FinancialYearDTO> dateList = new ArrayList<>();
		for (int i = 0; i < pageSize; i++) {
			dateList.add(new FinancialYearDTO(
					(correctLocDate.getYear() - 1 - i) + "-" + (correctLocDate.getYear() - i) % 100,
					correctLocDate.getYear() - 1 - i + "", (correctLocDate.getYear() - i) + "\n"));
		}

		return dateList;
	}

	@Override
	public FinancialRealDatesDTO interpretDate(FinancialYearDTO finDate) {
		LocalDate startDate = LocalDate.of(Integer.parseInt(finDate.getFinancialYearStartYear()), 04, 01);
		LocalDate endDate = LocalDate.of(Integer.parseInt(finDate.getFinancialYearEndDate()), 03, 31);
		return new FinancialRealDatesDTO(startDate, endDate);
	}

}
