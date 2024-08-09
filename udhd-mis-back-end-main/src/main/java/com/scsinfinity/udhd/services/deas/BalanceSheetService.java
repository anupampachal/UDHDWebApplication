package com.scsinfinity.udhd.services.deas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.deas.BalanceSheetEntity;
import com.scsinfinity.udhd.dao.entities.deas.LedgerTransactionEntity;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceFileInfoEntity;
import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;
import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.repositories.deas.IBalanceSheetRepository;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.deas.dto.BalanceSheetAssetOrLiabilityEnum;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetDistrictLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetDivisionLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetLineItemDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetMajorCodeLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetStateLevelDataDTO;
import com.scsinfinity.udhd.services.deas.dto.DeasBalanceSheetULBLevelDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IBalanceSheetService;
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDistrictService;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDivisionService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceSheetService implements IBalanceSheetService {

	private final DeasServices deasServices;
	private final IULBService ulbService;
	private final IGeoDistrictService districtService;
	private final IGeoDivisionService divisionService;
	private final IBalanceSheetRepository balanceSheetRepo;

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasBalanceSheetStateLevelDataDTO getBalanceSheetAtStateLevel(LocalDate date) {
		log.debug("getBalanceSheetAtStateLevel");
		LocalDate previousDate = date.minus(1, ChronoUnit.YEARS);
		List<GeoDivisionEntity> geoDivisionList = divisionService.getAllActiveGeoDivisionEntity();
		List<DeasBalanceSheetLineItemDTO> divLevelBsIndividual = getDivisionLevelBalanceSheetForEachULB(geoDivisionList,
				date, previousDate);
		DeasBalanceSheetLineItemDTO divisionLevelBalanceSheet = getStateLevelBalanceSheet(divLevelBsIndividual, date,
				previousDate);
		List<DeasBalanceSheetMajorCodeLevelDataDTO> majorHeadLevelBalanceDetails = getMajorHeadLevelBalanceDetails();

		return DeasBalanceSheetStateLevelDataDTO.builder().stateLevelBalanceSheet(divisionLevelBalanceSheet)
				.majorHeadLevelBalanceDetails(majorHeadLevelBalanceDetails)
				.divisionLevelBalanceSheet(divLevelBsIndividual).build();

	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasBalanceSheetDivisionLevelDataDTO getBalanceSheetAtDivisionLevel(Long divisionId, LocalDate date) {
		log.debug("getBalanceSheetAtDivisionLevel");
		LocalDate previousDate = date.minus(1, ChronoUnit.YEARS);
		List<GeoDistrictEntity> districts = districtService.getGeoDistrictEntityForDivision(divisionId);
		GeoDivisionDTO division = divisionService.findByGeoDivisionId(divisionId);
		List<DeasBalanceSheetLineItemDTO> districtLevelIndividualBalanceSheet = getBalanceSheetForEachDistrict(
				districts, date, previousDate);
		DeasBalanceSheetLineItemDTO divisionLevelBalanceSheet = getDivisionLevelBalanceSheet(
				districtLevelIndividualBalanceSheet, date, previousDate, division);
		List<DeasBalanceSheetMajorCodeLevelDataDTO> majorHeadLevelBalanceDetails = getMajorHeadLevelBalanceDetails();

		return DeasBalanceSheetDivisionLevelDataDTO.builder().divisionLevelBalanceSheet(divisionLevelBalanceSheet)
				.majorHeadLevelBalanceDetails(majorHeadLevelBalanceDetails)
				.districtLevelBalanceSheetIndividual(districtLevelIndividualBalanceSheet).build();
	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasBalanceSheetDistrictLevelDataDTO getBalanceSheetAtDistrictLevel(Long districtId, LocalDate date) {
		log.debug("getBalanceSheetAtDistrictLevel");
		List<ULBEntity> ulbs = ulbService.getAllULBsForDistrict(districtId);
		LocalDate previousDate = date.minus(1, ChronoUnit.YEARS);

		List<DeasBalanceSheetLineItemDTO> ulbLevelBalanceSheetIndividual = getULBLevelBalanceSheetForEachULB(ulbs, date,
				previousDate);
		DeasBalanceSheetLineItemDTO districtLevelBalanceSheet = getDistrictLevelBalanceSheet(
				ulbLevelBalanceSheetIndividual, date, previousDate);
		List<DeasBalanceSheetMajorCodeLevelDataDTO> majorHeadLevelBalanceDetails = getMajorHeadLevelBalanceDetails();

		return DeasBalanceSheetDistrictLevelDataDTO.builder().districtLevelBalanceSheet(districtLevelBalanceSheet)
				.majorHeadLevelBalanceDetails(majorHeadLevelBalanceDetails)
				.ulbLevelBalanceSheetIndividual(ulbLevelBalanceSheetIndividual).build();
	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public DeasBalanceSheetULBLevelDataDTO getBalanceSheetAtULBLevel(Long ulbId, LocalDate date) {
		log.debug("getBalanceSheetAtULBLevel");

		ULBEntity ulb = ulbService.findULBEntityById(ulbId);
		Optional<BalanceSheetEntity> balanceSheetEnO = balanceSheetRepo.findByGivenCriteria(date, ulb.getId());
		Optional<BalanceSheetEntity> balanceSheetEnPO = balanceSheetRepo
				.findByGivenCriteria(date.minus(1, ChronoUnit.YEARS), ulb.getId());
		DeasBalanceSheetLineItemDTO ulbLevelBalanceSheet = getDeasBalanceSheetAtULBLevel(ulb, date, balanceSheetEnO,
				balanceSheetEnPO);
		List<DeasBalanceSheetMajorCodeLevelDataDTO> majorHeadLevelBalanceDetails = getMajorCodeLevelData(ulb,
				balanceSheetEnO, balanceSheetEnPO);

		return DeasBalanceSheetULBLevelDataDTO.builder().majorHeadLevelBalanceDetails(majorHeadLevelBalanceDetails)
				.ulbLevelBalanceSheet(ulbLevelBalanceSheet).build();

	}
	private List<DeasBalanceSheetMajorCodeLevelDataDTO> getMajorHeadLevelBalanceDetails() {
		return null;
	}

	

	private DeasBalanceSheetLineItemDTO getStateLevelBalanceSheet(List<DeasBalanceSheetLineItemDTO> divBs,
			LocalDate currentDate, LocalDate previousDate) {
		return DeasBalanceSheetLineItemDTO.builder().asCurrentOn(currentDate).asPreviousOn(previousDate).id(null)
				.particulars("Bihar")
				.latestAssets(
						divBs.stream().map(dist -> dist.getLatestAssets()).reduce(BigDecimal.ZERO, BigDecimal::add))
				.latestLiabilities(divBs.stream().map(dist -> dist.getLatestLiabilities()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.previousAssets(
						divBs.stream().map(dist -> dist.getPreviousAssets()).reduce(BigDecimal.ZERO, BigDecimal::add))
				.previousLiabilities(divBs.stream().map(dist -> dist.getPreviousLiabilities()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.build();
	}

	private DeasBalanceSheetLineItemDTO getDivisionLevelBalanceSheet(List<DeasBalanceSheetLineItemDTO> distBs,
			LocalDate currentDate, LocalDate previousDate, GeoDivisionDTO division) {
		return DeasBalanceSheetLineItemDTO.builder().asCurrentOn(currentDate).asPreviousOn(previousDate)
				.id(division.getId()).particulars(division.getName())
				.latestAssets(
						distBs.stream().map(dist -> dist.getLatestAssets()).reduce(BigDecimal.ZERO, BigDecimal::add))
				.latestLiabilities(distBs.stream().map(dist -> dist.getLatestLiabilities()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.previousAssets(
						distBs.stream().map(dist -> dist.getPreviousAssets()).reduce(BigDecimal.ZERO, BigDecimal::add))
				.previousLiabilities(distBs.stream().map(dist -> dist.getPreviousLiabilities()).reduce(BigDecimal.ZERO,
						BigDecimal::add))
				.build();
	}

	private DeasBalanceSheetLineItemDTO getDistrictLevelBalanceSheet(
			List<DeasBalanceSheetLineItemDTO> ulbLevelBalanceSheetIndividual, LocalDate currentDate,
			LocalDate previousDate) {
		DeasBalanceSheetLineItemDTO districtBS = new DeasBalanceSheetLineItemDTO();

		districtBS.setLatestAssets(
				ulbLevelBalanceSheetIndividual.stream().filter(data -> data != null && data.getLatestAssets() != null)
						.map(data -> data.getLatestAssets()).reduce(BigDecimal.ZERO, BigDecimal::add));
		districtBS.setLatestLiabilities(ulbLevelBalanceSheetIndividual.stream()
				.filter(data -> data != null && data.getLatestLiabilities() != null)
				.map(data -> data.getLatestLiabilities()).reduce(BigDecimal.ZERO, BigDecimal::add));
		districtBS.setPreviousAssets(
				ulbLevelBalanceSheetIndividual.stream().filter(data -> data != null && data.getPreviousAssets() != null)
						.map(data -> data.getPreviousAssets()).reduce(BigDecimal.ZERO, BigDecimal::add));
		districtBS.setPreviousLiabilities(
				ulbLevelBalanceSheetIndividual.stream().filter(data -> data != null && data.getPreviousAssets() != null)
						.map(data -> data.getPreviousLiabilities()).reduce(BigDecimal.ZERO, BigDecimal::add));
		districtBS.setAsCurrentOn(currentDate);
		districtBS.setAsPreviousOn(previousDate);
		districtBS.setId(0L);
		districtBS.setParticulars("District Level BS");
		return districtBS;
	}

	private List<DeasBalanceSheetLineItemDTO> getDivisionLevelBalanceSheetForEachULB(List<GeoDivisionEntity> divisions,
			LocalDate date, LocalDate previousDate) {
		Map<GeoDivisionEntity, DeasBalanceSheetLineItemDTO> divisionBSMap = new HashMap<>();
		divisions.forEach(division -> {
			List<ULBEntity> ulbsForDivision = ulbService.getAllULBsForDivision(division.getId());
			List<DeasBalanceSheetLineItemDTO> bsListForEachULB = getULBLevelBalanceSheetForEachULB(ulbsForDivision,
					date, previousDate);
			divisionBSMap.put(division,
					getBSForGroup(bsListForEachULB, date, previousDate, division.getId(), division.getName()));
		});
		return divisions.stream().map(div -> divisionBSMap.get(div)).collect(Collectors.toList());

	}

	private List<DeasBalanceSheetLineItemDTO> getBalanceSheetForEachDistrict(List<GeoDistrictEntity> districts,
			LocalDate date, LocalDate previousDate) {
		/*Map<GeoDistrictEntity, DeasBalanceSheetLineItemDTO> districtBSMap = new HashMap<>();
		districts.forEach(district -> {
			List<ULBEntity> ulbsForDistrict = ulbService.getAllULBsForDistrict(district.getId());
			List<DeasBalanceSheetLineItemDTO> bsListForEachULB = getULBLevelBalanceSheetForEachULB(ulbsForDistrict,
					date, previousDate);
			districtBSMap.put(district,
					getBSForGroup(bsListForEachULB, date, previousDate, district.getId(), district.getName()));
		});*/
		
		return districts.stream().map(dist ->getDistrictLevelBSFromBSLIst(dist,date,previousDate)).collect(Collectors.toList());
	}

	private DeasBalanceSheetLineItemDTO getDistrictLevelBSFromBSLIst(GeoDistrictEntity district, LocalDate date,
			LocalDate previousDate) {
		List<ULBEntity> ulbsForDistrict = ulbService.getAllULBsForDistrict(district.getId());
		List<DeasBalanceSheetLineItemDTO> bsListForEachULB = getULBLevelBalanceSheetForEachULB(ulbsForDistrict, date,
				previousDate);

		return getBSForGroup(bsListForEachULB, date, previousDate, district.getId(), district.getName());
	}

	private DeasBalanceSheetLineItemDTO getBSForGroup(List<DeasBalanceSheetLineItemDTO> bsList, LocalDate currentDate,
			LocalDate previousDate, Long id, String particulars) {
		DeasBalanceSheetLineItemDTO bsLineItem = new DeasBalanceSheetLineItemDTO();
		bsLineItem.setAsCurrentOn(currentDate);
		bsLineItem.setAsPreviousOn(previousDate);
		bsLineItem.setId(id);
		bsLineItem.setParticulars(particulars);
		bsLineItem.setLatestAssets(
				bsList.stream().filter(bs->bs.getLatestAssets()!=null).map(bs -> bs.getLatestAssets()).reduce(BigDecimal.ZERO, BigDecimal::add));
		bsLineItem.setLatestLiabilities(
				bsList.stream().filter(bs->bs.getLatestLiabilities()!=null).map(bs -> bs.getLatestLiabilities()).reduce(BigDecimal.ZERO, BigDecimal::add));
		bsLineItem.setPreviousAssets(
				bsList.stream().filter(bs->bs.getPreviousAssets()!=null).map(bs -> bs.getPreviousAssets()).reduce(BigDecimal.ZERO, BigDecimal::add));
		bsLineItem.setPreviousLiabilities(
				bsList.stream().filter(bs->bs.getPreviousAssets()!=null).map(bs -> bs.getPreviousLiabilities()).reduce(BigDecimal.ZERO, BigDecimal::add));
		return bsLineItem;
	}

	private List<DeasBalanceSheetLineItemDTO> getULBLevelBalanceSheetForEachULB(List<ULBEntity> ulbs, LocalDate date,
			LocalDate previousDate) {
		List<BalanceSheetEntity> balanceSheetsForAllULBsCurrent = balanceSheetRepo
				.findByActiveAndDateTillLessThanEqualAndUlbIn(true, date, ulbs);
		// long is for ulbid which will be key here
		Map<Long, BalanceSheetEntity> currentBSMap = findBalanceSheetForEachULB(ulbs, balanceSheetsForAllULBsCurrent,
				date);

		List<BalanceSheetEntity> balanceSheetsForAllULBsPrevious = balanceSheetRepo
				.findByActiveAndDateTillLessThanEqualAndUlbIn(true, previousDate, ulbs);
		Map<Long, BalanceSheetEntity> prevBSMap = findBalanceSheetForEachULB(ulbs, balanceSheetsForAllULBsPrevious,
				date);

		return ulbs.stream().map(ulb -> getBSLineItemDTO(currentBSMap, prevBSMap, ulb, date, previousDate))
				.collect(Collectors.toList());
	}

	private Map<Long, BalanceSheetEntity> findBalanceSheetForEachULB(List<ULBEntity> ulbs, List<BalanceSheetEntity> bs,
			LocalDate dateTill) {
		Map<Long, BalanceSheetEntity> bsMap = new HashMap<>();
		Comparator<BalanceSheetEntity> localDateComp = Comparator.comparing(BalanceSheetEntity::getDateTill).reversed();
		Collections.sort(bs, localDateComp);
		bs.stream().filter(datad -> bsMap.get(datad.getUlb().getId()) == null)
				.filter(datad -> ulbs.contains(datad.getUlb()))
				.filter(datad -> datad.getDateTill().isBefore(dateTill) || datad.getDateTill().equals(dateTill))
				.map(datad -> bsMap.put(datad.getUlb().getId(), datad)).collect(Collectors.counting());

		return bsMap;
	}

	private DeasBalanceSheetLineItemDTO getBSLineItemDTO(Map<Long, BalanceSheetEntity> currentBSMap,
			Map<Long, BalanceSheetEntity> prevBSMap, ULBEntity ulb, LocalDate currentOn, LocalDate previousOn) {
		return DeasBalanceSheetLineItemDTO.builder().asCurrentOn(currentOn).asPreviousOn(previousOn).id(ulb.getId())
				.particulars(ulb.getName())
				.latestAssets(
						currentBSMap.get(ulb.getId()) != null ? currentBSMap.get(ulb.getId()).getTotalAssets() : null)
				.latestLiabilities(
						currentBSMap.get(ulb.getId()) != null ? currentBSMap.get(ulb.getId()).getTotalLiabilities()
								: null)
				.previousAssets(prevBSMap.get(ulb.getId()) != null ? prevBSMap.get(ulb.getId()).getTotalAssets() : null)
				.previousLiabilities(
						prevBSMap.get(ulb.getId()) != null ? prevBSMap.get(ulb.getId()).getTotalAssets() : null)
				.build();
	}

	private DeasBalanceSheetLineItemDTO getDeasBalanceSheetAtULBLevel(ULBEntity ulb, LocalDate date,
			Optional<BalanceSheetEntity> balanceSheetEnO, Optional<BalanceSheetEntity> previousBalanceSheetO) {

		LocalDate previousPeriod = date.minus(1, ChronoUnit.YEARS);

		if (balanceSheetEnO.isPresent()) {

			return DeasBalanceSheetLineItemDTO.builder().asCurrentOn(date).asPreviousOn(previousPeriod)
					.id(balanceSheetEnO.get().getId()).latestAssets(balanceSheetEnO.get().getTotalAssets())
					.latestLiabilities(balanceSheetEnO.get().getTotalLiabilities())
					.particulars(balanceSheetEnO.get().getName())
					.previousAssets(
							previousBalanceSheetO.isPresent() ? previousBalanceSheetO.get().getTotalAssets() : null)
					.previousLiabilities(
							previousBalanceSheetO.isPresent() ? previousBalanceSheetO.get().getTotalLiabilities()
									: null)
					.build();
		}
		return null;
	}

	private List<DeasBalanceSheetMajorCodeLevelDataDTO> getMajorCodeLevelData(ULBEntity ulb,
			Optional<BalanceSheetEntity> currentBalanceSheetO, Optional<BalanceSheetEntity> previousBalanceSheetO) {
		if (currentBalanceSheetO.isPresent()) {
			TrialBalanceFileInfoEntity tbfo = currentBalanceSheetO.get().getTrialBalance();
			List<LedgerTransactionEntity> ledgerTran = deasServices.getLedgersForFileOnly(tbfo);
			List<LedgerTransactionEntity> previousLedgerTran = null;
			Map<String, LedgerTransactionEntity> codeWiseMap = new HashMap<>();
			if (previousBalanceSheetO.isPresent()) {
				previousLedgerTran = deasServices.getLedgersForFileOnly(previousBalanceSheetO.get().getTrialBalance());
				previousLedgerTran.stream().map(ledgerP -> codeWiseMap.put(ledgerP.getAccountCode(), ledgerP));
			}

			return ledgerTran.stream()
					.filter(ledger -> ledger.getAccountCode().length() == 3
							&& (ledger.getAccountCode().startsWith("3") || ledger.getAccountCode().startsWith("4")))
					.map(ledger -> DeasBalanceSheetMajorCodeLevelDataDTO.builder().alias(ledger.getName())
							.assetOrLiabilityEnum(getLiabilityOrAsset(ledger.getAccountCode()))
							.code(ledger.getAccountCode()).currentAmout(ledger.getClosingBalance())
							.previousAmount(getPreviousAmt(ledger.getAccountCode(), codeWiseMap)).build())
					.collect(Collectors.toList());
		}

		return null;
	}

	private BigDecimal getPreviousAmt(String accCode, Map<String, LedgerTransactionEntity> codeWiseMap) {
		LedgerTransactionEntity acc = codeWiseMap.get(accCode);
		if (acc == null)
			return BigDecimal.ZERO;
		else
			return acc.getClosingBalance();
	}

	private BalanceSheetAssetOrLiabilityEnum getLiabilityOrAsset(String accCode) {
		if (accCode.startsWith("3"))
			return BalanceSheetAssetOrLiabilityEnum.LIABILITY;
		else if (accCode.startsWith("4"))
			return BalanceSheetAssetOrLiabilityEnum.ASSET;
		return null;
	}

}
