package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRCFCGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRCentralCapitalAccountGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRFeesNFinesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRIncomeFromInterestEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRLoansEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROctraiCompensationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherCapitalReceiptsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherCentralGovtTransfersEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherNonTaxRevenueEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherRevenueIncomeEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherStateGovtTransfersEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROtherTaxEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCROthersEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRPropertyTaxEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRSaleOfMunicipalLandEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRStateAssignedReveueEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRStateCapitalAccountsGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRStateFCGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARCRUserChargesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueAndCapitalReceiptsInfoDetailsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalReceiptsAndExpenditure;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalReceiptsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAFinanceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRCFCGrantsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRCentralCapitalAccountGrantsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRFeesNFinesRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRIncomeFromInterestRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRLoansRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROctraiCompensationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROtherCapitalReceiptsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROtherCentralGovtTransfersRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROtherNonTaxRevenueRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROtherRevenueIncomeRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROtherStateGovtTransfersRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROtherTaxRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCROthersRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRPropertyTaxRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRSaleOfMunicipalLandRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRStateAssignedReveueRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRStateCapitalAccountsGrantsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRStateFCGrantsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARCRUserChargesRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARevenueAndCapitalReceiptsInfoDetailsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARevenueNCapitalReceiptsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IInternalAuditRepository;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueAndCapitalReceiptsInfoDetailsDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalReceiptDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalReceiptsAndExpenditureDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalReceiptsEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IReceiptsAndCapitalReceiptsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptsAndCapitalReceiptsService implements IReceiptsAndCapitalReceiptsService {

	private final IIARevenueNCapitalReceiptsRepository revenueAndCapitalReceiptsRepo;
	private final IInternalAuditService internalAuditService;
	private final IInternalAuditRepository iaRepo;
	private final IIAFinanceRepository financeRepo;

	private final IIARevenueAndCapitalReceiptsInfoDetailsRepository revenueAndCapitalReceiptsInfoDetailsRepo;
	private final IIARCRPropertyTaxRepository propertyTaxRepository;
	private final IIARCROtherTaxRepository otherTaxRepository;
	private final IIARCRFeesNFinesRepository feesNFinesRepository;
	private final IIARCRUserChargesRepository userChargesRepository;
	private final IIARCROtherNonTaxRevenueRepository otherNonTaxRevenueRepo;
	private final IIARCRIncomeFromInterestRepository incomeFromInterestRepo;
	private final IIARCROtherRevenueIncomeRepository otherRevenueIncomeRepo;
	private final IIARCRStateAssignedReveueRepository stateAssignedRevenueRepo;
	private final IIARCRStateFCGrantsRepository stateFCGrantsRepo;
	private final IIARCROctraiCompensationRepository octraiCompensationRepo;
	private final IIARCROtherStateGovtTransfersRepository otherStateGovtTransferRepo;
	private final IIARCRCFCGrantsRepository cfcGrantsRepo;
	private final IIARCROtherCentralGovtTransfersRepository otherCentralGovtRepo;
	private final IIARCROthersRepository othersRepo;
	private final IIARCRSaleOfMunicipalLandRepository saleOfMunicipalLandRepo;
	private final IIARCRLoansRepository loansRepo;
	private final IIARCRStateCapitalAccountsGrantsRepository stateCapitalAccountsGrantsRepo;
	private final IIARCRCentralCapitalAccountGrantsRepository centralCapitalAccountGrantsRepo;
	private final IIARCROtherCapitalReceiptsRepository otherCapitalReceiptsRepo;

	@Override
	@Transactional
	public IARevenueNCapitalReceiptDTO createUpdateReceiptsAndCapitalInformation(IARevenueNCapitalReceiptDTO dto) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(dto.getIaId());
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(ia).build());
			ia.setFinance(financeEntity);
			iaRepo.save(ia);
		}
		/*
		 * Long id=null; if(financeEntity.getRevenueNCapitalReceipts()!=null)
		 * id=financeEntity.getRevenueNCapitalReceipts().getId();
		 */
		IARevenueNCapitalReceiptsEntity revenueAndCapitalReceipts = financeEntity.getRevenueNCapitalReceipts();
		if (revenueAndCapitalReceipts == null) {
			revenueAndCapitalReceipts = revenueAndCapitalReceiptsRepo
					.saveAndFlush(IARevenueNCapitalReceiptsEntity.builder().finance(financeEntity).fy1L(dto.getFy1L())
							.fy2L(dto.getFy2L()).fy3L(dto.getFy3L()).build());
		}
		revenueAndCapitalReceipts = getEntity(dto, revenueAndCapitalReceipts, financeEntity);
		revenueAndCapitalReceipts = revenueAndCapitalReceiptsRepo.saveAndFlush(revenueAndCapitalReceipts);
		IARevenueNCapitalReceiptDTO dataO = getDTOFromEntity(revenueAndCapitalReceipts, ia.getId());

		// save references
		IARevenueAndCapitalReceiptsInfoDetailsEntity details = revenueAndCapitalReceipts.getDetails();
		details.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		revenueAndCapitalReceiptsInfoDetailsRepo.save(details);

		IARCRPropertyTaxEntity propertyTax = revenueAndCapitalReceipts.getPropertyTax();
		propertyTax.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		propertyTaxRepository.save(propertyTax);

		IARCROtherTaxEntity otherTax = revenueAndCapitalReceipts.getOtherTax();
		otherTax.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		otherTaxRepository.save(otherTax);

		IARCRFeesNFinesEntity feesNFines = revenueAndCapitalReceipts.getFeesAndFines();
		feesNFines.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		feesNFinesRepository.save(feesNFines);

		IARCRUserChargesEntity userCharges = revenueAndCapitalReceipts.getUserCharges();
		userCharges.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		userChargesRepository.save(userCharges);

		IARCROtherNonTaxRevenueEntity otherNonTaxRevenue = revenueAndCapitalReceipts.getOtherNonTaxRevenue();
		otherNonTaxRevenue.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		otherNonTaxRevenueRepo.save(otherNonTaxRevenue);

		IARCRIncomeFromInterestEntity incomeFromInterest = revenueAndCapitalReceipts.getIncomeFromInterest();
		incomeFromInterest.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		incomeFromInterestRepo.save(incomeFromInterest);

		IARCROtherRevenueIncomeEntity otherRevenueIncome = revenueAndCapitalReceipts.getOtherRevenueIncome();
		otherRevenueIncome.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		otherRevenueIncomeRepo.save(otherRevenueIncome);

		IARCRStateAssignedReveueEntity stateAssignedRevenue = revenueAndCapitalReceipts.getStateAssignedRevenue();
		stateAssignedRevenue.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		stateAssignedRevenueRepo.save(stateAssignedRevenue);

		IARCRStateFCGrantsEntity stateFCGrants = revenueAndCapitalReceipts.getStateFCGrants();
		stateFCGrants.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		stateFCGrantsRepo.save(stateFCGrants);

		IARCROctraiCompensationEntity octraiCompensation = revenueAndCapitalReceipts.getOctraiCompensation();
		octraiCompensation.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		octraiCompensationRepo.save(octraiCompensation);

		IARCROtherStateGovtTransfersEntity otherStateGovtTransfers = revenueAndCapitalReceipts
				.getOtherStateGovtTransfers();
		otherStateGovtTransfers.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		otherStateGovtTransferRepo.save(otherStateGovtTransfers);

		IARCRCFCGrantsEntity cfcGrants = revenueAndCapitalReceipts.getCfcGrants();
		cfcGrants.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		cfcGrantsRepo.save(cfcGrants);

		IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers = revenueAndCapitalReceipts
				.getOtherCentralGovtTransfers();
		otherCentralGovtTransfers.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		otherCentralGovtRepo.save(otherCentralGovtTransfers);

		IARCROthersEntity others = revenueAndCapitalReceipts.getOthers();
		others.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		othersRepo.save(others);

		IARCRSaleOfMunicipalLandEntity saleOfMunicipalLand = revenueAndCapitalReceipts.getSaleOfMunicipalLand();
		saleOfMunicipalLand.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		saleOfMunicipalLandRepo.save(saleOfMunicipalLand);

		IARCRLoansEntity loans = revenueAndCapitalReceipts.getLoans();
		loans.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		loansRepo.save(loans);

		IARCRStateCapitalAccountsGrantsEntity stateCapitalAccountGrants = revenueAndCapitalReceipts
				.getStateCapitalAccountGrants();
		stateCapitalAccountGrants.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		stateCapitalAccountsGrantsRepo.save(stateCapitalAccountGrants);

		IARCRCentralCapitalAccountGrantsEntity centralCapitalAccountGrants = revenueAndCapitalReceipts
				.getCentralCapitalAccountGrants();
		centralCapitalAccountGrants.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		centralCapitalAccountGrantsRepo.save(centralCapitalAccountGrants);

		IARCROtherCapitalReceiptsEntity otherCapitalReceipts = revenueAndCapitalReceipts.getOtherCapitalReceipts();
		otherCapitalReceipts.setRevenueAndCapitalReceipts(revenueAndCapitalReceipts);
		otherCapitalReceiptsRepo.save(otherCapitalReceipts);
		
		return dataO;
	}

	@Transactional
	private IARevenueNCapitalReceiptsEntity getEntity(IARevenueNCapitalReceiptDTO dto,
			IARevenueNCapitalReceiptsEntity en, IAFinanceEntity finance) {
		IARevenueAndCapitalReceiptsInfoDetailsEntity details = getDetails.apply(dto.getDetails(), null);
		IARCRPropertyTaxEntity propertyTax = getPropertyTaxEntity.apply(dto.getPropertyTax());
		IARCROtherTaxEntity otherTax = getOtherTaxEntity.apply(dto.getOtherTax());
		IARCRFeesNFinesEntity feesAndFines = getFeesAndFines.apply(dto.getFeesAndFines());
		IARCRUserChargesEntity userCharges = getUserCharges.apply(dto.getUserCharges());
		IARCROtherNonTaxRevenueEntity otherNonTaxRevenue = getOtherNonTaxRevenue.apply(dto.getOtherNonTaxRevenue());
		IARCRIncomeFromInterestEntity incomeFromInterest = getIncomeFromOtherInterest
				.apply(dto.getIncomeFromInterest());
		IARCROtherRevenueIncomeEntity otherRevenueIncome = getIncomeFromOtherRevenue.apply(dto.getOtherRevenueIncome());
		IARCRStateAssignedReveueEntity stateAssignedRevenue = getStateAssignedRevenue
				.apply(dto.getStateAssignedRevenue());
		IARCRStateFCGrantsEntity stateFCGrants = getStateFCGrants.apply(dto.getStateFCGrants());
		IARCROctraiCompensationEntity octraiCompensation = getOctraiCompensation.apply(dto.getOctraiCompensation());
		IARCROtherStateGovtTransfersEntity otherStateGovtTransfers = getStateGovtTransfers
				.apply(dto.getOtherStateGovtTransfers());
		IARCRCFCGrantsEntity cfcGrants = getCFCGrants.apply(dto.getCfcGrants());
		IARCROtherCentralGovtTransfersEntity otherCentralGovtTransfers = getOtherCentralGovtTransfers
				.apply(dto.getOtherCentralGovtTransfers());
		IARCROthersEntity others = getOtherEntity.apply(dto.getOthers());
		IARCRSaleOfMunicipalLandEntity saleOfMunicipalLand = getSaleOfMunicipalLand.apply(dto.getSaleOfMunicipalLand());
		IARCRLoansEntity loans = getLoans.apply(dto.getLoans());
		IARCRStateCapitalAccountsGrantsEntity stateCapitalAccountGrants = getStateCapitalAccountGrants
				.apply(dto.getStateCapitalAccountGrants());
		IARCRCentralCapitalAccountGrantsEntity centralCapitalAccountGrants = getCentralCapitalAccounts
				.apply(dto.getCentralCapitalAccountGrants());
		IARCROtherCapitalReceiptsEntity otherCapitalReceipts = getOtherCapitalReceipts
				.apply(dto.getOtherCapitalReceipts());
		return IARevenueNCapitalReceiptsEntity.builder().id(en.getId()).fy1L(dto.getFy1L()).fy2L(dto.getFy2L())
				.fy3L(dto.getFy3L()).finance(finance).details(details).propertyTax(propertyTax).otherTax(otherTax)
				.feesAndFines(feesAndFines).userCharges(userCharges).otherNonTaxRevenue(otherNonTaxRevenue)
				.incomeFromInterest(incomeFromInterest).otherRevenueIncome(otherRevenueIncome)
				.stateAssignedRevenue(stateAssignedRevenue).stateFCGrants(stateFCGrants)
				.octraiCompensation(octraiCompensation).otherStateGovtTransfers(otherStateGovtTransfers)
				.cfcGrants(cfcGrants).otherCentralGovtTransfers(otherCentralGovtTransfers).others(others)
				.saleOfMunicipalLand(saleOfMunicipalLand).loans(loans)
				.stateCapitalAccountGrants(stateCapitalAccountGrants)
				.centralCapitalAccountGrants(centralCapitalAccountGrants).otherCapitalReceipts(otherCapitalReceipts)
				.build();
	}

	@Transactional
	private IARevenueNCapitalReceiptDTO getDTOFromEntity(IARevenueNCapitalReceiptsEntity en, Long iaId) {
		return IARevenueNCapitalReceiptDTO.builder().iaId(iaId).id(en.getId()).fy1L(en.getFy1L()).fy2L(en.getFy2L())
				.fy3L(en.getFy3L()).details(getDetailsDTO.apply(en.getDetails(), iaId))
				.propertyTax(getInfo.apply(en.getPropertyTax(), IARevenueNCapitalReceiptsEnum.PROPERTY_TAX))
				.otherTax(getInfo.apply(en.getOtherTax(), IARevenueNCapitalReceiptsEnum.OTHER_TAX))
				.feesAndFines(getInfo.apply(en.getFeesAndFines(), IARevenueNCapitalReceiptsEnum.FEES_AND_FINES))
				.userCharges(getInfo.apply(en.getUserCharges(), IARevenueNCapitalReceiptsEnum.USER_CHARGES))
				.otherNonTaxRevenue(
						getInfo.apply(en.getOtherNonTaxRevenue(), IARevenueNCapitalReceiptsEnum.OTHER_NON_TAX_REVENUE))
				.incomeFromInterest(
						getInfo.apply(en.getIncomeFromInterest(), IARevenueNCapitalReceiptsEnum.INCOME_FROM_INTEREST))
				.otherRevenueIncome(
						getInfo.apply(en.getOtherRevenueIncome(), IARevenueNCapitalReceiptsEnum.OTHER_REVENUE_INCOME))
				.stateAssignedRevenue(getInfo.apply(en.getStateAssignedRevenue(),
						IARevenueNCapitalReceiptsEnum.STATE_ASSIGNED_REVENUE))
				.stateFCGrants(getInfo.apply(en.getStateFCGrants(), IARevenueNCapitalReceiptsEnum.STATE_FC_GRANTS))
				.octraiCompensation(
						getInfo.apply(en.getOctraiCompensation(), IARevenueNCapitalReceiptsEnum.OCTRAI_COMPENSATION))
				.otherStateGovtTransfers(getInfo.apply(en.getOtherStateGovtTransfers(),
						IARevenueNCapitalReceiptsEnum.OTHER_STATE_GOVT_TRANSFERS))
				.cfcGrants(getInfo.apply(en.getCfcGrants(), IARevenueNCapitalReceiptsEnum.CFC_GRANTS))
				.otherCentralGovtTransfers(getInfo.apply(en.getOtherCentralGovtTransfers(),
						IARevenueNCapitalReceiptsEnum.OTHER_CENTRAL_GOVT_TRANSFERS))
				.others(getInfo.apply(en.getOthers(), IARevenueNCapitalReceiptsEnum.OTHERS))
				.saleOfMunicipalLand(getInfo.apply(en.getSaleOfMunicipalLand(),
						IARevenueNCapitalReceiptsEnum.SALES_OF_MUNICIPAL_LAND))
				.loans(getInfo.apply(en.getLoans(), IARevenueNCapitalReceiptsEnum.LOANS))
				.stateCapitalAccountGrants(getInfo.apply(en.getStateCapitalAccountGrants(),
						IARevenueNCapitalReceiptsEnum.STATE_CAPITAL_ACCOUNT_GRANTS))
				.centralCapitalAccountGrants(getInfo.apply(en.getCentralCapitalAccountGrants(),
						IARevenueNCapitalReceiptsEnum.CENTRAL_CAPITAL_ACCOUNT_GRANTS))
				.otherCapitalReceipts(getInfo.apply(en.getOtherCapitalReceipts(),
						IARevenueNCapitalReceiptsEnum.OTHER_CAPITAL_RECEIPTS))
				.build();
		// .details()
	}

	private BiFunction<IARevenueNCapitalReceiptsAndExpenditure, IARevenueNCapitalReceiptsEnum, IARevenueNCapitalReceiptsAndExpenditureDTO> getInfo = (
			en, type) -> IARevenueNCapitalReceiptsAndExpenditureDTO.builder().id(en.getId()).fy1Amt(en.getFy1Amt())
					.fy2Amt(en.getFy2Amt()).fy3Amt(en.getFy3Amt()).fy4Amt(en.getFy4Amt()).fy5Amt(en.getFy5Amt())
					.fy6Amt(en.getFy6Amt()).type(type).build();
	private BiFunction<IARevenueAndCapitalReceiptsInfoDetailsEntity, Long, IARevenueAndCapitalReceiptsInfoDetailsDTO> getDetailsDTO = (
			en, ia) -> IARevenueAndCapitalReceiptsInfoDetailsDTO.builder().iaId(ia).id(en.getId()).fy1(en.getFy1())
					.fy2(en.getFy2()).fy3(en.getFy3()).fy4(en.getFy4()).fy5(en.getFy5()).fy6(en.getFy6()).build();

	private BiFunction<IARevenueAndCapitalReceiptsInfoDetailsDTO, IARevenueNCapitalReceiptsEntity, IARevenueAndCapitalReceiptsInfoDetailsEntity> getDetails = (
			dto, r) -> IARevenueAndCapitalReceiptsInfoDetailsEntity.builder().id(dto.getId()).fy1(dto.getFy1())
					.fy2(dto.getFy2()).fy3(dto.getFy3()).fy4(dto.getFy4()).fy5(dto.getFy5()).fy6(dto.getFy6())
					.revenueAndCapitalReceipts(r).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRPropertyTaxEntity> getPropertyTaxEntity = dto -> IARCRPropertyTaxEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROtherTaxEntity> getOtherTaxEntity = dto -> IARCROtherTaxEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRFeesNFinesEntity> getFeesAndFines = dto -> IARCRFeesNFinesEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRUserChargesEntity> getUserCharges = dto -> IARCRUserChargesEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROtherNonTaxRevenueEntity> getOtherNonTaxRevenue = dto -> IARCROtherNonTaxRevenueEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRIncomeFromInterestEntity> getIncomeFromOtherInterest = dto -> IARCRIncomeFromInterestEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROtherRevenueIncomeEntity> getIncomeFromOtherRevenue = dto -> IARCROtherRevenueIncomeEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRStateAssignedReveueEntity> getStateAssignedRevenue = dto -> IARCRStateAssignedReveueEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRStateFCGrantsEntity> getStateFCGrants = dto -> IARCRStateFCGrantsEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROctraiCompensationEntity> getOctraiCompensation = dto -> IARCROctraiCompensationEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROtherStateGovtTransfersEntity> getStateGovtTransfers = dto -> IARCROtherStateGovtTransfersEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRCFCGrantsEntity> getCFCGrants = dto -> IARCRCFCGrantsEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROtherCentralGovtTransfersEntity> getOtherCentralGovtTransfers = dto -> IARCROtherCentralGovtTransfersEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROthersEntity> getOtherEntity = dto -> IARCROthersEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRSaleOfMunicipalLandEntity> getSaleOfMunicipalLand = dto -> IARCRSaleOfMunicipalLandEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRLoansEntity> getLoans = dto -> IARCRLoansEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRStateCapitalAccountsGrantsEntity> getStateCapitalAccountGrants = dto -> IARCRStateCapitalAccountsGrantsEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCRCentralCapitalAccountGrantsEntity> getCentralCapitalAccounts = dto -> IARCRCentralCapitalAccountGrantsEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IARCROtherCapitalReceiptsEntity> getOtherCapitalReceipts = dto -> IARCROtherCapitalReceiptsEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();

	@Override
	public IARevenueNCapitalReceiptDTO findReceiptsAndCapitalInformationByIaId(Long iaId) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			return null;
		}

		IARevenueNCapitalReceiptsEntity revenueAndCapitalReceipts = financeEntity.getRevenueNCapitalReceipts();
		if (revenueAndCapitalReceipts == null)
			return null;
		return getDTOFromEntity(revenueAndCapitalReceipts, ia.getId());

	}

}
