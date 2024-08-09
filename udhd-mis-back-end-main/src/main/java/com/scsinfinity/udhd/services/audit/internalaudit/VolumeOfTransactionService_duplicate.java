package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAVOTNetExpenditureEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAVOTOpeningBalanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAVOTReceiptsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAVolumeOfTransactionsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAFinanceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAVOTNetExpenditureRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAVOTOpeningBalanceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAVOTReceiptsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAVolumeOfTransactionsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IInternalAuditRepository;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAVolumeOfTransactionLeafDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAVolumeOfTransactionsDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.VolumeOfTransactionTypeEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IVolumeOfTransactionService;

import io.vavr.Function3;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class VolumeOfTransactionService_duplicate implements IVolumeOfTransactionService {

	private final IIAFinanceRepository financeRepo;
	private final IInternalAuditService internalAuditService;
	private final IIAVolumeOfTransactionsRepository volumeOfTransactionRepo;
	private final IIAVOTOpeningBalanceRepository openingBalanceRepo;
	private final IIAVOTReceiptsRepository receiptsRepo;
	private final IIAVOTNetExpenditureRepository netExpenditureRepo;
	private final IInternalAuditRepository iaRepo;

	@Override
	public IAVolumeOfTransactionsDTO createUpdateVolumeOfTransaction(IAVolumeOfTransactionsDTO dto) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(dto.getIaId());
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(ia).build());
			ia.setFinance(financeEntity);
			iaRepo.save(ia);
		}
		IAVolumeOfTransactionsEntity volume = financeEntity.getVolumeOfTransactions();
		if (volume == null) {
			
			volume = new IAVolumeOfTransactionsEntity();
			volume.setFinance(financeEntity);
			volume = volumeOfTransactionRepo.saveAndFlush(volume);
		}

		// volume = getVolumeOfTransactionEntityFromRequest.apply(dto, volume, ia);
		volume = getVolumeOfTransactionEntityFromRequest(dto, volume, ia);

		volume = volumeOfTransactionRepo.save(volume);

		financeEntity.setVolumeOfTransactions(volume);
		financeRepo.save(financeEntity);

		IAVOTOpeningBalanceEntity openingBalance = volume.getOpeningBalance();
		openingBalance.setVolumeOfTran(volume);
		openingBalanceRepo.save(openingBalance);
		IAVOTReceiptsEntity receiptsData = volume.getReceipts();
		receiptsData.setVolumeOfTran(volume);
		receiptsRepo.save(receiptsData);
		IAVOTNetExpenditureEntity netExpenditure = volume.getNetExpenditure();
		netExpenditure.setVolumeOfTran(volume);
		netExpenditureRepo.save(netExpenditure);
		return getVolumeDTOFromEn.apply(volume, ia);
	}

	private BiFunction<IAVolumeOfTransactionLeafDTO, IAVolumeOfTransactionsEntity, IAVOTOpeningBalanceEntity> getOpeningBalanceEntityFromDTO = (
			dto, vot) -> IAVOTOpeningBalanceEntity.builder().id(dto.getId()).budgetFY(dto.getBudgetFY())
					.correspondingPrevYearFY(dto.getCorrespondingPrevYearFY()).volumeOfTran(vot)
					.cumulativeForCurrentPeriod(dto.getCumulativeForCurrentPeriod())
					.currentYearFY(dto.getCurrentYearFY()).previousYearFY(dto.getPreviousYearFY()).build();
	private Function<IAVOTOpeningBalanceEntity, IAVolumeOfTransactionLeafDTO> getOpeningBalanceDTOFromEn = en -> IAVolumeOfTransactionLeafDTO
			.builder().id(en.getId()).budgetFY(en.getBudgetFY())
			.correspondingPrevYearFY(en.getCorrespondingPrevYearFY()).type(VolumeOfTransactionTypeEnum.OPENING_BALANCE)
			.cumulativeForCurrentPeriod(en.getCumulativeForCurrentPeriod()).currentYearFY(en.getCurrentYearFY())
			.id(en.getId()).previousYearFY(en.getPreviousYearFY()).build();

	private BiFunction<IAVolumeOfTransactionLeafDTO, IAVolumeOfTransactionsEntity, IAVOTReceiptsEntity> getReceiptsEntityFromDTO = (
			dto, vot) -> IAVOTReceiptsEntity.builder().id(dto.getId()).budgetFY(dto.getBudgetFY())
					.correspondingPrevYearFY(dto.getCorrespondingPrevYearFY()).volumeOfTran(vot)
					.cumulativeForCurrentPeriod(dto.getCumulativeForCurrentPeriod())
					.currentYearFY(dto.getCurrentYearFY()).previousYearFY(dto.getPreviousYearFY()).build();
	private Function<IAVOTReceiptsEntity, IAVolumeOfTransactionLeafDTO> getReceiptsDTOFromEn = en -> IAVolumeOfTransactionLeafDTO
			.builder().id(en.getId()).budgetFY(en.getBudgetFY())
			.correspondingPrevYearFY(en.getCorrespondingPrevYearFY()).type(VolumeOfTransactionTypeEnum.RECEIPTS)
			.cumulativeForCurrentPeriod(en.getCumulativeForCurrentPeriod()).currentYearFY(en.getCurrentYearFY())
			.id(en.getId()).previousYearFY(en.getPreviousYearFY()).build();

	private BiFunction<IAVolumeOfTransactionLeafDTO, IAVolumeOfTransactionsEntity, IAVOTNetExpenditureEntity> getNetExpenditureFromDTO = (
			dto, vot) -> IAVOTNetExpenditureEntity.builder().id(dto.getId()).budgetFY(dto.getBudgetFY())
					.volumeOfTran(vot).correspondingPrevYearFY(dto.getCorrespondingPrevYearFY())
					.cumulativeForCurrentPeriod(dto.getCumulativeForCurrentPeriod())
					.currentYearFY(dto.getCurrentYearFY()).previousYearFY(dto.getPreviousYearFY()).build();

	private Function<IAVOTNetExpenditureEntity, IAVolumeOfTransactionLeafDTO> getNetExpenditureDTOFromEn = en -> IAVolumeOfTransactionLeafDTO
			.builder().id(en.getId()).budgetFY(en.getBudgetFY())
			.correspondingPrevYearFY(en.getCorrespondingPrevYearFY()).type(VolumeOfTransactionTypeEnum.EXPENDITURE)
			.cumulativeForCurrentPeriod(en.getCumulativeForCurrentPeriod()).currentYearFY(en.getCurrentYearFY())
			.id(en.getId()).previousYearFY(en.getPreviousYearFY()).build();

	private BiFunction<IAVolumeOfTransactionsEntity, InternalAuditEntity, IAVolumeOfTransactionsDTO> getVolumeDTOFromEn = (
			en, ia) -> IAVolumeOfTransactionsDTO.builder().budgetFY(en.getBudgetFY())
					.correspondingPrevYearFY(en.getCorrespondingPrevYearFY()).currentYearFY(en.getCurrentYearFY())
					.iaId(ia.getId())
					.netExpenditure(
							en.getNetExpenditure() != null ? getNetExpenditureDTOFromEn.apply(en.getNetExpenditure())
									: null)
					.openingBalance(
							en.getOpeningBalance() != null ? getOpeningBalanceDTOFromEn.apply(en.getOpeningBalance())
									: null)
					.previousYearFY(en.getPreviousYearFY())
					.receipts(en.getReceipts() != null ? getReceiptsDTOFromEn.apply(en.getReceipts()) : null).build();

	/*
	 * private Function3<IAVolumeOfTransactionsDTO, IAVolumeOfTransactionsEntity,
	 * InternalAuditEntity, IAVolumeOfTransactionsEntity>
	 * getVolumeOfTransactionEntityFromRequest = ( dto, en, ia) ->
	 * IAVolumeOfTransactionsEntity.builder().id(en.getId()).budgetFY(dto.
	 * getBudgetFY())
	 * .correspondingPrevYearFY(dto.getCorrespondingPrevYearFY()).currentYearFY(dto.
	 * getCurrentYearFY()) .finance(ia.getFinance())
	 * .netExpenditure(dto.getNetExpenditure() != null ?
	 * getNetExpenditureFromDTO.apply(dto.getNetExpenditure(),en.getNetExpenditure()
	 * !=null? en:null) : en.getNetExpenditure()!=null?en.getNetExpenditure():null)
	 * .openingBalance(dto.getOpeningBalance() != null ?
	 * getOpeningBalanceEntityFromDTO
	 * .apply(dto.getOpeningBalance(),en.getOpeningBalance()!=null? en:null) :
	 * en.getOpeningBalance()!=null?en.getOpeningBalance():null)
	 * .previousYearFY(dto.getPreviousYearFY()) .receipts(dto.getReceipts() != null
	 * ? getReceiptsEntityFromDTO.apply(dto.getReceipts(),
	 * en.getReceipts()!=null?en:null):
	 * en.getReceipts()!=null?en.getReceipts():null)
	 * 
	 * .build();
	 */

	private IAVolumeOfTransactionsEntity getVolumeOfTransactionEntityFromRequest(IAVolumeOfTransactionsDTO dto,
			IAVolumeOfTransactionsEntity en, InternalAuditEntity ia) {

		IAVOTNetExpenditureEntity netExpenditure = dto.getNetExpenditure() != null
				? getNetExpenditureFromDTO.apply(dto.getNetExpenditure(), en)
				: null;
		IAVOTOpeningBalanceEntity openingBalance = dto.getOpeningBalance() != null
				? getOpeningBalanceEntityFromDTO.apply(dto.getOpeningBalance(), en)
				: null;
		IAVOTReceiptsEntity receipts = dto.getReceipts() != null ? getReceiptsEntityFromDTO.apply(dto.getReceipts(), en)
				: null;
		return IAVolumeOfTransactionsEntity.builder().id(en.getId()).budgetFY(dto.getBudgetFY())
				.correspondingPrevYearFY(dto.getCorrespondingPrevYearFY()).currentYearFY(dto.getCurrentYearFY())
				.finance(ia.getFinance()).netExpenditure(netExpenditure).openingBalance(openingBalance)
				.previousYearFY(dto.getPreviousYearFY()).receipts(receipts)

				.build();
	}

	@Override
	public IAVolumeOfTransactionsDTO findVolumeOfTransactionByIaId(Long iaId) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null)
			return null;
		IAVolumeOfTransactionsEntity volume = financeEntity.getVolumeOfTransactions();
		if (volume == null)
			return null;
		return getVolumeDTOFromEn.apply(volume, ia);
	}

}
