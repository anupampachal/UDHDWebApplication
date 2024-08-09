package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
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
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationPartAService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationPartBService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationPartCService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IAAuditObservationService implements IIAAuditObservationService {

	private final IIAAuditObservationPartAService partAService;
	private final IIAAuditObservationPartBService auditObservationPartBService;
	private final IIAAuditObservationPartCService partCService;

	@Override
	public IAAuditObservationPartALineItemDTO createUpdateAuditObservationPartALineItem(
			IAAuditObservationPartALineItemDTO auditObservation) {
		log.debug("createUpdateAuditObservationPartA");
		return partAService.createUpdateAuditObservationPartALineItem(auditObservation);
	}

	@Override
	public IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemById(Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType) {
		return partAService.getAuditObservationPartALineItemById(id, iaId, partAType);
	}

	@Override
	public Boolean deleteAuditObservationLineItem(Long auditObservationId, Long id, IAPartAAuditObsTypeEnum partAType) {
		return partAService.deleteAuditObservationLineItem(auditObservationId, id, partAType);
	}

	@Override
	public String uploadFileForAuditObservationPartA(MultipartFile file, Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType) {
		return partAService.uploadFileForAuditObservationPartA(file, id, iaId, partAType);
	}

	@Override
	public Resource getFilePartA(Long id, IAPartAAuditObsTypeEnum partAType) {
		return partAService.getFilePartA(id, partAType);
	}

	@Override
	public IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemByType(Long iaId,
			IAPartAAuditObsTypeEnum partAType) {
		return partAService.getAuditObservationPartALineItemByType(iaId, partAType);
	}

	@Override
	public List<IAAuditObservationPartALineItemDTO> getOthersTypeList(Long iaId) {
		return partAService.getOthersTypeList(iaId);
	}

	@Override
	public IAAuditObjectsWithMonetaryIrregularitiesDTO createUpdateAuditObservationPartB(
			IAAuditObjectsWithMonetaryIrregularitiesDTO irregularitiesDTO) {
		return auditObservationPartBService.createUpdateAuditObservationPartB(irregularitiesDTO);
	}

	@Override
	public IAAuditObjectsWithMonetaryIrregularitiesDTO getAuditObsPartBById(Long id) {
		return auditObservationPartBService.getAuditObsPartBById(id);
	}

	@Override
	public List<IAAuditObjectsWithMonetaryIrregularitiesDTO> getAllIrregularitiesForPartB(Long iaId,
			PartByTypeAuditObsEnum type) {
		return auditObservationPartBService.getAllIrregularitiesForPartB(iaId, type);
	}

	@Override
	public Boolean deletePartBAuditObservation(Long id, PartByTypeAuditObsEnum type) {
		return auditObservationPartBService.deletePartBAuditObservation(id, type);
	}

	@Override
	public String uploadFileForPartBAuditObservation(MultipartFile file, Long id, Long iaId) {
		return auditObservationPartBService.uploadFileForPartBAuditObservation(file, id, iaId);
	}

	@Override
	public Resource getFilePartB(Long id) {
		try {
			return auditObservationPartBService.getFilePartB(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IANonComplianceOfTDSNVATDTO createUpdateTDSVATStatute(IANonComplianceOfTDSNVATDTO compliance) {
		return auditObservationPartBService.createUpdateTDSVATStatute(compliance);
	}

	@Override
	public IANonComplianceOfTDSNVATDTO getTDSVATStatuteOtherById(Long id, Long iaId,
			IANonComplianceOfTDSNVATEnum type) {
		return auditObservationPartBService.getTDSVATStatuteOtherById(id, iaId, type);
	}

	@Override
	public List<IANonComplianceOfTDSNVATDTO> getAllTDSVATStatuteOfTypeOfTypeOthers(Long iaId) {
		return auditObservationPartBService.getAllTDSVATStatuteOfTypeOfTypeOthers(iaId);
	}

	@Override
	public IANonComplianceOfTDSNVATDTO getTDSVATStatuteByIaId(Long iaId, IANonComplianceOfTDSNVATEnum type) {
		return auditObservationPartBService.getTDSVATStatuteByIaId(iaId, type);
	}

	@Override
	public String uploadPartBTDSVATStatute(MultipartFile file, Long id, Long iaId,IANonComplianceOfTDSNVATEnum type) {
		return auditObservationPartBService.uploadPartBTDSVATStatute(file, id,iaId,type);
	}

	@Override
	public Boolean deletePartBTDSVATStatute(Long iaId, IANonComplianceOfTDSNVATEnum type) {
		return auditObservationPartBService.deletePartBTDSVATStatute(iaId, type);
	}

	@Override
	public Boolean deletePartBTDSVATStatuteOthers(Long id, Long iaId) {
		return auditObservationPartBService.deletePartBTDSVATStatuteOthers(id, iaId);
	}

	@Override
	public Resource getTDSVATPartB(String id) {
		return auditObservationPartBService.getTDSVATPartB(id);
	}

	@Override
	public IAUtilisationOfGrantsLineItemDTO createUpdateUtilisationOfGrantLineItem(
			IAUtilisationOfGrantsLineItemDTO lineItemDTO) {
		return auditObservationPartBService.createUpdateUtilisationOfGrantLineItem(lineItemDTO);
	}

	@Override
	public IAUtilisationOfGrantsLineItemDTO getLineItemById(Long id, Long iaId) {
		return auditObservationPartBService.getLineItemById(id, iaId);
	}

	@Override
	public List<IAUtilisationOfGrantsLineItemDTO> getAllLineItemsByIaId(Long iaId) {
		return auditObservationPartBService.getAllLineItemsByIaId(iaId);
	}

	@Override
	public Boolean deleteLineItem(Long id, Long iaId) {
		return auditObservationPartBService.deleteLineItem(id,iaId);
	}

	@Override
	public String uploadFileUtilizationOfGrantLine(MultipartFile file, Long iaId) {
		return auditObservationPartBService.uploadFileUtilizationOfGrantLine(file, iaId);
	}

	@Override
	public Resource getUtilizationOfGrantLineItemFile(Long id) {
		return auditObservationPartBService.getUtilizationOfGrantLineItemFile(id);
	}

	@Override
	public IAPartCAuditObservationDTO createUpdatePartC(IAPartCAuditObservationDTO partCDTO) {
		return partCService.createUpdatePartC(partCDTO);
	}

	@Override
	public IAPartCAuditObservationDTO getPartCString(Long iaID, IAPartCAuditObservationEnum partCEnum) {
		return partCService.getPartCString(iaID, partCEnum);
	}

}
