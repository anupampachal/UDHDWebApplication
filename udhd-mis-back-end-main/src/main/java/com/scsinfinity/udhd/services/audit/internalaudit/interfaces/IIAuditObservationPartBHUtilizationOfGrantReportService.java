package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAUtilisationOfGrantsLineItemDTO;

public interface IIAuditObservationPartBHUtilizationOfGrantReportService {

	IAUtilisationOfGrantsLineItemDTO createUpdateUtilisationOfGrantLineItem(
			IAUtilisationOfGrantsLineItemDTO lineItemDTO);

	IAUtilisationOfGrantsLineItemDTO getLineItemById(Long id, Long iaId);

	List<IAUtilisationOfGrantsLineItemDTO> getAllLineItemsByIaId(Long iaId);

	Boolean deleteLineItem(Long id, Long iaId);

	String uploadFileUtilizationOfGrantLine(MultipartFile file, Long iaId);

	Resource getUtilizationOfGrantLineItemFile(Long id);

}
