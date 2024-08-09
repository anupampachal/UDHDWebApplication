package com.scsinfinity.udhd.services.deas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.deas.BankAndCashBalanceEntity;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceFileInfoEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.repositories.deas.IBankAndCashBalanceRepository;
import com.scsinfinity.udhd.services.deas.dto.DEASDataLevelEnum;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceDistrictDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceLineItemDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasCashAndBankBalanceULBLevelDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IDeasCashAndBankBalanceService;
import com.scsinfinity.udhd.services.settings.dto.GeoDistrictDTO;
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDistrictService;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDivisionService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeasCashAndBankBalanceService implements IDeasCashAndBankBalanceService {

	private final DeasServices deasServices;
	private final IULBService ulbService;
	private final IGeoDistrictService districtService;
	private final IGeoDivisionService divisionService;
	private final IBankAndCashBalanceRepository cashAndBankBalanceRepo;

	@Override
	public DeasCashAndBankBalanceStateLevelDataDTO getCashAndBankBalanceAtStateLevel(FinancialRealDatesDTO dates) {
		log.debug("getCashAndBankBalanceAtStateLevel1");
		List<GeoDivisionDTO> divisions = divisionService.getAllActiveGeoDivision();
		FinancialRealDatesDTO currentPeriod = deasServices.getStartDate(dates);

		List<TrialBalanceFileInfoEntity> filesInfoForCurrentPeriod = deasServices.getFileInfosForState(currentPeriod);

		List<BankAndCashBalanceEntity> bankAndCashList = filesInfoForCurrentPeriod.stream()
				.map(file -> cashAndBankBalanceRepo.findByTrialBalance_Id(file.getId())).flatMap(List::stream)
				.collect(Collectors.toList());

		List<BankAndCashBalanceEntity> bankAndCashListMajorHead = bankAndCashList.stream()
				.filter(ba -> ba.getCode() == 450).collect(Collectors.toList());

		DeasCashAndBankBalanceLineItemDTO curretLevelCashAndBankBalance = getCurrentLevelCashAndBankBalance(
				bankAndCashListMajorHead, "State", 0L);
		List<DeasCashAndBankBalanceLineItemDTO> childLevelCashAndBankBalance = divisions.parallelStream()
				.map(ulb -> getDeasCashAndBankBalanceAtDivisionLevel(ulb, filesInfoForCurrentPeriod))
				.collect(Collectors.toList());
		DeasCashAndBankBalanceDTO details = getCurrentLevelDetailsForCashAndBankBalance(bankAndCashList);
		return DeasCashAndBankBalanceStateLevelDataDTO.builder()
				.childLevelCashAndBankBalance(childLevelCashAndBankBalance)
				.curretLevelCashAndBankBalance(curretLevelCashAndBankBalance).details(details).build();
	}

	@Override
	public DeasCashAndBankBalanceDivisionLevelDataDTO getCashAndBankBalanceAtDivisionLevel(Long divisionId,
			FinancialRealDatesDTO dates) {
		log.debug("getCashAndBankBalanceAtDivisionLevel2");

		GeoDivisionDTO division = divisionService.findByGeoDivisionId(divisionId);
		List<GeoDistrictDTO> districts = districtService.getGeoDistrictForDivision(divisionId);
		FinancialRealDatesDTO currentPeriod = deasServices.getStartDate(dates);
		List<TrialBalanceFileInfoEntity> filesInfoForCurrentPeriod = deasServices.getFileInfosForDivision(currentPeriod,
				division.getId());
		List<BankAndCashBalanceEntity> bankAndCashList = filesInfoForCurrentPeriod.stream()
				.map(file -> cashAndBankBalanceRepo.findByTrialBalance_Id(file.getId())).flatMap(List::stream)
				.collect(Collectors.toList());
		List<BankAndCashBalanceEntity> bankAndCashListMajorHead = bankAndCashList.stream()
				.filter(ba -> ba.getCode() == 450).collect(Collectors.toList());
		DeasCashAndBankBalanceLineItemDTO curretLevelCashAndBankBalance = getCurrentLevelCashAndBankBalance(
				bankAndCashListMajorHead, division.getName(), division.getId());
		List<DeasCashAndBankBalanceLineItemDTO> childLevelCashAndBankBalance = districts.parallelStream()
				.map(ulb -> getDeasCashAndBankBalanceAtDistrictLevel(ulb, filesInfoForCurrentPeriod))
				.collect(Collectors.toList());
		DeasCashAndBankBalanceDTO details = getCurrentLevelDetailsForCashAndBankBalance(bankAndCashList);

		return DeasCashAndBankBalanceDivisionLevelDataDTO.builder()
				.childLevelCashAndBankBalance(childLevelCashAndBankBalance)
				.curretLevelCashAndBankBalance(curretLevelCashAndBankBalance).details(details).build();
	}

	@Override
	public DeasCashAndBankBalanceDistrictDataDTO getCashAndBankBalanceAtDistrictLevel(Long districtId,
			FinancialRealDatesDTO dates) {
		log.debug("getCashAndBankBalanceAtDistrictLevel");
		GeoDistrictDTO district = districtService.findByGeoDistrictId(districtId);
		List<ULBEntity> ulbs = ulbService.getAllULBsForDistrict(districtId);
		FinancialRealDatesDTO currentPeriod = deasServices.getStartDate(dates);

		List<TrialBalanceFileInfoEntity> filesInfoForCurrentPeriod = deasServices.getFileInfosForDistrict(currentPeriod,
				district.getId());

		List<BankAndCashBalanceEntity> bankAndCashList = filesInfoForCurrentPeriod.stream()
				.map(file -> cashAndBankBalanceRepo.findByTrialBalance_Id(file.getId())).flatMap(List::stream)
				.collect(Collectors.toList());

		List<BankAndCashBalanceEntity> bankAndCashListMajorHead = bankAndCashList.stream()
				.filter(ba -> ba.getCode() == 450).collect(Collectors.toList());

		DeasCashAndBankBalanceLineItemDTO curretLevelCashAndBankBalance = getCurrentLevelCashAndBankBalance(
				bankAndCashListMajorHead, district.getName(), districtId);
		List<DeasCashAndBankBalanceLineItemDTO> childLevelCashAndBankBalance = ulbs.parallelStream()
				.map(ulb -> getDeasCashAndBankBalanceAtULBLevel(ulb, filesInfoForCurrentPeriod))
				.collect(Collectors.toList());
		DeasCashAndBankBalanceDTO details = getCurrentLevelDetailsForCashAndBankBalance(bankAndCashList);
		return DeasCashAndBankBalanceDistrictDataDTO.builder()
				.childLevelCashAndBankBalance(childLevelCashAndBankBalance)
				.curretLevelCashAndBankBalance(curretLevelCashAndBankBalance).details(details).build();
	}

	private DeasCashAndBankBalanceLineItemDTO getDeasCashAndBankBalanceAtDivisionLevel(GeoDivisionDTO division,
			List<TrialBalanceFileInfoEntity> filesInfoForCurrentPeriod) {
		List<ULBEntity> ulbs = ulbService.getAllULBsForDivisionPTD(division.getId());
		List<BankAndCashBalanceEntity> bankAndCashList = filesInfoForCurrentPeriod.stream()
				.filter(file -> ulbs.contains(file.getUlb()))
				.map(file -> cashAndBankBalanceRepo.findByTrialBalance_Id(file.getId())).flatMap(List::stream)
				.filter(ba -> ba.getCode() == 450).collect(Collectors.toList());
		return getCurrentLevelCashAndBankBalance(bankAndCashList, division.getName(), division.getId());
	}

	private DeasCashAndBankBalanceLineItemDTO getDeasCashAndBankBalanceAtDistrictLevel(GeoDistrictDTO distict,
			List<TrialBalanceFileInfoEntity> filesInfoForCurrentPeriod) {
		List<ULBEntity> ulbs = ulbService.getAllULBsForDistrictPTD(distict.getId());
		List<BankAndCashBalanceEntity> bankAndCashList = filesInfoForCurrentPeriod.stream()
				.filter(file -> ulbs.contains(file.getUlb()))
				.map(file -> cashAndBankBalanceRepo.findByTrialBalance_Id(file.getId())).flatMap(List::stream)
				.filter(ba -> ba.getCode() == 450).collect(Collectors.toList());
		return getCurrentLevelCashAndBankBalance(bankAndCashList, distict.getName(), distict.getId());
	}

	private DeasCashAndBankBalanceLineItemDTO getDeasCashAndBankBalanceAtULBLevel(ULBEntity ulb,
			List<TrialBalanceFileInfoEntity> filesInfoForCurrentPeriod) {
		List<BankAndCashBalanceEntity> bankAndCashList = filesInfoForCurrentPeriod.stream()
				.filter(file -> file.getUlb().getId() == ulb.getId())
				.map(file -> cashAndBankBalanceRepo.findByTrialBalance_Id(file.getId())).flatMap(List::stream)
				.filter(ba -> ba.getCode() == 450).collect(Collectors.toList());
		return getCurrentLevelCashAndBankBalance(bankAndCashList, ulb.getName(), ulb.getId());
	}

	@Override
	public DeasCashAndBankBalanceULBLevelDataDTO getCashAndBankBalanceAtULBLevel(Long ulbId,
			FinancialRealDatesDTO dates) {
		log.debug("getCashAndBankBalanceAtULBLevel");

		FinancialRealDatesDTO currentPeriod = deasServices.getStartDate(dates);
		ULBEntity ulb = ulbService.findULBEntityById(ulbId);
		List<TrialBalanceFileInfoEntity> filesInfoForCurrentPeriod = deasServices.getFileInfosForULB(currentPeriod,
				ulb);

		List<BankAndCashBalanceEntity> bankAndCashList = filesInfoForCurrentPeriod.stream()
				.map(file -> cashAndBankBalanceRepo.findByTrialBalance_Id(file.getId())).flatMap(List::stream)
				.collect(Collectors.toList());

		DeasCashAndBankBalanceLineItemDTO curretLevelCashAndBankBalance = getDeasCashAndBankBalanceAtULBLevel(ulb,
				filesInfoForCurrentPeriod);
		List<DeasCashAndBankBalanceLineItemDTO> childLevelCashAndBankBalance = null;
		DeasCashAndBankBalanceDTO details = getCurrentLevelDetailsForCashAndBankBalance(bankAndCashList);
		return DeasCashAndBankBalanceULBLevelDataDTO.builder()
				.curretLevelCashAndBankBalance(curretLevelCashAndBankBalance).details(details)
				.childLevelCashAndBankBalance(childLevelCashAndBankBalance).build();
	}

	private DeasCashAndBankBalanceLineItemDTO getCurrentLevelCashAndBankBalance(
			List<BankAndCashBalanceEntity> bankAndCashList, String particulars, Long code) {
		return DeasCashAndBankBalanceLineItemDTO.builder()
				.closingBalance(bankAndCashList.stream().map(data -> data.getClosingBlnc()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.creditAmt(bankAndCashList.stream().map(data -> data.getCreditAmount()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.debitAmt(bankAndCashList.stream().map(data -> data.getDebitAmount()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.openingBalance(bankAndCashList.stream().map(data -> data.getOpeningBlnc()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.accountCode(Integer.parseInt(code.toString())).particulars(particulars).build();
	}

	private DeasCashAndBankBalanceDTO getCurrentLevelDetailsForCashAndBankBalance(
			List<BankAndCashBalanceEntity> bankAndCashList) {
		Function<BankAndCashBalanceEntity, DeasCashAndBankBalanceLineItemDTO> getDTO = en -> DeasCashAndBankBalanceLineItemDTO
				.builder().closingBalance(en.getClosingBlnc()).creditAmt(en.getCreditAmount())
				.debitAmt(en.getDebitAmount()).openingBalance(en.getOpeningBlnc()).particulars(en.getName())
				.accountCode(en.getCode()).build();

		Map<Integer, DeasCashAndBankBalanceLineItemDTO> dataMap = new HashMap<>();
		DeasCashAndBankBalanceDTO data = DeasCashAndBankBalanceDTO.builder().level(DEASDataLevelEnum.ULB)
				.cashAndBankBalanceInfo(
						bankAndCashList.stream().map(bnc -> getDTO.apply(bnc)).collect(Collectors.toList()))
				.build();
		for (DeasCashAndBankBalanceLineItemDTO lineItem : data.getCashAndBankBalanceInfo()) {
			if (lineItem.getAccountCode() > 10000 && lineItem.getAccountCode() < 99999) {

				if (!dataMap.containsKey(lineItem.getAccountCode())) {
					dataMap.put(lineItem.getAccountCode(), lineItem);
				} else {
					DeasCashAndBankBalanceLineItemDTO existingData = dataMap.get(lineItem.getAccountCode());
					DeasCashAndBankBalanceLineItemDTO newData = DeasCashAndBankBalanceLineItemDTO.builder()
							.closingBalance(existingData.getClosingBalance().add(lineItem.getClosingBalance()))
							.creditAmt(existingData.getCreditAmt().add(lineItem.getCreditAmt()))
							.debitAmt(existingData.getDebitAmt().add(lineItem.getDebitAmt()))
							.openingBalance(existingData.getOpeningBalance().add(lineItem.getOpeningBalance()))
							.particulars(existingData.getParticulars()).accountCode(existingData.getAccountCode())
							.build();
					dataMap.put(existingData.getAccountCode(), newData);
				}
			}
		}

		List<DeasCashAndBankBalanceLineItemDTO> resultList = new ArrayList<>(dataMap.values());
		data.setCashAndBankBalanceInfo(resultList);
		return data;
	}

}
