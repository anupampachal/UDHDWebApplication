package com.scsinfinity.udhd.services.audit.agir.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.scsinfinity.udhd.dao.entities.audit.agir.AGIRAuditEntity;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRAuditDTO;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRCommentDTO;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRComplianceDTO;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRCriteriaDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditCommentDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditComplianceDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditCriteriaDTO;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IAGIRAuditService {

	AGIRAuditDTO findAuditById(Long id);

	Long getTotalAGIRDuring(LocalDate startDate, LocalDate endDate);

	AGIRAuditEntity findAuditEntityById(Long id);

	AuditCriteriaDTO findAuditCriteriaById(Long id);

	ULBDTO getULBsForCreateUpdate();

	AGIRAuditDTO createUpdateBasicAGIRAudit(AGIRAuditDTO auditDto);

	Boolean deleteBasicAGIRAudit(Long id);

	AGIRCriteriaDTO createUpdateCriteriaForAGIRAudit(AGIRCriteriaDTO criteriaDTO);

	Boolean deleteAGIRAuditCriterion(Long id);

	AGIRComplianceDTO createUpdateComplianceForAGIRAuditCriteria(AGIRComplianceDTO compliance);

	AGIRCommentDTO createUpdateAGIRCommentForAGIRAuditCriteria(AGIRCommentDTO comment);

	List<AuditComplianceDTO> getComplianceForCriteria(AGIRCriteriaDTO criteria);

	List<AGIRCommentDTO> getCommentsForCriteria(AGIRCriteriaDTO criteria);

	GenericResponseObject<AuditCriteriaDTO> loadCriteriasByPage(Pagination<AuditCriteriaDTO> data);

	GenericResponseObject<AuditComplianceDTO> loadCompliancesByPage(Pagination<AuditComplianceDTO> data);

	GenericResponseObject<AuditCommentDTO> loadCommentsByPage(Pagination<AuditCommentDTO> data);

	GenericResponseObject<AGIRAuditDTO> loadAGIRAuditByPage(Pagination<AGIRAuditDTO> data);

	AuditComplianceDTO findAuditComplianceById(Long id);

	AuditCommentDTO findAuditCommentById(Long id);

	BooleanResponseDTO sendAGIRAuditForReviewToSLPMU(Long id);

	//@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_ACCOUNT +  "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT+"')")
	AGIRAuditDTO assignAGIRAuditToMe(Long agirAuditId);


	AuditCommentDTO addCommentsForAGIRAuditReviewBySLPMU(Long categoryId, String comment);

	//@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +"')")
	AGIRAuditDTO approveOrRejectBySLPMUAudit(Boolean view, Long aGIRAuditEntityId) throws BadRequestAlertException;

	GenericResponseObject<AGIRAuditDTO> loadAGIRAuditUnassingedByPage(Pagination<AGIRAuditDTO> data);


	GenericResponseObject<AGIRAuditDTO> loadAGIRAuditAssingedToMeByPage(Pagination<AGIRAuditDTO> data);
}
