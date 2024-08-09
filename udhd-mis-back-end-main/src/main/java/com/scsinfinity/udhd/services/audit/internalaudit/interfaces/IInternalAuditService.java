package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.dto.IAAuditCommentDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStep1RequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStep1ResponseDTO;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import org.springframework.security.access.prepost.PreAuthorize;

public interface IInternalAuditService {

	IAStep1ResponseDTO createIAWithStep1(IAStep1RequestDTO step1RequestDTO);

	IAStep1ResponseDTO findInternalAuditById(Long id);

	InternalAuditEntity findInternalAuditEntityById(Long id);

	GenericResponseObject<IAStep1ResponseDTO> loadInternalAuditByPage(Pagination<IAStep1ResponseDTO> data);

	BooleanResponseDTO deleteIA(Long id);

	Boolean isUserAllowedToCreateUpdateDeleteIA(UserEntity user, ULBEntity ulb);

	List<ULBDTO> getULBsAllowedForIA();

	Long getTotalIADuring(LocalDate startDate, LocalDate endDate);


	BooleanResponseDTO sendInternalAuditForReviewToSLPMU(Long id);

	IAStep1ResponseDTO assignIAAuditToMe(Long iaAuditId);

	IAAuditCommentDTO addCommentsForIAAuditReviewBySLPMUAndInternalAuditor(Long iaId, String comment);

	List<IAAuditCommentDTO> getCommentsForIA(Long iaId);

	IAStep1ResponseDTO approveOrRejectBySLPMUAudit(Boolean view, Long iaId) throws BadRequestAlertException;

	GenericResponseObject<IAStep1ResponseDTO> loadIAAuditUnassingedByPage(Pagination<IAStep1ResponseDTO> data);

	GenericResponseObject<IAStep1ResponseDTO> loadIAAuditAssingedToMeByPage(Pagination<IAStep1ResponseDTO> data);
}
