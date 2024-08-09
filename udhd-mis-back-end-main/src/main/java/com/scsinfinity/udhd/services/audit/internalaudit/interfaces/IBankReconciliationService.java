package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IABankReconciliationLineItemDTO;

public interface IBankReconciliationService {

	IABankReconciliationLineItemDTO createUpdateBankReconciliation(IABankReconciliationLineItemDTO lineItem);

	List<IABankReconciliationLineItemDTO> getAllBankReconciliation(Long iaId);

	Boolean deleteBankReconciliationLeaf(Long bankReconciliationLeafId, Long iaId);

	IABankReconciliationLineItemDTO getBankReconciliationLineItemById(Long bankReconciliationId, Long iaId);
}
