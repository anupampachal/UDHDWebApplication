package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAPartCAuditObservationDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAuditObjectsWithMonetaryIrregularitiesDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAuditObservationPartALineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartAAuditObsTypeEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartCAuditObservationEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAUtilisationOfGrantsLineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.PartByTypeAuditObsEnum;

public interface IIAAuditObservationService {

	IAAuditObservationPartALineItemDTO createUpdateAuditObservationPartALineItem(
			IAAuditObservationPartALineItemDTO auditObservation);

	IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemById(Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType);

	// List<IAAuditObservationPartALineItemDTO> getAllAuditObservation(Long iaId,
	// IAPartAAuditObsTypeEnum partAType);

	Boolean deleteAuditObservationLineItem(Long auditObservationId, Long id, IAPartAAuditObsTypeEnum partAType);

	IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemByType(Long iaId,
			IAPartAAuditObsTypeEnum partAType);

	List<IAAuditObservationPartALineItemDTO> getOthersTypeList(Long iaId);

	String uploadFileForAuditObservationPartA(MultipartFile file, Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType);

	Resource getFilePartA(Long id, IAPartAAuditObsTypeEnum partAType);

	IAAuditObjectsWithMonetaryIrregularitiesDTO createUpdateAuditObservationPartB(
			IAAuditObjectsWithMonetaryIrregularitiesDTO irregularitiesDTO);

	IAAuditObjectsWithMonetaryIrregularitiesDTO getAuditObsPartBById(Long id);

	List<IAAuditObjectsWithMonetaryIrregularitiesDTO> getAllIrregularitiesForPartB(Long iaId,
			PartByTypeAuditObsEnum type);

	Boolean deletePartBAuditObservation(Long id, PartByTypeAuditObsEnum type);

	String uploadFileForPartBAuditObservation(MultipartFile file, Long id, Long iaId);

	Resource getFilePartB(Long id);

	IANonComplianceOfTDSNVATDTO createUpdateTDSVATStatute(IANonComplianceOfTDSNVATDTO compliance);

	IANonComplianceOfTDSNVATDTO getTDSVATStatuteOtherById(Long id, Long iaId, IANonComplianceOfTDSNVATEnum type);

	List<IANonComplianceOfTDSNVATDTO> getAllTDSVATStatuteOfTypeOfTypeOthers(Long iaId);

	IANonComplianceOfTDSNVATDTO getTDSVATStatuteByIaId(Long iaId, IANonComplianceOfTDSNVATEnum type);

	Boolean deletePartBTDSVATStatute(Long iaId, IANonComplianceOfTDSNVATEnum type);

	Boolean deletePartBTDSVATStatuteOthers(Long id, Long iaId);

	String uploadPartBTDSVATStatute(MultipartFile file, Long id, Long iaId, IANonComplianceOfTDSNVATEnum type);

	Resource getTDSVATPartB(String id);

	IAUtilisationOfGrantsLineItemDTO createUpdateUtilisationOfGrantLineItem(
			IAUtilisationOfGrantsLineItemDTO lineItemDTO);

	IAUtilisationOfGrantsLineItemDTO getLineItemById(Long id, Long iaId);

	List<IAUtilisationOfGrantsLineItemDTO> getAllLineItemsByIaId(Long iaId);

	Boolean deleteLineItem(Long id, Long iaId);

	String uploadFileUtilizationOfGrantLine(MultipartFile file, Long iaId);

	Resource getUtilizationOfGrantLineItemFile(Long id);

	IAPartCAuditObservationDTO createUpdatePartC(IAPartCAuditObservationDTO partCDTO);

	IAPartCAuditObservationDTO getPartCString(Long iaID, IAPartCAuditObservationEnum partCEnum);

}
