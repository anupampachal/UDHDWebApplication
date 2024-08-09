package com.scsinfinity.udhd.services.deas.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrialBalanceXlsxStructureDataDTO {

	private String ulbName;
	private Long ulbId;
	private FinancialRealDatesDTO finDate;
	private List<LedgerTransactionDTO> trialBalanceInputs;
	private TrialBalanceSheetCreditAndDebitSumDTO trialCreditAndDebitSum;
	private TrialBalanceSheetCreditAndDebitSumDTO sheetCreditAndDebitSum;
	private MultipartFile inputXlsx;
	private FileEntity file;

}
