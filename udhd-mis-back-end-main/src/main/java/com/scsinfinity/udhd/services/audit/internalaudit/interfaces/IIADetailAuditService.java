package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IADetailAuditDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IADetailAuditParaInfoDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStatusOfAuditObservationDTO;

public interface IIADetailAuditService {

	IADetailAuditDTO retrieveDetailedAudit(Long iaId);

	IAStatusOfAuditObservationDTO createUpdateAuditObservation(Long iaId, IAStatusOfAuditObservationDTO dto);

	IADetailAuditParaInfoDTO createUpdateAuditParaInfo(Long id, IADetailAuditParaInfoDTO dto);

	List<IAStatusOfAuditObservationDTO> getListOfAuditObservation(Long iaId);

	List<IADetailAuditParaInfoDTO> getListOfAuditPara(Long iaId);

	IAStatusOfAuditObservationDTO getAuditObservationById(Long auditObsId, Long iaId);

	IADetailAuditParaInfoDTO getAuditParaById(Long auditParaId, Long iaId);

	IADetailAuditParaInfoDTO uploadParaInfoFile(MultipartFile file, Long paraInfoId, Long iaId);

	Resource getFile(Long paraInfoId);
}
