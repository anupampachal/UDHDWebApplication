package com.scsinfinity.udhd.services.deas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.dao.entities.deas.LedgerTransactionEntity;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceFileInfoEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.repositories.deas.ILedgerTransactionRepository;
import com.scsinfinity.udhd.dao.repositories.deas.ITrialBalanceFileInfoRepository;
import com.scsinfinity.udhd.services.deas.dto.DEASDataLevelEnum;
import com.scsinfinity.udhd.services.deas.dto.DEASIncomeAndExpenseAccountLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.IncomeAndExpenseNetDTO;
import com.scsinfinity.udhd.services.settings.dto.GeoDistrictDTO;
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeasServices {

	private final DateUtil dateUtil;
	private final ITrialBalanceFileInfoRepository tbFileInfoRepository;
	private final ILedgerTransactionRepository ledgerTransactionRepository;
	private final IULBService ulbService;
	private static final Integer incomeHead = 1;
	private static final Integer expenseHead = 2;

	public FinancialRealDatesDTO getStartDate(FinancialRealDatesDTO datesF) {
		Function<FinancialRealDatesDTO, FinancialRealDatesDTO> getStartDateFn = date -> date != null ? date
				: dateUtil.getDatesFromFinYear(LocalDate.now());
		return getStartDateFn.apply(datesF);
	}

	public IncomeAndExpenseNetDTO getIncomeAndExpenseForSpecificDivision(FinancialRealDatesDTO datesCurrent,
			FinancialRealDatesDTO datesPrevious, GeoDivisionDTO division) {
		List<TrialBalanceFileInfoEntity> fileInfoForCurrentPeriod = getFileInfosForDivision(datesCurrent,
				division.getId());
		List<LedgerTransactionEntity> ledgersIncomeCurrent = getLedgers(fileInfoForCurrentPeriod, incomeHead);
		List<LedgerTransactionEntity> ledgersExpenseCurrent = getLedgers(fileInfoForCurrentPeriod, expenseHead);
		List<TrialBalanceFileInfoEntity> fileInfoForPreviousPeriod = getFileInfosForDivision(datesPrevious,
				division.getId());
		List<LedgerTransactionEntity> ledgersIncomePrevious = getLedgers(fileInfoForPreviousPeriod, incomeHead);
		List<LedgerTransactionEntity> ledgersExpensePrevious = getLedgers(fileInfoForPreviousPeriod, expenseHead);

		return getIncomeAndExpenseNetDTO(datesCurrent, datesPrevious, ledgersIncomeCurrent, ledgersExpenseCurrent,
				ledgersIncomePrevious, ledgersExpensePrevious, DEASDataLevelEnum.DIVISION, division.getName(),
				division.getId());

	}

	public IncomeAndExpenseNetDTO getIncomeAndExpenseForSpecificDistrict(FinancialRealDatesDTO datesCurrent,
			FinancialRealDatesDTO datesPrevious, GeoDistrictDTO district) {
		List<TrialBalanceFileInfoEntity> fileInfoForCurrentPeriod = getFileInfosForDistrict(datesCurrent,
				district.getId());
		List<LedgerTransactionEntity> ledgersIncomeCurrent = getLedgers(fileInfoForCurrentPeriod, incomeHead);
		List<LedgerTransactionEntity> ledgersExpenseCurrent = getLedgers(fileInfoForCurrentPeriod, expenseHead);
		List<TrialBalanceFileInfoEntity> fileInfoForPreviousPeriod = getFileInfosForDistrict(datesPrevious,
				district.getId());
		List<LedgerTransactionEntity> ledgersIncomePrevious = getLedgers(fileInfoForPreviousPeriod, incomeHead);
		List<LedgerTransactionEntity> ledgersExpensePrevious = getLedgers(fileInfoForPreviousPeriod, expenseHead);

		return getIncomeAndExpenseNetDTO(datesCurrent, datesPrevious, ledgersIncomeCurrent, ledgersExpenseCurrent,
				ledgersIncomePrevious, ledgersExpensePrevious, DEASDataLevelEnum.DIVISION, district.getName(),
				district.getId());

	}

	public IncomeAndExpenseNetDTO getIncomeAndExpenseForSpecificULB(FinancialRealDatesDTO datesCurrent,
			FinancialRealDatesDTO datesPrevious, ULBEntity ulb) {
		List<TrialBalanceFileInfoEntity> fileInfoForCurrentPeriod = getFileInfosForULB(datesCurrent, ulb);
		List<LedgerTransactionEntity> ledgersIncomeCurrent = getLedgers(fileInfoForCurrentPeriod, incomeHead);
		List<LedgerTransactionEntity> ledgersExpenseCurrent = getLedgers(fileInfoForCurrentPeriod, expenseHead);
		List<TrialBalanceFileInfoEntity> fileInfoForPreviousPeriod = getFileInfosForULB(datesPrevious, ulb);
		List<LedgerTransactionEntity> ledgersIncomePrevious = getLedgers(fileInfoForPreviousPeriod, incomeHead);
		List<LedgerTransactionEntity> ledgersExpensePrevious = getLedgers(fileInfoForPreviousPeriod, expenseHead);

		return getIncomeAndExpenseNetDTO(datesCurrent, datesPrevious, ledgersIncomeCurrent, ledgersExpenseCurrent,
				ledgersIncomePrevious, ledgersExpensePrevious, DEASDataLevelEnum.DIVISION, ulb.getName(), ulb.getId());

	}

	public Function<LedgerTransactionEntity, DEASIncomeAndExpenseAccountLevelDataDTO> mapToIncomeAndExpenseDtoForMajorHead = ledger -> DEASIncomeAndExpenseAccountLevelDataDTO
			.builder().amount(ledger.getClosingBalance()).code(ledger.getAccountCode())
			.description(ledger.getMajorHead().getName()).id(ledger.getMajorHead().getId()).build();

	public Function<List<LedgerTransactionEntity>, List<DEASIncomeAndExpenseAccountLevelDataDTO>> getHeadLevelDetails = list -> list
			.stream().filter(ledger -> ledger.getAccountCode().length() == 3)
			.map(ledger -> mapToIncomeAndExpenseDtoForMajorHead.apply(ledger)).collect(Collectors.toList());

	public IncomeAndExpenseNetDTO getIncomeAndExpenseNetDTO(FinancialRealDatesDTO datesCurrent,
			FinancialRealDatesDTO datesPrevious, List<LedgerTransactionEntity> ledgersIncomeCurrent,
			List<LedgerTransactionEntity> ledgersExpenseCurrent, List<LedgerTransactionEntity> ledgersIncomePrevious,
			List<LedgerTransactionEntity> ledgersExpensePrevious, DEASDataLevelEnum level, String geographyName,
			Long geographyId) {
		// @formatter:off
		BigDecimal currentIncome=getSummationOfAmount.apply(ledgersIncomeCurrent);
		BigDecimal currentExpense=getSummationOfAmount.apply(ledgersExpenseCurrent);
		return IncomeAndExpenseNetDTO.builder().currentPeriod(getDatesInFormatOfRangeString.apply(datesCurrent))
				.previousPeriod(getDatesInFormatOfRangeString.apply(datesPrevious))
				.expenseAmtFromCurrentToPrev(currentExpense)
				.expenseAmtFromPrevToLast(getSummationOfAmount.apply(ledgersExpensePrevious))
				.geographyId(geographyId)
				.geographyName(geographyName)
				.incomeAmtFromCurrentToPrev(currentIncome)
				.incomeAmtFromPrevToLast(getSummationOfAmount.apply(ledgersIncomePrevious))
				.level(level)
				.net(currentIncome.add(currentExpense))
				.build();
		// @formatter:on
	}

	public Function<List<LedgerTransactionEntity>, BigDecimal> getSummationOfAmount = list -> list.stream()
			.filter(tn->tn.getAccountCode().length()==3).map(tn -> tn.getClosingBalance()).reduce(BigDecimal.ZERO, BigDecimal::add);

	public Function<FinancialRealDatesDTO, FinancialRealDatesDTO> getDateOfYearBefore = dates -> new FinancialRealDatesDTO(
			dates.getStartDate().minus(1, ChronoUnit.YEARS), dates.getEndDate().minus(1, ChronoUnit.YEARS));

	public Function<FinancialRealDatesDTO, String> getDatesInFormatOfRangeString = dates -> dates.getStartDate()
			.getMonth() + " " + dates.getStartDate().getDayOfMonth() + ", " + dates.getStartDate().getYear() + " - "
			+ dates.getEndDate().getMonth() + " " + dates.getEndDate().getDayOfMonth() + ", "
			+ dates.getEndDate().getYear();

	public List<TrialBalanceFileInfoEntity> getFileInfosForState(FinancialRealDatesDTO dates) {
		return tbFileInfoRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatus(
				dates.getStartDate(), dates.getEndDate(), TypeStatusEnum.ACTIVE);
	}

	public List<TrialBalanceFileInfoEntity> getFileInfosForDivision(FinancialRealDatesDTO dates, Long divisionId) {
		Function<Long, List<ULBEntity>> getAllULBsforDivison = divId -> ulbService.getAllULBsForDivision(divId);
		return tbFileInfoRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatusAndUlbIn(
				dates.getStartDate(), dates.getEndDate(), TypeStatusEnum.ACTIVE,
				getAllULBsforDivison.apply(divisionId));
	}

	public List<TrialBalanceFileInfoEntity> getFileInfosForDistrict(FinancialRealDatesDTO dates, Long districtId) {
		Function<Long, List<ULBEntity>> getAllULBsforDistrict = distId -> ulbService.getAllULBsForDistrict(distId);
		return tbFileInfoRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatusAndUlbIn(
				dates.getStartDate(), dates.getEndDate(), TypeStatusEnum.ACTIVE,
				getAllULBsforDistrict.apply(districtId));
	}

	public List<TrialBalanceFileInfoEntity> getFileInfosForULB(FinancialRealDatesDTO dates, ULBEntity ulb) {
		return tbFileInfoRepository.findByUlb_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatus(
				ulb.getId(), dates.getStartDate(), dates.getEndDate(), TypeStatusEnum.ACTIVE);
	}

	public List<LedgerTransactionEntity> getLedgers(List<TrialBalanceFileInfoEntity> files, Integer headCode) {
		return ledgerTransactionRepository.findByTrialBalance_InAndBaseHead_Code(files, headCode);
	}

	public List<LedgerTransactionEntity> getLedgersForFileOnly(TrialBalanceFileInfoEntity file) {
		return ledgerTransactionRepository.findByTrialBalance_Id(file.getId());
	}
}
