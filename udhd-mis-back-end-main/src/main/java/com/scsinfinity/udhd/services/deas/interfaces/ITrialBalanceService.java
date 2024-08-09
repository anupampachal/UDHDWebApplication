package com.scsinfinity.udhd.services.deas.interfaces;

import java.time.LocalDate;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.TrialBalanceUploadReportSummaryDTO;

public interface ITrialBalanceService {

	Boolean uploadTrialBalance(MultipartFile file);

	TrialBalanceUploadReportSummaryDTO getTrialBalanceFileUploadSummary(Long id);

	GenericResponseObject<TrialBalanceUploadReportSummaryDTO> getTrialBalanceFileUploadSummaryPage(
			@RequestBody Pagination<TrialBalanceUploadReportSummaryDTO> data);

	Resource getSummaryFile(String fileId);

	Resource getTBFile(String fileId);

	Long getTBDuring(LocalDate startDate, LocalDate endDate);
}
