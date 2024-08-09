package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATEnum;

public interface IIAuditObservationPartBFNonComplianceOfVATnTDSService {

	IANonComplianceOfTDSNVATDTO createUpdateTDSVATStatute(IANonComplianceOfTDSNVATDTO compliance);

	IANonComplianceOfTDSNVATDTO getTDSVATStatuteOtherById(Long id, Long iaId, IANonComplianceOfTDSNVATEnum type);

	IANonComplianceOfTDSNVATDTO getTDSVATStatuteByIaId(Long iaId, IANonComplianceOfTDSNVATEnum type);

	List<IANonComplianceOfTDSNVATDTO> getAllTDSVATStatuteOfTypeOfTypeOthers(Long iaId);

	Boolean deletePartBTDSVATStatute(Long iaId, IANonComplianceOfTDSNVATEnum type);

	String uploadPartBTDSVATStatute(MultipartFile file, Long id, Long iaId, IANonComplianceOfTDSNVATEnum type);

	Resource getTDSVATPartB(String id);

	Boolean deletePartBTDSVATStatuteOthers(Long id, Long iaId);

}
