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

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VolumeOfTransactionService implements IVolumeOfTransactionService {

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
		Boolean updateFlag = false;
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(ia).build());
			ia.setFinance(financeEntity);
			iaRepo.save(ia);
		}
		IAVolumeOfTransactionsEntity volume = financeEntity.getVolumeOfTransactions();

		if (volume != null)
			updateFlag = true;

		IAVOTNetExpenditureEntity netExpenditure = getNetExpenditureFromDTO.apply(dto.getNetExpenditure());
		netExpenditureRepo.save(netExpenditure);
		IAVOTOpeningBalanceEntity openingBalance = getOpeningBalanceEntityFromDTO.apply(dto.getOpeningBalance());
		openingBalanceRepo.save(openingBalance);
		IAVOTReceiptsEntity receipts = getReceiptsEntityFromDTO.apply(dto.getReceipts());
		receiptsRepo.save(receipts);

		volume = getVolumeOfTransactionEntityFromRequest(dto, volume != null ? volume.getId() : null, ia,
				netExpenditure, openingBalance, receipts);

		volume = volumeOfTransactionRepo.save(volume);
		financeEntity.setVolumeOfTransactions(volume);
		financeRepo.save(financeEntity);

		if (!updateFlag) {
			openingBalance = volume.getOpeningBalance();
			openingBalance.setVolumeOfTran(volume);
			openingBalanceRepo.save(openingBalance);

			receipts = volume.getReceipts();
			receipts.setVolumeOfTran(volume);
			receiptsRepo.save(receipts);

			netExpenditure = volume.getNetExpenditure();
			netExpenditure.setVolumeOfTran(volume);
			netExpenditureRepo.save(netExpenditure);
		}
		return getVolumeDTOFromEn.apply(volume, ia);
	}

	private Function<IAVOTOpeningBalanceEntity, IAVolumeOfTransactionLeafDTO> getOpeningBalanceDTOFromEn = en -> IAVolumeOfTransactionLeafDTO
			.builder().id(en.getId()).budgetFY(en.getBudgetFY())
			.correspondingPrevYearFY(en.getCorrespondingPrevYearFY()).type(VolumeOfTransactionTypeEnum.OPENING_BALANCE)
			.cumulativeForCurrentPeriod(en.getCumulativeForCurrentPeriod()).currentYearFY(en.getCurrentYearFY())
			.id(en.getId()).previousYearFY(en.getPreviousYearFY()).build();
	private Function<IAVOTReceiptsEntity, IAVolumeOfTransactionLeafDTO> getReceiptsDTOFromEn = en -> IAVolumeOfTransactionLeafDTO
			.builder().id(en.getId()).budgetFY(en.getBudgetFY())
			.correspondingPrevYearFY(en.getCorrespondingPrevYearFY()).type(VolumeOfTransactionTypeEnum.RECEIPTS)
			.cumulativeForCurrentPeriod(en.getCumulativeForCurrentPeriod()).currentYearFY(en.getCurrentYearFY())
			.id(en.getId()).previousYearFY(en.getPreviousYearFY()).build();
	private Function<IAVOTNetExpenditureEntity, IAVolumeOfTransactionLeafDTO> getNetExpenditureDTOFromEn = en -> IAVolumeOfTransactionLeafDTO
			.builder().id(en.getId()).budgetFY(en.getBudgetFY())
			.correspondingPrevYearFY(en.getCorrespondingPrevYearFY()).type(VolumeOfTransactionTypeEnum.EXPENDITURE)
			.cumulativeForCurrentPeriod(en.getCumulativeForCurrentPeriod()).currentYearFY(en.getCurrentYearFY())
			.id(en.getId()).previousYearFY(en.getPreviousYearFY()).build();

	private BiFunction<IAVolumeOfTransactionsEntity, InternalAuditEntity, IAVolumeOfTransactionsDTO> getVolumeDTOFromEn = (
			en, ia) -> IAVolumeOfTransactionsDTO.builder()
					.id(en.getId())
					.budgetFY(en.getBudgetFY())
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

	private Function<IAVolumeOfTransactionLeafDTO, IAVOTNetExpenditureEntity> getNetExpenditureFromDTO = (
			dto) -> IAVOTNetExpenditureEntity.builder().id(dto.getId()).budgetFY(dto.getBudgetFY())
					.correspondingPrevYearFY(dto.getCorrespondingPrevYearFY())
					.cumulativeForCurrentPeriod(dto.getCumulativeForCurrentPeriod())
					.currentYearFY(dto.getCurrentYearFY()).previousYearFY(dto.getPreviousYearFY()).build();
	private Function<IAVolumeOfTransactionLeafDTO, IAVOTReceiptsEntity> getReceiptsEntityFromDTO = (
			dto) -> IAVOTReceiptsEntity.builder().id(dto.getId()).budgetFY(dto.getBudgetFY())
					.correspondingPrevYearFY(dto.getCorrespondingPrevYearFY())
					.cumulativeForCurrentPeriod(dto.getCumulativeForCurrentPeriod())
					.currentYearFY(dto.getCurrentYearFY()).previousYearFY(dto.getPreviousYearFY()).build();

	private Function<IAVolumeOfTransactionLeafDTO, IAVOTOpeningBalanceEntity> getOpeningBalanceEntityFromDTO = (
			dto) -> IAVOTOpeningBalanceEntity.builder().id(dto.getId()).budgetFY(dto.getBudgetFY())
					.correspondingPrevYearFY(dto.getCorrespondingPrevYearFY())
					.cumulativeForCurrentPeriod(dto.getCumulativeForCurrentPeriod())
					.currentYearFY(dto.getCurrentYearFY()).previousYearFY(dto.getPreviousYearFY()).build();

	private IAVolumeOfTransactionsEntity getVolumeOfTransactionEntityFromRequest(IAVolumeOfTransactionsDTO dto, Long id,
			InternalAuditEntity ia, IAVOTNetExpenditureEntity netExpenditure, IAVOTOpeningBalanceEntity openingBalance,
			IAVOTReceiptsEntity receipts) {

		return IAVolumeOfTransactionsEntity.builder().id(id).budgetFY(dto.getBudgetFY())
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
