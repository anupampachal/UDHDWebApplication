package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAuditObservationPartALineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartAAuditObsTypeEnum;

public interface IIAAuditObservationPartAService {

	IAAuditObservationPartALineItemDTO createUpdateAuditObservationPartALineItem(
			IAAuditObservationPartALineItemDTO auditObservation);

	IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemById(Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType);
	
	IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemByType( Long iaId,
			IAPartAAuditObsTypeEnum partAType);

	// List<IAAuditObservationPartALineItemDTO> getAllAuditObservation(Long iaId,
	// IAPartAAuditObsTypeEnum partAType);

	Boolean deleteAuditObservationLineItem(Long auditObservationId,Long id, IAPartAAuditObsTypeEnum partAType);

	String uploadFileForAuditObservationPartA(MultipartFile file, Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType);

	Resource getFilePartA(Long id, IAPartAAuditObsTypeEnum partAType);

	List<IAAuditObservationPartALineItemDTO> getOthersTypeList(Long iaId);

}
