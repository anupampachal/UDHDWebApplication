package com.scsinfinity.udhd.configurations.application.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

@Component
public class DateUtil {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	DateTimeFormatter dtfWithMonthName = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	DateTimeFormatter dtfPtYearFirst = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//"dd-MM-yyyy"
	public LocalDate getDateFromPattern(String date) {
		return LocalDate.parse(date, dtf);
	}

	public LocalDate getDateFromPatternYearFirst(String date) {
		return LocalDate.parse(date, dtfPtYearFirst);
	}

	public LocalDate getDateFromMonthName(String date) {
		String datePart = date.split("-")[0];

		if (Integer.parseInt(datePart) < 10) {
			date = "0" + date;

		}
		return LocalDate.parse(date, dtfWithMonthName);
	}

	public FinancialRealDatesDTO getDatesFromFinYear(LocalDate date) {
		LocalDate startDate;
		LocalDate endDate = date;
		int valToSubtract = date.getMonth().getValue() < 3 ? 1 : 0;
		startDate = LocalDate.of(date.getYear() - valToSubtract, 4, 1);
		return new FinancialRealDatesDTO(startDate, endDate);

	}

	public FinYearDateDTO getFinDates(LocalDate startDate, LocalDate endDate) throws Exception {

		if (startDate.get(IsoFields.QUARTER_OF_YEAR) != endDate.get(IsoFields.QUARTER_OF_YEAR)) {
			throw new Exception("invalid date range");
		}
		int quarterVal = startDate.get(IsoFields.QUARTER_OF_YEAR);
		DateQuartersEnum quarter = null;
		int advance = (startDate.getMonthValue() < 4) ? 0 : 1;
		startDate = startDate.plus(advance, ChronoUnit.YEARS);
		endDate = endDate.plus(advance, ChronoUnit.YEARS);
		switch (quarterVal) {
		case 1: {
			quarter = DateQuartersEnum.Q4;
			break;
		}
		case 2: {
			quarter = DateQuartersEnum.Q1;
			break;
		}
		case 3: {
			quarter = DateQuartersEnum.Q2;
			break;
		}
		case 4: {
			quarter = DateQuartersEnum.Q3;
			break;
		}
		}
		return new FinYearDateDTO(startDate.getYear() - 1 + "-" + (endDate.getYear() % 100),
				startDate.getYear() - 1 + "", (endDate.getYear()) + "");
	}

	public FinancialRealDatesDTO interpretDateFromFinYearAndQuarter(FinYearDateDTO finDate) {
		FinancialRealDatesDTO finalDates = new FinancialRealDatesDTO();

		finalDates.setStartDate(LocalDate.of(Integer.parseInt(finDate.getStartDate()), 04, 01));
		finalDates.setEndDate(LocalDate.of(Integer.parseInt(finDate.getEndDate()), 03, 31));
		return finalDates;

	}

	public List<FinYearDateDTO> getFinancialYear(LocalDate date, Integer pageNo, Integer pageSize) {
		Integer month = date.getMonthValue();
		int advance = (month < 4) ? 0 : 1;
		LocalDate correctLocDate = date.plus(pageNo * pageSize + advance, ChronoUnit.YEARS);
		List<FinYearDateDTO> dateList = new ArrayList<>();
		for (int i = 0; i < pageSize; i++) {
			dateList.add(
					new FinYearDateDTO((correctLocDate.getYear() - 1 - i) + "-" + (correctLocDate.getYear() - i) % 100,
							correctLocDate.getYear() - 1 - i + "", (correctLocDate.getYear() - i) + ""));
		}

		return dateList;
	}

}
