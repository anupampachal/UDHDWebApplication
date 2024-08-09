package com.scsinfinity.udhd.web.deas.dataviews;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseDistrictLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpensesULBDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IDeasIncomeAndExpenseDataService;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deas/income-expense")
public class IncomeAndExpenseController {
	private final IDeasIncomeAndExpenseDataService incomeAndExpenseService;
	private final DateUtil dateUtil;

	@PostMapping("/state")
	public ResponseEntity<DeasIncomeAndExpenseStateLevelDataDTO> getStateLevelIncomeAndExpensesData(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		log.debug("state level income and expense info" + startDate +"<>" +endDate );
		return ResponseEntity.ok(incomeAndExpenseService.getIncomeAndExpenseAtStateLevel(new FinancialRealDatesDTO(
				dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}

	@PostMapping("/division/{divisionId}")
	public ResponseEntity<DeasIncomeAndExpenseDivisionLevelDataDTO> getDivisionLevelIncomeAndExpensesData(
			@PathVariable Long divisionId, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		log.debug("Division level income and expense info" + startDate +"<>" +endDate );
		return ResponseEntity
				.ok(incomeAndExpenseService.getIncomeAndExpenseAtDivisionLevel(divisionId, new FinancialRealDatesDTO(
						dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}

	@PostMapping("/district/{districtId}")
	public ResponseEntity<DeasIncomeAndExpenseDistrictLevelDataDTO> getDistrictLevelIncomeAndExpensesData(
			@PathVariable Long districtId, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		log.debug("District level income and expense info" + startDate +"<>" +endDate );
		return ResponseEntity
				.ok(incomeAndExpenseService.getIncomeAndExpenseAtDistrictLevel(districtId, new FinancialRealDatesDTO(
						dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}

	@PostMapping("/ulb/{ulbId}")
	public ResponseEntity<DeasIncomeAndExpensesULBDataDTO> getULBLevelIncomeAndExpensesData(@PathVariable Long ulbId,
			 @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		log.debug("ULB level income and expense info" + startDate +"<>" +endDate );
		return ResponseEntity.ok(incomeAndExpenseService.getIncomeAndExpenseAtULBLevel(ulbId, new FinancialRealDatesDTO(
				dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}

}
