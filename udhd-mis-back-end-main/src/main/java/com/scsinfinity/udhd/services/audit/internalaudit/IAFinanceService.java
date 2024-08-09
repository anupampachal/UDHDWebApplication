package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
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
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IBankReconciliationService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IFinanceService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAFinanceBudgetaryProvisionsAndExpenditureService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IReceiptsAndCapitalExpenditureService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IReceiptsAndCapitalReceiptsService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IStatusOfImplementationOfDeasService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IVolumeOfTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IAFinanceService implements IFinanceService {
	private final IIAFinanceBudgetaryProvisionsAndExpenditureService budgetaryProvisionsAndExpenditureService;
	private final IVolumeOfTransactionService volumeService;
	private final IBankReconciliationService bankReconciliationService;
	private final IReceiptsAndCapitalReceiptsService receiptsAndCapitalReceiptsService;
	private final IReceiptsAndCapitalExpenditureService receiptsAndCapitalExpenditureService;
	private final IStatusOfImplementationOfDeasService statusOfDeasService;

	@Override
	public IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO createUpdateBudgetaryProvisionsAndExpenditure(
			IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO request) {
		return budgetaryProvisionsAndExpenditureService.createUpdateBudgetaryProvisionsAndExpenditure(request);
	}

	@Override
	public IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO findBudgetaryProvisionsAndExpenditureByIAId(
			Long iaId) {
		return budgetaryProvisionsAndExpenditureService.findBudgetaryProvisionsAndExpenditureByIAId(iaId);
	}

	@Override
	public IAVolumeOfTransactionsDTO createUpdateVolumeOfTransaction(IAVolumeOfTransactionsDTO dto) {
		return volumeService.createUpdateVolumeOfTransaction(dto);
	}

	@Override
	public IAVolumeOfTransactionsDTO findVolumeOfTransactionByIaId(Long iaId) {
		return volumeService.findVolumeOfTransactionByIaId(iaId);
	}

	@Override
	public IABankReconciliationLineItemDTO createUpdateBankReconciliation(IABankReconciliationLineItemDTO lineItem) {
		return bankReconciliationService.createUpdateBankReconciliation(lineItem);
	}

	@Override
	public List<IABankReconciliationLineItemDTO> getAllBankReconciliation(Long iaId) {
		return bankReconciliationService.getAllBankReconciliation(iaId);
	}

	@Override
	public Boolean deleteBankReconciliationLeaf(Long bankReconciliationLeafId, Long iaId) {
		return bankReconciliationService.deleteBankReconciliationLeaf(bankReconciliationLeafId, iaId);
	}

	@Override
	public IABankReconciliationLineItemDTO getBankReconciliationLineItemById(Long bankReconciliationId, Long iaId) {
		return bankReconciliationService.getBankReconciliationLineItemById(bankReconciliationId, iaId);
	}

	@Override
	public IARevenueNCapitalReceiptDTO createUpdateReceiptsAndCapitalInformation(IARevenueNCapitalReceiptDTO dto) {
		return receiptsAndCapitalReceiptsService.createUpdateReceiptsAndCapitalInformation(dto);
	}

	@Override
	public IARevenueNCapitalReceiptDTO findReceiptsAndCapitalInformationByIaId(Long iaId) {
		return receiptsAndCapitalReceiptsService.findReceiptsAndCapitalInformationByIaId(iaId);
	}

	@Override
	public IARevenueNCapitalExpenditureDTO createUpdateReceiptsAndCapitalExpenditureInformation(
			IARevenueNCapitalExpenditureDTO dto) {
		return receiptsAndCapitalExpenditureService.createUpdateReceiptsAndCapitalExpenditureInformation(dto);
	}

	@Override
	public IARevenueNCapitalExpenditureDTO findReceiptsAndCapitalExpenditureInformationByIaId(Long iaId) {
		return receiptsAndCapitalExpenditureService.findReceiptsAndCapitalExpenditureInformationByIaId(iaId);
	}

	@Override
	public IATallyInfoDTO createUpdateTallyStatus(IATallyInfoDTO dto) {
		return statusOfDeasService.createUpdateTallyStatus(dto);
	}

	@Override
	public Long createUpdateDescription(Long ia, String dto) {
		return statusOfDeasService.createUpdateDescription(ia, dto);
	}

	@Override
	public IAFinanceStatementStatusDeasDTO createUpdateFinanceStatementStatus(
			IAFinanceStatementStatusDeasDTO financeStatementDTO) {
		return statusOfDeasService.createUpdateFinanceStatementStatus(financeStatementDTO);
	}

	@Override
	public IAParticularsWithFinanceStatusDTO createUpdateParticularsWithFinanceStatus(
			IAParticularsWithFinanceStatusDTO particularsWithFinanceStatus) {
		return statusOfDeasService.createUpdateParticularsWithFinanceStatus(particularsWithFinanceStatus);
	}

	@Override
	public String uploadStatusOfMunicipalAccCommitteeFile(MultipartFile statusOfMunicipalAccCommitteeFile, Long iaId) {
		return statusOfDeasService.uploadStatusOfMunicipalAccCommitteeFile(statusOfMunicipalAccCommitteeFile, iaId);
	}

	@Override
	public Long createUpdateStatusOfMunicipalAccCommittee(String statusOfMunicipalAccCommitteeText, Long iaId) {
		return statusOfDeasService.createUpdateStatusOfMunicipalAccCommittee(statusOfMunicipalAccCommitteeText, iaId);
	}

	@Override
	public IAStatusOfImplementationOfDeasDTO getStatusOfImplementationOfDeas(Long iaId) {
		return statusOfDeasService.getStatusOfImplementationOfDeas(iaId);
	}

	@Override
	public IATallyInfoDTO getTallyInfoDTO(Long iaId) {
		return statusOfDeasService.getTallyInfoDTO(iaId);
	}

	@Override
	public IAFinanceStatementStatusDeasDTO getFinanceStatementStatusDeas(Long iaId) {
		return statusOfDeasService.getFinanceStatementStatusDeas(iaId);
	}

	@Override
	public Resource getFile(Long ia) {
		return statusOfDeasService.getFile(ia);
	}

}
