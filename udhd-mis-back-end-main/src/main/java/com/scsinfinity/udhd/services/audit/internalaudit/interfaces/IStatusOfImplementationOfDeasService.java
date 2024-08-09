package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinanceStatementStatusDeasDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAParticularsWithFinanceStatusDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStatusOfImplementationOfDeasDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IATallyInfoDTO;

public interface IStatusOfImplementationOfDeasService {

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
