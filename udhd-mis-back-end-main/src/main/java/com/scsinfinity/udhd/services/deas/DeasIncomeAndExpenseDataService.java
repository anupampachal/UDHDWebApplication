package com.scsinfinity.udhd.services.deas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.deas.LedgerTransactionEntity;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceFileInfoEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.deas.dto.DEASDataLevelEnum;
import com.scsinfinity.udhd.services.deas.dto.DEASIncomeAndExpenseAccountLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseDistrictLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpenseStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasIncomeAndExpensesULBDataDTO;
import com.scsinfinity.udhd.services.deas.dto.IncomeAndExpenseNetDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IDeasIncomeAndExpenseDataService;
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
public class DeasIncomeAndExpenseDataService implements IDeasIncomeAndExpenseDataService {
	private final IGeoDivisionService geoDivisionService;
	private final IGeoDistrictService geoDistrictService;
	private final IULBService ulbService;
	private final DeasServices deasServices;
	private static final Integer incomeHead = 1;
	private static final Integer expenseHead = 2;

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasIncomeAndExpenseStateLevelDataDTO getIncomeAndExpenseAtStateLevel(FinancialRealDatesDTO datesF) {
		log.debug("getIncomeAndExpenseAtStateLevel");
		FinancialRealDatesDTO dates = deasServices.getStartDate(datesF);
		FinancialRealDatesDTO previousPeriod = deasServices.getDateOfYearBefore.apply(dates);

		List<TrialBalanceFileInfoEntity> fileInfoForCurrentPeriod = deasServices.getFileInfosForState(dates);
		List<LedgerTransactionEntity> ledgersIncomeCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpenseCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				expenseHead);

		List<TrialBalanceFileInfoEntity> fileInfoForPreviousPeriod = deasServices.getFileInfosForState(previousPeriod);
		List<LedgerTransactionEntity> ledgersIncomePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpensePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				expenseHead);
		IncomeAndExpenseNetDTO stateLevelIncomeAndExpense = deasServices.getIncomeAndExpenseNetDTO(dates,
				previousPeriod, ledgersIncomeCurrent, ledgersExpenseCurrent, ledgersIncomePrevious,
				ledgersExpensePrevious, DEASDataLevelEnum.STATE, "State", 0L);

		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelIncomeInfo = deasServices.getHeadLevelDetails
				.apply(ledgersIncomeCurrent);
		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelExpenseInfo = deasServices.getHeadLevelDetails
				.apply(ledgersExpenseCurrent);

		List<GeoDivisionDTO> divs = geoDivisionService.getAllActiveGeoDivision();
		FinancialRealDatesDTO datesFinal = dates;
		List<IncomeAndExpenseNetDTO> childLevelIncomeAndExpenseData = divs.stream()
				.map(div -> deasServices.getIncomeAndExpenseForSpecificDivision(datesFinal, previousPeriod, div))
				.collect(Collectors.toList());
		return DeasIncomeAndExpenseStateLevelDataDTO.builder()
				.childLevelIncomeAndExpenseData(childLevelIncomeAndExpenseData)
				.headLevelExpenseInfo(headLevelExpenseInfo).headLevelIncomeInfo(headLevelIncomeInfo)
				.mainIncomeAndExpenseComparativeData(stateLevelIncomeAndExpense).build();
	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasIncomeAndExpenseDivisionLevelDataDTO getIncomeAndExpenseAtDivisionLevel(Long divisionId,
			FinancialRealDatesDTO dates) {
		GeoDivisionDTO division = geoDivisionService.findByGeoDivisionId(divisionId);
		FinancialRealDatesDTO currentPeriod = deasServices.getStartDate(dates);
		FinancialRealDatesDTO previousPeriod = deasServices.getDateOfYearBefore.apply(dates);

		List<TrialBalanceFileInfoEntity> fileInfoForCurrentPeriod = deasServices.getFileInfosForDivision(currentPeriod,
				divisionId);
		List<LedgerTransactionEntity> ledgersIncomeCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpenseCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				expenseHead);
		List<TrialBalanceFileInfoEntity> fileInfoForPreviousPeriod = deasServices
				.getFileInfosForDivision(previousPeriod, divisionId);
		List<LedgerTransactionEntity> ledgersIncomePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpensePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				expenseHead);
		IncomeAndExpenseNetDTO divisionLevelIncomeAndExpense = deasServices.getIncomeAndExpenseNetDTO(dates,
				previousPeriod, ledgersIncomeCurrent, ledgersExpenseCurrent, ledgersIncomePrevious,
				ledgersExpensePrevious, DEASDataLevelEnum.DIVISION, division.getName(), division.getId());
		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelIncomeInfo = deasServices.getHeadLevelDetails
				.apply(ledgersIncomeCurrent);
		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelExpenseInfo = deasServices.getHeadLevelDetails
				.apply(ledgersExpenseCurrent);

		List<GeoDistrictDTO> districts = geoDistrictService.getGeoDistrictForDivision(divisionId);
		FinancialRealDatesDTO datesFinal = dates;
		List<IncomeAndExpenseNetDTO> childLevelIncomeAndExpenseData = districts.stream().map(
				district -> deasServices.getIncomeAndExpenseForSpecificDistrict(datesFinal, previousPeriod, district))
				.collect(Collectors.toList());

		return DeasIncomeAndExpenseDivisionLevelDataDTO.builder()
				.childLevelIncomeAndExpenseData(childLevelIncomeAndExpenseData)
				.headLevelExpenseInfo(headLevelExpenseInfo).headLevelIncomeInfo(headLevelIncomeInfo)
				.mainIncomeAndExpenseComparativeData(divisionLevelIncomeAndExpense).build();

	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasIncomeAndExpenseDistrictLevelDataDTO getIncomeAndExpenseAtDistrictLevel(Long districtId,
			FinancialRealDatesDTO dates) {
		GeoDistrictDTO district = geoDistrictService.findByGeoDistrictId(districtId);
		FinancialRealDatesDTO currentPeriod = deasServices.getStartDate(dates);
		FinancialRealDatesDTO previousPeriod = deasServices.getDateOfYearBefore.apply(dates);

		List<TrialBalanceFileInfoEntity> fileInfoForCurrentPeriod = deasServices.getFileInfosForDistrict(currentPeriod,
				districtId);
		List<LedgerTransactionEntity> ledgersIncomeCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpenseCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				expenseHead);
		List<TrialBalanceFileInfoEntity> fileInfoForPreviousPeriod = deasServices
				.getFileInfosForDistrict(previousPeriod, districtId);
		List<LedgerTransactionEntity> ledgersIncomePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpensePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				expenseHead);

		IncomeAndExpenseNetDTO districtLevelIncomeAndExpense = deasServices.getIncomeAndExpenseNetDTO(dates,
				previousPeriod, ledgersIncomeCurrent, ledgersExpenseCurrent, ledgersIncomePrevious,
				ledgersExpensePrevious, DEASDataLevelEnum.DISTRICT, district.getName(), district.getId());
		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelIncomeInfo = deasServices.getHeadLevelDetails
				.apply(ledgersIncomeCurrent);
		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelExpenseInfo = deasServices.getHeadLevelDetails
				.apply(ledgersExpenseCurrent);

		List<ULBEntity> ulbs = ulbService.getAllULBsForDistrict(districtId);
		FinancialRealDatesDTO datesFinal = dates;
		List<IncomeAndExpenseNetDTO> childLevelIncomeAndExpenseData = ulbs.stream()
				.map(ulb -> deasServices.getIncomeAndExpenseForSpecificULB(datesFinal, previousPeriod, ulb))
				.collect(Collectors.toList());

		return DeasIncomeAndExpenseDistrictLevelDataDTO.builder()
				.childLevelIncomeAndExpenseData(childLevelIncomeAndExpenseData)
				.headLevelExpenseInfo(headLevelExpenseInfo).headLevelIncomeInfo(headLevelIncomeInfo)
				.mainIncomeAndExpenseComparativeData(districtLevelIncomeAndExpense).build();

	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasIncomeAndExpensesULBDataDTO getIncomeAndExpenseAtULBLevel(Long ulbId, FinancialRealDatesDTO dates) {
		FinancialRealDatesDTO currentPeriod = deasServices.getStartDate(dates);
		FinancialRealDatesDTO previousPeriod = deasServices.getDateOfYearBefore.apply(dates);
		ULBEntity ulb = ulbService.findULBEntityById(ulbId);
		List<TrialBalanceFileInfoEntity> fileInfoForCurrentPeriod = deasServices.getFileInfosForULB(currentPeriod, ulb);
		List<LedgerTransactionEntity> ledgersIncomeCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpenseCurrent = deasServices.getLedgers(fileInfoForCurrentPeriod,
				expenseHead);
		List<TrialBalanceFileInfoEntity> fileInfoForPreviousPeriod = deasServices.getFileInfosForULB(previousPeriod,
				ulb);
		List<LedgerTransactionEntity> ledgersIncomePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				incomeHead);
		List<LedgerTransactionEntity> ledgersExpensePrevious = deasServices.getLedgers(fileInfoForPreviousPeriod,
				expenseHead);

		IncomeAndExpenseNetDTO ulbLevelIncomeAndExpense = deasServices.getIncomeAndExpenseNetDTO(dates, previousPeriod,
				ledgersIncomeCurrent, ledgersExpenseCurrent, ledgersIncomePrevious, ledgersExpensePrevious,
				DEASDataLevelEnum.ULB, ulb.getName(), ulb.getId());
		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelIncomeInfo = deasServices.getHeadLevelDetails
				.apply(ledgersIncomeCurrent);
		List<DEASIncomeAndExpenseAccountLevelDataDTO> headLevelExpenseInfo = deasServices.getHeadLevelDetails
				.apply(ledgersExpenseCurrent);

		return DeasIncomeAndExpensesULBDataDTO.builder().childLevelIncomeAndExpenseData(null)
				.headLevelExpenseInfo(headLevelExpenseInfo).headLevelIncomeInfo(headLevelIncomeInfo)
				.mainIncomeAndExpenseComparativeData(ulbLevelIncomeAndExpense).build();

	}

}
