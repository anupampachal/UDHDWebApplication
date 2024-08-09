package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAdministrativeExpensesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAllDevelopmentWorksEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IALoanRepaymentEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAOperationAndMaintenanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAOtherCapitalExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAOtherExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueAndCapitalReceiptsInfoDetailsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalReceiptsAndExpenditure;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARevenueNCapitalReceiptsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAdministrativeExpensesRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAllDevelopmentWorksRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAFinanceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIALoanRepaymentRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAOperationAndMaintenanceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAOtherCapitalExpenditureRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAOtherExpenditureRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARevenueAndCapitalReceiptsInfoDetailsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARevenueNCapitalExpenditureRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IInternalAuditRepository;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueAndCapitalReceiptsInfoDetailsDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalExpenditureDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalExpenditureEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalReceiptsAndExpenditureDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IReceiptsAndCapitalExpenditureService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptsAndCapitalExpenditureService implements IReceiptsAndCapitalExpenditureService {

	private final IInternalAuditService internalAuditService;
	private final IInternalAuditRepository iaRepo;
	private final IIAFinanceRepository financeRepo;
	private final IIARevenueNCapitalExpenditureRepository revenueAndCapitalExpenditureRepo;

	private final IIARevenueAndCapitalReceiptsInfoDetailsRepository detailsRepo;
	private final IIAAdministrativeExpensesRepository administrativeExpensesRepo;
	private final IIAOperationAndMaintenanceRepository operationAndMaintenanceRepo;
	private final IIALoanRepaymentRepository loanRepaymentRepo;
	private final IIAOtherExpenditureRepository otherExpenditureRepo;
	private final IIAAllDevelopmentWorksRepository developmentWorksRepo;
	private final IIAOtherCapitalExpenditureRepository otherCapitalExpenditureRepo;

	@Override
	@Transactional
	public IARevenueNCapitalExpenditureDTO createUpdateReceiptsAndCapitalExpenditureInformation(
			IARevenueNCapitalExpenditureDTO dto) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(dto.getIaId());
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(ia).build());
			ia.setFinance(financeEntity);
			iaRepo.save(ia);
		}
		IARevenueNCapitalExpenditureEntity revenueAndCapitalExpenditure = financeEntity.getRevenueNCapitalExpenditure();
		if (revenueAndCapitalExpenditure == null) {
			revenueAndCapitalExpenditure = revenueAndCapitalExpenditureRepo
					.saveAndFlush(IARevenueNCapitalExpenditureEntity.builder().finance(financeEntity)
							.fy1L(dto.getFy1L()).fy2L(dto.getFy2L()).fy3L(dto.getFy3L()).build());
		}
		revenueAndCapitalExpenditure = getEntity(dto, revenueAndCapitalExpenditure, financeEntity);
		revenueAndCapitalExpenditure = revenueAndCapitalExpenditureRepo.saveAndFlush(revenueAndCapitalExpenditure);

		IARevenueNCapitalExpenditureDTO dataO = getDTOFromEntity(revenueAndCapitalExpenditure, ia.getId());

		IARevenueAndCapitalReceiptsInfoDetailsEntity details = revenueAndCapitalExpenditure.getDetails();
		details.setRevenueAndCapitalExpenditure(revenueAndCapitalExpenditure);
		details = detailsRepo.save(details);
		revenueAndCapitalExpenditure.setDetails(details);

		IAAdministrativeExpensesEntity administrativeExpenses = revenueAndCapitalExpenditure
				.getAdministrativeExpenses();
		administrativeExpenses.setRevenueAndCapitalExpenditure(revenueAndCapitalExpenditure);
		administrativeExpenses = administrativeExpensesRepo.save(administrativeExpenses);
		revenueAndCapitalExpenditure.setAdministrativeExpenses(administrativeExpenses);

		IAOperationAndMaintenanceEntity operationAndMaintenance = revenueAndCapitalExpenditure
				.getOperationAndMaintenance();
		operationAndMaintenance.setRevenueAndCapitalExpenditure(revenueAndCapitalExpenditure);
		operationAndMaintenance = operationAndMaintenanceRepo.save(operationAndMaintenance);
		revenueAndCapitalExpenditure.setOperationAndMaintenance(operationAndMaintenance);

		IALoanRepaymentEntity loanRepayment = revenueAndCapitalExpenditure.getLoanRepayment();
		loanRepayment.setRevenueAndCapitalExpenditure(revenueAndCapitalExpenditure);
		loanRepayment = loanRepaymentRepo.save(loanRepayment);
		revenueAndCapitalExpenditure.setLoanRepayment(loanRepayment);

		IAOtherExpenditureEntity otherExpenditure = revenueAndCapitalExpenditure.getOtherExpenditure();
		otherExpenditure.setRevenueAndCapitalExpenditure(revenueAndCapitalExpenditure);
		otherExpenditure = otherExpenditureRepo.save(otherExpenditure);
		revenueAndCapitalExpenditure.setOtherExpenditure(otherExpenditure);

		IAAllDevelopmentWorksEntity developmentWorks = revenueAndCapitalExpenditure.getDevelopmentWorks();
		developmentWorks.setRevenueAndCapitalExpenditure(revenueAndCapitalExpenditure);
		developmentWorks = developmentWorksRepo.save(developmentWorks);
		revenueAndCapitalExpenditure.setDevelopmentWorks(developmentWorks);

		IAOtherCapitalExpenditureEntity otherCapitalExpenditure = revenueAndCapitalExpenditure
				.getOtherCapitalExpenditure();
		otherCapitalExpenditure.setRevenueAndCapitalExpenditure(revenueAndCapitalExpenditure);
		otherCapitalExpenditure = otherCapitalExpenditureRepo.save(otherCapitalExpenditure);
		revenueAndCapitalExpenditure.setOtherCapitalExpenditure(otherCapitalExpenditure);

		revenueAndCapitalExpenditureRepo.save(revenueAndCapitalExpenditure);
		return dataO;
	}

	@Transactional
	private IARevenueNCapitalExpenditureEntity getEntity(IARevenueNCapitalExpenditureDTO dto,
			IARevenueNCapitalExpenditureEntity en, IAFinanceEntity finance) {
		IARevenueAndCapitalReceiptsInfoDetailsEntity details = getDetails.apply(dto.getDetails(), null);

		IAAdministrativeExpensesEntity administrativeExpenses = getAdministrativeExpensesEntity
				.apply(dto.getAdministrativeExpenses());

		IAOperationAndMaintenanceEntity operationAndMaintenance = getOperationAndMaintenanceEntity
				.apply(dto.getOperationAndMaintenance());

		IALoanRepaymentEntity loanRepayment = getLoanRepaymentEntity.apply(dto.getLoanRepayment());

		IAOtherExpenditureEntity otherExpenditure = getOtherExpenditureEntity.apply(dto.getOtherExpenditure());

		IAAllDevelopmentWorksEntity developmentWorks = getAllDevelopmentWorksEntity.apply(dto.getDevelopmentWorks());

		IAOtherCapitalExpenditureEntity otherCapitalExpenditure = getCapitalExpenditureEntity
				.apply(dto.getOtherCapitalExpenditure());
		return IARevenueNCapitalExpenditureEntity.builder().id(en.getId()).finance(finance).fy1L(dto.getFy1L())
				.fy2L(dto.getFy2L()).fy3L(dto.getFy3L()).administrativeExpenses(administrativeExpenses).details(details)
				.developmentWorks(developmentWorks).loanRepayment(loanRepayment)
				.operationAndMaintenance(operationAndMaintenance).otherCapitalExpenditure(otherCapitalExpenditure)
				.otherExpenditure(otherExpenditure).build();
	}

	@Transactional
	private IARevenueNCapitalExpenditureDTO getDTOFromEntity(IARevenueNCapitalExpenditureEntity en, Long iaId) {
		return IARevenueNCapitalExpenditureDTO.builder().iaId(iaId).id(en.getId()).fy1L(en.getFy1L()).fy2L(en.getFy2L())
				.fy3L(en.getFy3L()).details(getDetailsDTO.apply(en.getDetails(), iaId))
				.administrativeExpenses(getInfo.apply(en.getAdministrativeExpenses(),
						IARevenueNCapitalExpenditureEnum.ADMINISTRATIVE_EXPENSES))
				.operationAndMaintenance(getInfo.apply(en.getOperationAndMaintenance(),
						IARevenueNCapitalExpenditureEnum.OPERATION_AND_MAINTENANCE))
				.loanRepayment(getInfo.apply(en.getLoanRepayment(), IARevenueNCapitalExpenditureEnum.LOAN_REPAYMENT))
				.otherExpenditure(
						getInfo.apply(en.getOtherExpenditure(), IARevenueNCapitalExpenditureEnum.OTHER_EXPENDITURE))
				.developmentWorks(
						getInfo.apply(en.getDevelopmentWorks(), IARevenueNCapitalExpenditureEnum.DEVELOPMENT_WORKS))
				.otherCapitalExpenditure(getInfo.apply(en.getOtherCapitalExpenditure(),
						IARevenueNCapitalExpenditureEnum.OTHER_CAPITAL_EXPENDITURE))
				.build();
	}

	private BiFunction<IARevenueNCapitalReceiptsAndExpenditure, IARevenueNCapitalExpenditureEnum, IARevenueNCapitalReceiptsAndExpenditureDTO> getInfo = (
			en, type) -> IARevenueNCapitalReceiptsAndExpenditureDTO.builder().id(en.getId()).fy1Amt(en.getFy1Amt())
					.fy2Amt(en.getFy2Amt()).fy3Amt(en.getFy3Amt()).fy4Amt(en.getFy4Amt()).fy5Amt(en.getFy5Amt())
					.fy6Amt(en.getFy6Amt()).expenditureType(type).build();

	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IAAdministrativeExpensesEntity> getAdministrativeExpensesEntity = dto -> IAAdministrativeExpensesEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IAOperationAndMaintenanceEntity> getOperationAndMaintenanceEntity = dto -> IAOperationAndMaintenanceEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IALoanRepaymentEntity> getLoanRepaymentEntity = dto -> IALoanRepaymentEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IAOtherExpenditureEntity> getOtherExpenditureEntity = dto -> IAOtherExpenditureEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IAAllDevelopmentWorksEntity> getAllDevelopmentWorksEntity = dto -> IAAllDevelopmentWorksEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();
	private Function<IARevenueNCapitalReceiptsAndExpenditureDTO, IAOtherCapitalExpenditureEntity> getCapitalExpenditureEntity = dto -> IAOtherCapitalExpenditureEntity
			.builder().id(dto.getId()).fy1Amt(dto.getFy1Amt()).fy2Amt(dto.getFy2Amt()).fy3Amt(dto.getFy3Amt())
			.fy4Amt(dto.getFy4Amt()).fy5Amt(dto.getFy5Amt()).fy6Amt(dto.getFy6Amt()).build();

	private BiFunction<IARevenueAndCapitalReceiptsInfoDetailsDTO, IARevenueNCapitalReceiptsEntity, IARevenueAndCapitalReceiptsInfoDetailsEntity> getDetails = (
			dto, r) -> IARevenueAndCapitalReceiptsInfoDetailsEntity.builder().id(dto.getId()).fy1(dto.getFy1())
					.fy2(dto.getFy2()).fy3(dto.getFy3()).fy4(dto.getFy4()).fy5(dto.getFy5()).fy6(dto.getFy6())
					.revenueAndCapitalReceipts(r).build();
	private BiFunction<IARevenueAndCapitalReceiptsInfoDetailsEntity, Long, IARevenueAndCapitalReceiptsInfoDetailsDTO> getDetailsDTO = (
			en, ia) -> IARevenueAndCapitalReceiptsInfoDetailsDTO.builder().iaId(ia).id(en.getId()).fy1(en.getFy1())
					.fy2(en.getFy2()).fy3(en.getFy3()).fy4(en.getFy4()).fy5(en.getFy5()).fy6(en.getFy6()).build();

	@Override
	public IARevenueNCapitalExpenditureDTO findReceiptsAndCapitalExpenditureInformationByIaId(Long iaId) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			return null;
		}

		IARevenueNCapitalExpenditureEntity revenueAndCapitalExpenditure = financeEntity.getRevenueNCapitalExpenditure();
		if (revenueAndCapitalExpenditure == null)
			return null;
		return getDTOFromEntity(revenueAndCapitalExpenditure, ia.getId());
	}

}
