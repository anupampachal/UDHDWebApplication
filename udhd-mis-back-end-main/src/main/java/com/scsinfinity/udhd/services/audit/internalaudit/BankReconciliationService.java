package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IABankReconciliationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IABankReconciliationLineItemEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIABankReconciliationLineItemRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIABankReconciliationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAFinanceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IInternalAuditRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IABankReconciliationLineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IBankReconciliationService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BankReconciliationService implements IBankReconciliationService {

	private final IInternalAuditService iaService;
	private final IInternalAuditRepository iaRepo;
	private final IIAFinanceRepository financeRepo;
	private final IIABankReconciliationRepository bankReconciliationRepo;
	private final IIABankReconciliationLineItemRepository bankReconciliationLineItemRepo;

	@Override
	public IABankReconciliationLineItemDTO createUpdateBankReconciliation(IABankReconciliationLineItemDTO lineItem) {
		InternalAuditEntity ia = iaService.findInternalAuditEntityById(lineItem.getIaId());
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(ia).build());
			ia.setFinance(financeEntity);
			iaRepo.save(ia);
		}
		IABankReconciliationEntity bankReconciliationEn = financeEntity.getBankReconciliation();
		if (bankReconciliationEn == null) {
			bankReconciliationEn = bankReconciliationRepo
					.save(IABankReconciliationEntity.builder().finance(financeEntity).build());
		}
		List<IABankReconciliationLineItemEntity> bankReconciliationLineItemList = bankReconciliationEn
				.getBankReconciliationLineItem();
		if (bankReconciliationLineItemList == null) {
			bankReconciliationLineItemList = new ArrayList<>();
		}

		IABankReconciliationLineItemEntity lineItemEn = getLineItemEntityFromDTO.apply(lineItem);
		if (lineItemEn.getId() == null) {
			bankReconciliationLineItemList.add(lineItemEn);
			bankReconciliationEn.setBankReconciliationLineItem(bankReconciliationLineItemList);
			bankReconciliationEn = bankReconciliationRepo.save(bankReconciliationEn);
		}
		lineItemEn.setBankReconciliation(bankReconciliationEn);

		bankReconciliationLineItemRepo.save(lineItemEn);
		return getLineItemDTOFromEn.apply(lineItemEn, ia);
	}

	private Function<IABankReconciliationLineItemDTO, IABankReconciliationLineItemEntity> getLineItemEntityFromDTO = br -> IABankReconciliationLineItemEntity
			.builder().id(br.getId()).accountNumber(br.getAccountNumber()).amountReconciled(br.getAmountReconciled())
			.bankName(br.getBankName()).bankBalancePerPassbook(br.getBankBalancePerPassbook())
			.BRSStatus(br.getBRSStatus()).cashBalancePerCashbook(br.getCashBalancePerCashbook())
			.projectSchemeName(br.getProjectSchemeName()).build();

	private BiFunction<IABankReconciliationLineItemEntity, InternalAuditEntity, IABankReconciliationLineItemDTO> getLineItemDTOFromEn = (
			br, ia) -> IABankReconciliationLineItemDTO.builder().id(br.getId()).iaId(ia.getId())
					.accountNumber(br.getAccountNumber()).amountReconciled(br.getAmountReconciled())
					.bankName(br.getBankName()).bankBalancePerPassbook(br.getBankBalancePerPassbook())
					.BRSStatus(br.getBRSStatus()).cashBalancePerCashbook(br.getCashBalancePerCashbook())
					.projectSchemeName(br.getProjectSchemeName()).build();

	@Override
	public List<IABankReconciliationLineItemDTO> getAllBankReconciliation(Long iaId) {
		InternalAuditEntity ia = iaService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			return null;
		}
		IABankReconciliationEntity bankReconciliationEn = financeEntity.getBankReconciliation();
		if (bankReconciliationEn == null)
			return null;

		return bankReconciliationEn.getBankReconciliationLineItem().stream().filter(en -> en != null)
				.map(en -> getLineItemDTOFromEn.apply(en, ia)).collect(Collectors.toList());
	}

	@Override
	public IABankReconciliationLineItemDTO getBankReconciliationLineItemById(Long bankReconciliationId, Long iaId) {
		InternalAuditEntity ia = iaService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			return null;
		}
		IABankReconciliationEntity bankReconciliationEn = financeEntity.getBankReconciliation();
		if (bankReconciliationEn == null)
			return null;

		IABankReconciliationLineItemEntity lineItemEn = bankReconciliationLineItemRepo.findById(bankReconciliationId)
				.orElseThrow(() -> new EntityNotFoundException(
						"getBankReconciliationLineItemById: " + bankReconciliationId));
		if (!bankReconciliationEn.getBankReconciliationLineItem().contains(lineItemEn))
			throw new BadRequestAlertException("INCONSISTENT_REQUEST", "INCONSISTENT_REQUEST", "INCONSISTENT_REQUEST");
		return getLineItemDTOFromEn.apply(lineItemEn, ia);
	}

	@Override
	public Boolean deleteBankReconciliationLeaf(Long bankReconciliationLeafId, Long iaId) {
		InternalAuditEntity ia = iaService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			return false;
		}
		IABankReconciliationEntity bankReconciliationEn = financeEntity.getBankReconciliation();
		if (bankReconciliationEn == null)
			return false;

		IABankReconciliationLineItemEntity lineItemEn = bankReconciliationLineItemRepo
				.findById(bankReconciliationLeafId).orElseThrow(() -> new EntityNotFoundException(
						"getBankReconciliationLineItemById: " + bankReconciliationLeafId));
		bankReconciliationEn.getBankReconciliationLineItem().remove(lineItemEn);
		bankReconciliationRepo.save(bankReconciliationEn);
		bankReconciliationLineItemRepo.delete(lineItemEn);
		return true;
	}

}
