package com.scsinfinity.udhd.web.deas.dataviews;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetDistrictLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetULBLevelDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IBalanceSheetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deas/balance-sheet")
public class BalanceSheetController {

	private final IBalanceSheetService balanceSheetService;
	private final DateUtil dateUtil;

	@PostMapping("/state")
	public ResponseEntity<DeasBalanceSheetStateLevelDataDTO> getStateLevelBalanceSheetData(
			@RequestParam("dateTill") String dateTill) {
		log.debug("getStateLevelBalanceSheetData");
		return ResponseEntity
				.ok(balanceSheetService.getBalanceSheetAtStateLevel(dateUtil.getDateFromPattern(dateTill)));
	}

	@PostMapping("/division/{divisionId}")
	public ResponseEntity<DeasBalanceSheetDivisionLevelDataDTO> getDivisionLevelBalanceSheetData(
			@PathVariable Long divisionId, @RequestParam("dateTill") String dateTill) {
		log.debug("getDivisionLevelBalanceSheetData");
		return ResponseEntity.ok(
				balanceSheetService.getBalanceSheetAtDivisionLevel(divisionId, dateUtil.getDateFromPattern(dateTill)));
	}

	@PostMapping("/district/{districtId}")
	public ResponseEntity<DeasBalanceSheetDistrictLevelDataDTO> getDistrictLevelBalanceSheetData(
			@PathVariable Long districtId, @RequestParam("dateTill") String dateTill) {
		log.debug("getDistrictLevelBalanceSheetData");
		return ResponseEntity.ok(
				balanceSheetService.getBalanceSheetAtDistrictLevel(districtId, dateUtil.getDateFromPattern(dateTill)));
	}

	@PostMapping("/ulb/{ulbId}")
	public ResponseEntity<DeasBalanceSheetULBLevelDataDTO> getULBLevelBalanceSheetData(@PathVariable Long ulbId,
			@RequestParam("dateTill") String dateTill) {
		log.debug("getULBLevelBalanceSheetData");
		
		return ResponseEntity
				.ok(balanceSheetService.getBalanceSheetAtULBLevel(ulbId, dateUtil.getDateFromPattern(dateTill)));
	}
}
