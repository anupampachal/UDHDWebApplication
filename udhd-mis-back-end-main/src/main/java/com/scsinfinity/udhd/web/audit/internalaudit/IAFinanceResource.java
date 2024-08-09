package com.scsinfinity.udhd.web.audit.internalaudit;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IFinanceService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/audit/ia/step5")
public class IAFinanceResource {

	private final IFinanceService financeService;

	@GetMapping("/budgetaryprov/{iaId}")
	public ResponseEntity<IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO> findBudgetaryProvisionsAndExpenditureByIAId(
			@PathVariable Long iaId) {
		log.debug("findBudgetaryProvisionsAndExpenditureByIAId");
		return ResponseEntity.ok(financeService.findBudgetaryProvisionsAndExpenditureByIAId(iaId));
	}

	@PutMapping("/budgetaryprov")
	public ResponseEntity<IAFinanceBudgetaryProvisionsAndExpenditureResponseDTO> createUpdateBudgetaryProvisionsAndExpenditure(
			@RequestBody IAFinanceBudgetaryProvisionsAndExpenditureRequestDTO request) {
		return ResponseEntity.ok(financeService.createUpdateBudgetaryProvisionsAndExpenditure(request));
	}

	@PutMapping("/volume-of-transaction")
	public ResponseEntity<IAVolumeOfTransactionsDTO> createUpdateVolumeOfTransaction(
			@RequestBody IAVolumeOfTransactionsDTO dto) {
		log.debug("createUpdateVolumeOfTransaction");
		return ResponseEntity.ok(financeService.createUpdateVolumeOfTransaction(dto));
	}

	@GetMapping("/volume-of-transaction/{iaId}")
	public ResponseEntity<IAVolumeOfTransactionsDTO> findVolumeOfTransactionByIaId(@PathVariable Long iaId) {
		log.debug("findVolumeOfTransactionByIaId");
		return ResponseEntity.ok(financeService.findVolumeOfTransactionByIaId(iaId));
	}

	@PutMapping("/bank-reconciliation")
	private ResponseEntity<IABankReconciliationLineItemDTO> createUpdateBankReconciliation(
			@RequestBody IABankReconciliationLineItemDTO lineItem) {
		log.debug("createUpdateBankReconciliation");
		return ResponseEntity.ok(financeService.createUpdateBankReconciliation(lineItem));
	}

	@GetMapping("/bank-reconciliation/{iaId}")
	private ResponseEntity<List<IABankReconciliationLineItemDTO>> getAllBankReconciliation(@PathVariable Long iaId) {
		log.debug("getAllBankReconciliation");
		return ResponseEntity.ok(financeService.getAllBankReconciliation(iaId));
	}

	@DeleteMapping("/bank-reconciliation/{leafId}/ia/{iaId}")
	private ResponseEntity<Boolean> deleteBankReconciliationLeaf(@PathVariable("leafId") Long bankReconciliationLeafId,
			@PathVariable Long iaId) {
		log.debug("deleteBankReconciliationLeaf");
		return ResponseEntity.ok(financeService.deleteBankReconciliationLeaf(bankReconciliationLeafId, iaId));
	}

	@GetMapping("/bank-reconciliation/{leafId}/ia/{iaId}")
	private ResponseEntity<IABankReconciliationLineItemDTO> getBankReconciliationLineItemById(
			@PathVariable("leafId") Long bankReconciliationId, @PathVariable Long iaId) {
		log.debug("getBankReconciliationLineItemById");
		return ResponseEntity.ok(financeService.getBankReconciliationLineItemById(bankReconciliationId, iaId));
	}

	@PutMapping("/revenue-capital")
	public ResponseEntity<IARevenueNCapitalReceiptDTO> createUpdateReceiptsAndCapitalInformation(
			@RequestBody IARevenueNCapitalReceiptDTO dto) {
		log.debug("createUpdateReceiptsAndCapitalInformation");
		return ResponseEntity.ok(financeService.createUpdateReceiptsAndCapitalInformation(dto));
	}

	@GetMapping("/revenue-capital/{iaId}")
	public ResponseEntity<IARevenueNCapitalReceiptDTO> findReceiptsAndCapitalInformationByIaId(
			@PathVariable Long iaId) {
		return ResponseEntity.ok(financeService.findReceiptsAndCapitalInformationByIaId(iaId));
	}

	@PutMapping("/revenue-capital-ex")
	public ResponseEntity<IARevenueNCapitalExpenditureDTO> createUpdateReceiptsAndCapitalExpenditureInformation(
			@RequestBody IARevenueNCapitalExpenditureDTO dto) {
		return ResponseEntity.ok(financeService.createUpdateReceiptsAndCapitalExpenditureInformation(dto));
	}

	@GetMapping("/revenue-capital-ex/{iaId}")
	public ResponseEntity<IARevenueNCapitalExpenditureDTO> findReceiptsAndCapitalExpenditureInformationByIaId(
			@PathVariable Long iaId) {
		return ResponseEntity.ok(financeService.findReceiptsAndCapitalExpenditureInformationByIaId(iaId));
	}

	@PutMapping("/status-of-deas/tally")
	public ResponseEntity<IATallyInfoDTO> createUpdateTallyStatus(@RequestBody IATallyInfoDTO dto) {
		return ResponseEntity.ok(financeService.createUpdateTallyStatus(dto));
	}

	@PutMapping("/status-of-deas/{ia}")
	public ResponseEntity<Long> createUpdateDescription(@PathVariable Long ia, @RequestBody String dto) {
		return ResponseEntity.ok(financeService.createUpdateDescription(ia, dto));
	}

	@PutMapping("/status-of-deas/finance")
	public ResponseEntity<IAFinanceStatementStatusDeasDTO> createUpdateFinanceStatementStatus(
			@RequestBody IAFinanceStatementStatusDeasDTO financeStatementDTO) {
		return ResponseEntity.ok(financeService.createUpdateFinanceStatementStatus(financeStatementDTO));
	}

	@PutMapping("/status-of-deas/particulars")
	public ResponseEntity<IAParticularsWithFinanceStatusDTO> createUpdateParticularsWithFinanceStatus(
			@RequestBody IAParticularsWithFinanceStatusDTO particularsWithFinanceStatus) {
		return ResponseEntity.ok(financeService.createUpdateParticularsWithFinanceStatus(particularsWithFinanceStatus));
	}

	@PutMapping("/status-of-deas/file")
	public ResponseEntity<String> uploadStatusOfMunicipalAccCommitteeFile(
			@RequestPart("File") MultipartFile statusOfMunicipalAccCommitteeFile, @RequestPart("iaId") String iaId) {
		return ResponseEntity.ok(financeService
				.uploadStatusOfMunicipalAccCommitteeFile(statusOfMunicipalAccCommitteeFile, Long.parseLong(iaId)));
	}

	@PutMapping("/status-of-deas/mac/{iaId}")
	public ResponseEntity<Long> createUpdateStatusOfMunicipalAccCommittee(
			@RequestBody String statusOfMunicipalAccCommitteeText, @PathVariable Long iaId) {
		return ResponseEntity
				.ok(financeService.createUpdateStatusOfMunicipalAccCommittee(statusOfMunicipalAccCommitteeText, iaId));
	}

	@GetMapping("/status-of-deas/{iaId}")
	public ResponseEntity<IAStatusOfImplementationOfDeasDTO> getStatusOfImplementationOfDeas(@PathVariable Long iaId) {
		return ResponseEntity.ok(financeService.getStatusOfImplementationOfDeas(iaId));
	}

	@GetMapping("/status-of-deas/tallyInfo/{iaId}")
	public ResponseEntity<IATallyInfoDTO> getTallyInfoDTO(@PathVariable Long iaId) {
		return ResponseEntity.ok(financeService.getTallyInfoDTO(iaId));
	}

	@GetMapping("/status-of-deas/fin-status/{iaId}")
	public ResponseEntity<IAFinanceStatementStatusDeasDTO> getFinanceStatementStatusDeas(@PathVariable Long iaId) {
		return ResponseEntity.ok(financeService.getFinanceStatementStatusDeas(iaId));
	}

	@GetMapping("/status-of-deas/file/{iaId}")
	public ResponseEntity<Resource> getFile(@PathVariable Long iaId) {
		try {
			Resource file = financeService.getFile(iaId);
			return ResponseEntity.ok().contentLength(file.contentLength())
					.header("Content-Disposition", "attachment; filename=" + file.getFilename())
					.contentType(MediaType.APPLICATION_PDF).body(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
