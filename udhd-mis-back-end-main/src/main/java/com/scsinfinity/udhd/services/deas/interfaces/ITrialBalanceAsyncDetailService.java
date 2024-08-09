package com.scsinfinity.udhd.services.deas.interfaces;

import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.services.deas.dto.TrialBalanceUploadReportSummaryDTO;
import com.scsinfinity.udhd.services.deas.dto.TrialBalanceXlsxStructureDataDTO;

public interface ITrialBalanceAsyncDetailService {

	void handleTrialBalanceUploadDataOperation(MultipartFile file,FileEntity fileE);

	
}
