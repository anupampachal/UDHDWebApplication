package com.scsinfinity.udhd.web.deas.dataviews;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceDistrictDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceULBLevelDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IDeasCashAndBankBalanceService;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deas/cash-and-bank-balance")
public class DeasCashAndBankBalanceController {

	private final IDeasCashAndBankBalanceService cashNBankBalanceService;
	private final DateUtil dateUtil;

	@PostMapping("/state")
	public ResponseEntity<DeasCashAndBankBalanceStateLevelDataDTO> getStateLevelCashAndBankBalancesData(
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		log.debug("getStateLevelCashAndBankBalancesData");
		return ResponseEntity.ok(cashNBankBalanceService.getCashAndBankBalanceAtStateLevel(new FinancialRealDatesDTO(
				dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}

	@PostMapping("/division/{divisionId}")
	public ResponseEntity<DeasCashAndBankBalanceDivisionLevelDataDTO> getDivisionLevelCashAndBankBalancesData(
			@PathVariable Long divisionId, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		log.debug("getDivisionLevelCashAndBankBalancesData");
		return ResponseEntity
				.ok(cashNBankBalanceService.getCashAndBankBalanceAtDivisionLevel(divisionId, new FinancialRealDatesDTO(
						dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}

	@PostMapping("/district/{districtId}")
	public ResponseEntity<DeasCashAndBankBalanceDistrictDataDTO> getDistrictLevelCashAndBankBalancesData(
			@PathVariable Long districtId, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		log.debug("getDistrictLevelCashAndBankBalancesData");
		return ResponseEntity
				.ok(cashNBankBalanceService.getCashAndBankBalanceAtDistrictLevel(districtId, new FinancialRealDatesDTO(
						dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}

	@PostMapping("/ulb/{ulbId}")
	public ResponseEntity<DeasCashAndBankBalanceULBLevelDataDTO> getULBLevelCashAndBankBalancesData(
			@PathVariable Long ulbId, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate) {
		log.debug("getULBLevelCashAndBankBalancesData");
		System.out.println("getULBLevelCashAndBankBalancesData "+ startDate + endDate + ulbId);
		return ResponseEntity
				.ok(cashNBankBalanceService.getCashAndBankBalanceAtULBLevel(ulbId, new FinancialRealDatesDTO(
						dateUtil.getDateFromPattern(startDate), dateUtil.getDateFromPattern(endDate))));
	}
}
