package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.ActualExpenditureDataEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.FinalRevisedBudgetDataEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IABudgetaryProvisionsAndExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IAActualExpenditureDataRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IFinalRevisedBudgetDataRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIABudgetaryProvisionsAndExpenditureRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAFinanceRepository;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAActualExpenditureDataDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinalRevisedBudgetDataDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAFinanceBudgetaryProvisionsAndExpenditureService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IAFinanceBudgetaryProvisionsAndExpenditureService
		implements IIAFinanceBudgetaryProvisionsAndExpenditureService {

	private final IIABudgetaryProvisionsAndExpenditureRepository budgetaryProvisionsAndExpenditureRepo;
	private final IFinalRevisedBudgetDataRepository finalRevisedBudgetDataRepo;
	private final IAActualExpenditureDataRepository actualExpenditureDataRepo;
	private final IIAFinanceRepository financeRepo;
	private final IInternalAuditService internalAuditService;

	@Override
	public IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO createUpdateBudgetaryProvisionsAndExpenditure(
			IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO request) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(request.getIaId());
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(ia).build());
		}
		IABudgetaryProvisionsAndExpenditureEntity budget = financeEntity.getBudgetaryProvisions();
		if (budget != null) {
			budget = getBudgetaryProvisionsAndExpenditureEntityFromDTO(request, budget.getId());
		} else {
			budget = getBudgetaryProvisionsAndExpenditureEntityFromDTO(request, null);
		}
		budget.setFinance(financeEntity);
		budget = budgetaryProvisionsAndExpenditureRepo.save(budget);

		financeEntity.setBudgetaryProvisions(budget);
		financeRepo.save(financeEntity);
		FinalRevisedBudgetDataEntity finalRevisedBData = budget.getFinalBudgetData();
		finalRevisedBData.setBudget(budget);
		finalRevisedBudgetDataRepo.save(finalRevisedBData);
		ActualExpenditureDataEntity actualExpenData = budget.getActualExpenditureData();
		actualExpenData.setBudget(budget);
		actualExpenditureDataRepo.save(actualExpenData);

		return getBudgetResponseDTOFromEn.apply(budget, ia);
	}

	private Function<FinalRevisedBudgetDataEntity, IAFinalRevisedBudgetDataDTO> getFinalDTOFromEntity = en -> IAFinalRevisedBudgetDataDTO
			.builder().fy1(en.getFy1()).fy2(en.getFy2()).fy3(en.getFy3()).id(en.getId()).build();
	private Function<ActualExpenditureDataEntity, IAActualExpenditureDataDTO> getActualExpDTOFromEntity = en -> IAActualExpenditureDataDTO
			.builder().fy1(en.getFy1()).fy2(en.getFy2()).fy3(en.getFy3()).id(en.getId()).build();

	//@formatter:off
	private BiFunction<IABudgetaryProvisionsAndExpenditureEntity, InternalAuditEntity, IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO> getBudgetResponseDTOFromEn = (
			en, ia) -> 
	IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO.builder()
					.fy1(en.getFy1())
					.fy2(en.getFy2())
					.fy3(en.getFy3())
					.finalBudgetData(getFinalDTOFromEntity.apply(en.getFinalBudgetData()))
					.actualExpenditureData(getActualExpDTOFromEntity.apply(en.getActualExpenditureData()))
					.iaId(ia.getId()).build();

	private IABudgetaryProvisionsAndExpenditureEntity getBudgetaryProvisionsAndExpenditureEntityFromDTO(
			IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO request, Long budgetId) {
		FinalRevisedBudgetDataEntity revisedBudget = FinalRevisedBudgetDataEntity.builder()
				.fy1(request.getFinRevBudgetDataFy1()).fy2(request.getFinRevBudgetDataFy2())
				.fy3(request.getFinRevBudgetDataFy3()).build();
		ActualExpenditureDataEntity actualExpenditure = ActualExpenditureDataEntity.builder()
				.fy1(request.getActualExpenditureDataFy1()).fy2(request.getActualExpenditureDataFy2())
				.fy3(request.getActualExpenditureDataFy3()).build();

		return IABudgetaryProvisionsAndExpenditureEntity.builder().id(budgetId).fy1(request.getFy1())
				.fy2(request.getFy2()).fy3(request.getFy3()).finalBudgetData(revisedBudget)
				.actualExpenditureData(actualExpenditure).build();
	}

	@Override
	public IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO findBudgetaryProvisionsAndExpenditureByIAId(
			Long iaId) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null)
			return null;
		IABudgetaryProvisionsAndExpenditureEntity budget = financeEntity.getBudgetaryProvisions();
		if (budget == null)
			return null;
		return getBudgetResponseDTOFromEn.apply(budget, ia);
	}
}
