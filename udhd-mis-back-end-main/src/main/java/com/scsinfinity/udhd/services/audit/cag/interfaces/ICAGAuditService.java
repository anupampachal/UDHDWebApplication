package com.scsinfinity.udhd.services.audit.cag.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.scsinfinity.udhd.dao.entities.audit.cag.CAGAuditEntity;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGAuditDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGCommentDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGComplianceDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGCriteriaDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditCommentDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditComplianceDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditCriteriaDTO;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

public interface ICAGAuditService {

	CAGAuditDTO findAuditById(Long id);
	
	CAGAuditEntity findAuditEntityById(Long id);

	AuditCriteriaDTO findAuditCriteriaById(Long id);
	
	ULBDTO getULBsForCreateUpdate();

	CAGAuditDTO createUpdateBasicCAGAudit(CAGAuditDTO auditDto);

	CAGCriteriaDTO createUpdateCriteriaForCAGAudit(CAGCriteriaDTO criteriaDTO);

	CAGComplianceDTO createUpdateComplianceForCAGAuditCriteria(CAGComplianceDTO compliance);

	CAGCommentDTO createUpdateCAGCommentForCAGAuditCriteria(CAGCommentDTO comment);

	List<AuditComplianceDTO> getComplianceForCriteria(CAGCriteriaDTO criteria);

	List<CAGCommentDTO> getCommentsForCriteria(CAGCriteriaDTO criteria);

	GenericResponseObject<AuditCriteriaDTO> loadCriteriasByPage(Pagination<AuditCriteriaDTO> data);

	GenericResponseObject<AuditComplianceDTO> loadCompliancesByPage(Pagination<AuditComplianceDTO> data);

	GenericResponseObject<AuditCommentDTO> loadCommentsByPage(Pagination<AuditCommentDTO> data);

	GenericResponseObject<CAGAuditDTO> loadCAGAuditByPage(Pagination<CAGAuditDTO> data);

	AuditComplianceDTO findAuditComplianceById(Long id);

	AuditCommentDTO findAuditCommentById(Long id);

	Long getTotalCAGDuring(LocalDate startDate, LocalDate endDate);

	Boolean deleteBasicCAGAudit(Long id);
	Boolean deleteCAGAuditCriterion(Long id);

	BooleanResponseDTO sendCAGAuditForReviewToSLPMU(Long id);

	CAGAuditDTO assignCAGAuditToMe(Long agirAuditId);

	AuditCommentDTO addCommentsForCAGAuditReviewBySLPMU(Long categoryId, String comment);

	CAGAuditDTO approveOrRejectBySLPMUAudit(Boolean view, Long aGIRAuditEntityId) throws BadRequestAlertException;

	GenericResponseObject<CAGAuditDTO> loadCAGAuditUnassingedByPage(Pagination<CAGAuditDTO> data);


	GenericResponseObject<CAGAuditDTO> loadCAGAuditAssingedToMeByPage(Pagination<CAGAuditDTO> data);


}
