package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IABankReconciliationLineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinanceStatementStatusDeasDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAParticularsWithFinanceStatusDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalExpenditureDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalReceiptDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStatusOfImplementationOfDeasDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IATallyInfoDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAVolumeOfTransactionsDTO;

public interface IFinanceService {

	IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO createUpdateBudgetaryProvisionsAndExpenditure(
			IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO request);

	IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO findBudgetaryProvisionsAndExpenditureByIAId(Long iaId);

	IAVolumeOfTransactionsDTO createUpdateVolumeOfTransaction(IAVolumeOfTransactionsDTO dto);

	IAVolumeOfTransactionsDTO findVolumeOfTransactionByIaId(Long iaId);

	IABankReconciliationLineItemDTO createUpdateBankReconciliation(IABankReconciliationLineItemDTO lineItem);

	List<IABankReconciliationLineItemDTO> getAllBankReconciliation(Long iaId);

	Boolean deleteBankReconciliationLeaf(Long bankReconciliationLeafId, Long iaId);

	IABankReconciliationLineItemDTO getBankReconciliationLineItemById(Long bankReconciliationId, Long iaId);

	IARevenueNCapitalReceiptDTO createUpdateReceiptsAndCapitalInformation(IARevenueNCapitalReceiptDTO dto);

	IARevenueNCapitalReceiptDTO findReceiptsAndCapitalInformationByIaId(Long iaId);

	IARevenueNCapitalExpenditureDTO createUpdateReceiptsAndCapitalExpenditureInformation(
			IARevenueNCapitalExpenditureDTO dto);

	IARevenueNCapitalExpenditureDTO findReceiptsAndCapitalExpenditureInformationByIaId(Long iaId);

	IATallyInfoDTO createUpdateTallyStatus(IATallyInfoDTO dto);

	Long createUpdateDescription(Long ia, String dto);

	IAFinanceStatementStatusDeasDTO createUpdateFinanceStatementStatus(
			IAFinanceStatementStatusDeasDTO financeStatementDTO);

	IAParticularsWithFinanceStatusDTO createUpdateParticularsWithFinanceStatus(
			IAParticularsWithFinanceStatusDTO particularsWithFinanceStatus);

	String uploadStatusOfMunicipalAccCommitteeFile(MultipartFile statusOfMunicipalAccCommitteeFile, Long iaId);

	Long createUpdateStatusOfMunicipalAccCommittee(String statusOfMunicipalAccCommitteeText, Long iaId);

	IAStatusOfImplementationOfDeasDTO getStatusOfImplementationOfDeas(Long iaId);

	IATallyInfoDTO getTallyInfoDTO(Long iaId);

	IAFinanceStatementStatusDeasDTO getFinanceStatementStatusDeas(Long iaId);

	Resource getFile(Long ia);
	
}
