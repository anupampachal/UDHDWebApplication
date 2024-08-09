package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAPartCAuditObservationDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartCAuditObservationEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAUtilisationOfGrantsLineItemDTO;

public interface IIAAuditObservationPartCService {
	
	IAPartCAuditObservationDTO createUpdatePartC(IAPartCAuditObservationDTO partCDTO);

	IAPartCAuditObservationDTO getPartCString(Long iaID, IAPartCAuditObservationEnum partCEnum);

}
