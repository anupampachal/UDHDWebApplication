package com.scsinfinity.udhd.services.audit.cag;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.*;
import com.scsinfinity.udhd.dao.entities.audit.agir.AGIRAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRAuditDTO;
import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.cag.CAGAuditEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditCommentRepository;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditComplianceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditCriteriaRepository;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditRepository;
import com.scsinfinity.udhd.dao.repositories.audit.cag.ICAGAuditRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IULBRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGAuditDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGCommentDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGComplianceDTO;
import com.scsinfinity.udhd.services.audit.cag.dto.CAGCriteriaDTO;
import com.scsinfinity.udhd.services.audit.cag.interfaces.ICAGAuditService;
import com.scsinfinity.udhd.services.audit.dto.AuditCommentDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditComplianceDTO;
import com.scsinfinity.udhd.services.audit.dto.AuditCriteriaDTO;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBInfoForULBUsersDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBsInfoForOtherTypesOfUsers;
import com.scsinfinity.udhd.services.settings.dto.UserInfoDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IUserMgtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CAGAuditService implements ICAGAuditService {
	private final IUserMgtService userMgtService;
	private final ICAGAuditRepository cagAuditRepository;
	private final IAuditRepository auditRepository;
	private final IULBRepository ulbRepository;
	private final IAuditCriteriaRepository auditCriteriaRepository;
	private final IAuditCommentRepository auditCommentRepository;
	private final IAuditComplianceRepository auditComplianceRepository;
	private final IPaginationInfoService<CAGAuditDTO, CAGAuditEntity> paginationInfoServiceForCAGAudit;
	private final IPaginationInfoService<AuditCriteriaDTO, AuditCriteriaEntity> paginationInfoServiceForCriteria;
	private final IPaginationInfoService<AuditComplianceDTO, AuditComplianceEntity> paginationInfoServiceForCompliance;
	private final IPaginationInfoService<AuditCommentDTO, AuditCommentEntity> paginationInfoServiceForComment;

	private final SecuredUserInfoService securedUser;
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public CAGAuditEntity findAuditEntityById(Long id) {
		return cagAuditRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CAGAuditEntity" + id));
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "')")
	public CAGAuditDTO createUpdateBasicCAGAudit(CAGAuditDTO cagAuditDTO) {
		log.debug("createUpdateBasicCAGAudit");
		ULBEntity ulb = ulbRepository.findById(cagAuditDTO.getUlb().getId())
				.orElseThrow(() -> new EntityNotFoundException("ULB" + cagAuditDTO.getUlb().getId()));
		AuditEntity audit = cagAuditDTO.getEntityWithULB(ulb,securedUser.getCurrentUserInfo().getUserProfile());
		if(audit.getId()==null){
			audit.setAuditStatus(AuditStatusEnum.DRAFT);
		}
		audit = auditRepository.save(audit);
		CAGAuditEntity cagAudit = null;

		if (cagAuditDTO.getId() == null) {
			cagAudit = new CAGAuditEntity();

		} else {
			cagAudit = cagAuditRepository.findById(cagAuditDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("CAG Report ID"));
		}
		cagAudit.setAuditEntity(audit);
		return cagAuditRepository.save(cagAudit).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "')")
	public CAGCriteriaDTO createUpdateCriteriaForCAGAudit(CAGCriteriaDTO criteriaDTO) {

		log.debug("createUpdateCriteriaForCAGAudit");
		CAGAuditEntity cagAudit = cagAuditRepository.findById(criteriaDTO.getCagAuditId())
				.orElseThrow(() -> new EntityNotFoundException("CAG Report ID"));

		// 1.is the person allowed to do it?
		if (!isCurrentPersonEligibleForMakingCriteriaAndCompliances(cagAudit.getAuditEntity().getUlb())) {
			throw new BadRequestAlertException("User not allowed to create CAG Report", "CAGReportEntity",
					"CAGReportEntity");
		}

		// 2. save the changes to criteria
		AuditCriteriaEntity auditCriteria = criteriaDTO.getAuditCriteriaEntity(criteriaDTO, cagAudit);
		auditCriteria = auditCriteriaRepository.save(auditCriteria);

		if (!cagAudit.getAuditCriterias().contains(auditCriteria)) {
			cagAudit.getAuditCriterias().add(auditCriteria);
			cagAudit = cagAuditRepository.save(cagAudit);
		}

		CAGCriteriaDTO cagAuditCriteriaDTO = new CAGCriteriaDTO();
		return cagAuditCriteriaDTO.getDTO(auditCriteria.getDTO("CAG"), cagAudit);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public CAGComplianceDTO createUpdateComplianceForCAGAuditCriteria(CAGComplianceDTO compliance) {
		log.debug("createUpdateComplianceForCAGAuditCriteria");
		AuditCriteriaEntity auditCriteria = auditCriteriaRepository.findById(compliance.getAuditCriteriaId())
				.orElseThrow(() -> new EntityNotFoundException(
						"AuditCriteriaEntity id->" + compliance.getAuditCriteriaId()));
		// is the person allowed to do it?
		if (!isCurrentPersonEligibleForMakingCriteriaAndCompliances(
				auditCriteria.getCagAudit().getAuditEntity().getUlb())) {
			throw new BadRequestAlertException("User not allowed to create compliance for CAGReportEntity",
					"CAGReportEntity", "CAGReportEntity");
		}

		// save then changes to compliance
		AuditComplianceEntity auditCompliance = auditComplianceRepository.save(compliance.getEntity(auditCriteria));

		//if (!auditCriteria.getCompliances().contains(auditCompliance)) {
		//	auditCriteria.getCompliances().add(auditCompliance);
			auditCriteria = auditCriteriaRepository.save(auditCriteria);
		//}
		return CAGComplianceDTO.builder().auditCriteriaId(auditCriteria.getId()).comment(auditCompliance.getComment())
				.file(auditCompliance.getFile()).status(auditCompliance.getStatus()).id(auditCompliance.getId())
				.build();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public CAGCommentDTO createUpdateCAGCommentForCAGAuditCriteria(CAGCommentDTO comment) {
		log.debug("createUpdateCAGCommentForCAGAuditCriteria");
		AuditCriteriaEntity auditCriteria = auditCriteriaRepository.findById(comment.getAuditCriteriaId()).orElseThrow(
				() -> new EntityNotFoundException("AuditCriteriaEntity id->" + comment.getAuditCriteriaId()));

		// is the person allowed to do it?
		if (!isCurrentPersonEligibleForMakingComments(auditCriteria.getCagAudit().getAuditEntity().getUlb())) {
			throw new BadRequestAlertException("User not allowed to create comment for CAGReportEntity",
					"CAGReportEntity", "CAGReportEntity");
		}
		// save changes
		AuditCommentEntity auditComment = auditCommentRepository.save(comment.getEntity(auditCriteria));
		if (!auditCriteria.getComment().contains(auditComment)) {
			auditCriteria.getComment().add(auditComment);
			auditCriteria = auditCriteriaRepository.save(auditCriteria);
		}
		return CAGCommentDTO.builder().auditCriteriaId(auditCriteria.getId()).comment(auditComment.getComment())
				.id(auditComment.getId()).build();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public List<AuditComplianceDTO> getComplianceForCriteria(CAGCriteriaDTO criteria) {
		BiFunction<AuditCriteriaEntity, AuditComplianceEntity, AuditComplianceDTO> auditComplianceFunc = (auditCriteria,
				auditCompliance) -> AuditComplianceDTO.builder().auditCriteriaId(auditCriteria.getId())
						.comment(auditCompliance.getComment()).id(auditCompliance.getId())
						.status(auditCompliance.getStatus()).file(auditCompliance.getFile()).build();
		AuditCriteriaEntity auditCriteria = auditCriteriaRepository.findById(criteria.getId())
				.orElseThrow(() -> new EntityNotFoundException("getComplianceForCriteria id->" + criteria.getId()));
		return auditCriteria.getCompliances().stream()
				.map(compliances -> auditComplianceFunc.apply(auditCriteria, compliances)).collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public List<CAGCommentDTO> getCommentsForCriteria(CAGCriteriaDTO criteria) {
		BiFunction<AuditCriteriaEntity, AuditCommentEntity, CAGCommentDTO> auditCommentFunc = (auditCriteria,
				auditComment) -> CAGCommentDTO.builder().auditCriteriaId(auditCriteria.getId())
						.comment(auditComment.getComment()).id(auditComment.getId()).build();

		AuditCriteriaEntity auditCriteria = auditCriteriaRepository.findById(criteria.getId())
				.orElseThrow(() -> new EntityNotFoundException("getCommentsForCriteria id->" + criteria.getId()));
		return auditCriteria.getComment().stream().map(comment -> auditCommentFunc.apply(auditCriteria, comment))
				.collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public GenericResponseObject<AuditCriteriaDTO> loadCriteriasByPage(Pagination<AuditCriteriaDTO> data) {
		log.info("loadCriteriasByPage", data);
		Pageable pageable = paginationInfoServiceForCriteria.getPaginationRequestInfo(data);

		Page<AuditCriteriaEntity> auditCriteriaPage = null;
		if (data.getQueryString() != null) {
			auditCriteriaPage = auditCriteriaRepository.findAllByCagAudit_IdAndTitleIgnoreCaseContaining(pageable,
					data.getId(), data.getQueryString());
		} else {
			auditCriteriaPage = auditCriteriaRepository.findAllByCagAudit_Id(pageable, data.getId());
		}

		Page<AuditCriteriaDTO> auditCriteriaPageDTO = paginationInfoServiceForCriteria.getDataAsDTO(auditCriteriaPage,
				en -> en.getDTO("CAG"));

		return new GenericResponseObject<AuditCriteriaDTO>(auditCriteriaPageDTO.getTotalElements(),
				auditCriteriaPageDTO, data.getPageNo(), data.getPageSize());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public GenericResponseObject<AuditComplianceDTO> loadCompliancesByPage(Pagination<AuditComplianceDTO> data) {
		Pageable pageable = paginationInfoServiceForCompliance.getPaginationRequestInfo(data);

		Page<AuditComplianceEntity> auditCompliancePage = null;
		if (data.getQueryString() != null) {
			auditCompliancePage = auditComplianceRepository.findAllByCriteria_IdAndCommentIgnoreCaseContaining(pageable,
					data.getId(), data.getQueryString());
		} else {
			auditCompliancePage = auditComplianceRepository.findAllByCriteria_Id(pageable, data.getId());
		}

		Page<AuditComplianceDTO> auditComplianceDTOPage = paginationInfoServiceForCompliance
				.getDataAsDTO(auditCompliancePage, en -> en.getDTO());

		return new GenericResponseObject<AuditComplianceDTO>(auditComplianceDTOPage.getTotalElements(),
				auditComplianceDTOPage, data.getPageNo(), data.getPageSize());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public GenericResponseObject<AuditCommentDTO> loadCommentsByPage(Pagination<AuditCommentDTO> data) {
		Pageable pageable = paginationInfoServiceForComment.getPaginationRequestInfo(data);

		Page<AuditCommentEntity> auditCommentEntityPage = null;
		if (data.getQueryString() != null) {
			auditCommentEntityPage = auditCommentRepository.findAllByCriteria_IdAndCommentIgnoreCaseContaining(pageable,
					data.getId(), data.getQueryString());
		} else {
			auditCommentEntityPage = auditCommentRepository.findAllByCriteria_Id(pageable, data.getId());
		}

		Page<AuditCommentDTO> auditCommentDtoPage = paginationInfoServiceForComment.getDataAsDTO(auditCommentEntityPage,
				en -> en.getDTO());

		return new GenericResponseObject<AuditCommentDTO>(auditCommentDtoPage.getTotalElements(), auditCommentDtoPage,
				data.getPageNo(), data.getPageSize());

	}


	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','"+AuthorityConstants.ROLE_SLPMU_UC+"','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public GenericResponseObject<CAGAuditDTO> loadCAGAuditByPage(Pagination<CAGAuditDTO> data) {
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		if (user.getAuthority().getAuthorityType()== AuthorityTypeEnum.OTHERS || user.getAuthority().getAuthorityType()==AuthorityTypeEnum.ULB)
			return loadCAGAuditByPageForULBRelatedUsers(user,data);
		else
			return loadCAGAuditByPageForNonULBRelatedUsers(data);
	}

	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "')")
	private GenericResponseObject<CAGAuditDTO> loadCAGAuditByPageForULBRelatedUsers(UserEntity user, Pagination<CAGAuditDTO> data){
		Pageable pageable = paginationInfoServiceForCAGAudit.getPaginationRequestInfo(data);
		if(user.getUserProfile()!=null && user.getUserProfile().getUserUlbInfo() !=null && user.getUserProfile().getUserUlbInfo().getUlbs()!=null){
			Page<CAGAuditEntity> cagAuditEntityPage = null;
			if (data.getQueryString() != null) {
				List<ULBEntity>ulbsUserMappedTo= user.getUserProfile().getUserUlbInfo().getUlbs();
				List<ULBEntity>ulbsToSearchFrom=new ArrayList<>();
				for(ULBEntity ulb:ulbsUserMappedTo){
					if(ulb.getName().toUpperCase().contains(data.getQueryString().toUpperCase())){
						ulbsToSearchFrom.add(ulb);
					}
				}
				List<AuditEntity> auditEntitiesFromUserMappedULBs= auditRepository.findByUlbIn(ulbsUserMappedTo);
				List<AuditEntity> auditEntitiesToSearchFrom= new ArrayList<>();
				for(AuditEntity audit: auditEntitiesFromUserMappedULBs){
					if(audit.getTitle().toUpperCase().contains(data.getQueryString().toUpperCase())){
						auditEntitiesToSearchFrom.add(audit);
					}
				}
				cagAuditEntityPage = cagAuditRepository
						.findByAuditEntity_UlbInOrAuditEntityIn(pageable,ulbsToSearchFrom,auditEntitiesToSearchFrom);

			} else {
				cagAuditEntityPage = cagAuditRepository.findByAuditEntity_UlbIn(pageable,user.getUserProfile().getUserUlbInfo().getUlbs());
			}

			Page<CAGAuditDTO> cagAuditDTOPage = paginationInfoServiceForCAGAudit.getDataAsDTO(cagAuditEntityPage,
					en -> en.getDTO());

			return new GenericResponseObject<CAGAuditDTO>(cagAuditDTOPage.getTotalElements(), cagAuditDTOPage,
					data.getPageNo(), data.getPageSize());
		}
		else{
			return null;
		}
	}
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	private GenericResponseObject<CAGAuditDTO> loadCAGAuditByPageForNonULBRelatedUsers(Pagination<CAGAuditDTO> data){
		Pageable pageable = paginationInfoServiceForCAGAudit.getPaginationRequestInfo(data);

		Page<CAGAuditEntity> cagAuditEntityPage = null;
		if (data.getQueryString() != null) {
			cagAuditEntityPage = cagAuditRepository
					.findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContaining(pageable,
							data.getQueryString(), data.getQueryString());
		} else {
			cagAuditEntityPage = cagAuditRepository.findAll(pageable);
		}

		Page<CAGAuditDTO> cagAuditDTOPage = paginationInfoServiceForCAGAudit.getDataAsDTO(cagAuditEntityPage,
				en -> en.getDTO());

		return new GenericResponseObject<CAGAuditDTO>(cagAuditDTOPage.getTotalElements(), cagAuditDTOPage,
				data.getPageNo(), data.getPageSize());

	}

	private Boolean isCurrentPersonEligibleForMakingCriteriaAndCompliances(ULBEntity ulb) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Function<Authentication, Boolean> isUserFLIA = authp -> authp.getAuthorities().stream()
				.map(auth1 -> auth1.toString()).filter(autha -> autha.equalsIgnoreCase(AuthorityConstants.ROLE_FLIA))
				.count() > 0;
		Function<ULBsInfoForOtherTypesOfUsers, Boolean> doesFLIAHasThisULB = ulbs -> ulbs.getUlbs().stream()
				.filter(ulbstr -> ulbstr.getId() == ulb.getId()).count() > 0;

		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb
				.getId();
		if (isUserFLIA.apply(auth)) {
			return doesFLIAHasThisULB
					.apply(userMgtService.getUlBsInfoForOtherTypesOfUsers(auth.getPrincipal().toString()));
		} else {
			return isULBUserMappedToThisULB.apply(userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString()));
		}
	}

	private Boolean isULBCMOEligibleToMakeComment(UserInfoDTO userInfo, ULBEntity ulb) {
		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb
				.getId();
		ULBInfoForULBUsersDTO ulbInfo = userMgtService.getUlbInfoForUlbUser(userInfo.getUsername());
		return isULBUserMappedToThisULB.apply(ulbInfo);

	}

	private Boolean isCurrentPersonEligibleForMakingComments(ULBEntity ulb) {
		UserInfoDTO userInfo = userMgtService.getCurrentUserInfo();
		switch (userInfo.getAuthority()) {
		case AuthorityConstants.ROLE_FLIA:
		case AuthorityConstants.ROLE_ULB_ACCOUNTANT:
			return isCurrentPersonEligibleForMakingCriteriaAndCompliances(ulb);

		case AuthorityConstants.ROLE_ULB_CMO:
			return this.isULBCMOEligibleToMakeComment(userInfo, ulb);

		case AuthorityConstants.ROLE_SLPMU_ADMIN:
		case AuthorityConstants.ROLE_SLPMU_ACCOUNT:
		case AuthorityConstants.ROLE_SLPMU_AUDIT:
		case AuthorityConstants.ROLE_SLPMU_UC:

		case AuthorityConstants.ROLE_UDHD_PSEC:
		case AuthorityConstants.ROLE_UDHD_SEC:
		case AuthorityConstants.ROLE_UDHD_SO:
			return true;
		}
		return false;

	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public CAGAuditDTO findAuditById(Long id) {

		return cagAuditRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("CAGAuditDTO" + id))
				.getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public AuditCriteriaDTO findAuditCriteriaById(Long id) {
		return auditCriteriaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("CAGCriteriaDTO" + id)).getDTO("CAG");
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public AuditComplianceDTO findAuditComplianceById(Long id) {
		return auditComplianceRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("AuditComplianceDTO" + id)).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public AuditCommentDTO findAuditCommentById(Long id) {
		return auditCommentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("AuditCommentDTO" + id)).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','"
			+ AuthorityConstants.ROLE_ULB_ACCOUNTANT+ "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT
			+ "')")
	public ULBDTO getULBsForCreateUpdate() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ULBInfoForULBUsersDTO ulbInfo = userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString());
		if(ulbInfo!=null)
			return ulbInfo.getUlb();
		return null;
	}
	
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public Long getTotalCAGDuring(LocalDate startDate, LocalDate endDate) {
		return cagAuditRepository
				.findByAuditEntity_StartDateGreaterThanEqualAndAuditEntity_EndDateLessThanEqual(startDate, endDate)
				.stream().count();
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "')")
	public Boolean deleteBasicCAGAudit(Long id) {
		log.debug("deleteBasicCAGAudit");
		CAGAuditEntity cagAudit = cagAuditRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("CAG Report ID"));
		ULBEntity ulb = cagAudit.getAuditEntity().getUlb();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!cagAudit.getAuditEntity().getAuditStatus().equals(AuditStatusEnum.DRAFT))
			throw new BadRequestAlertException("Audit entity can only be deleted in draft stage", "CAGAuditEntity", "CAGAuditEntity");

		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb
				.getId();
		if (isULBUserMappedToThisULB.apply(userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString())) == false)
			throw new BadRequestAlertException("User not mapped to ULB for which this draft audit is being deleted", "CAGAuditEntity", "CAGAuditEntity");

		List<AuditCriteriaEntity> criterias = cagAudit.getAuditCriterias();
		// delete all the comments associated with all the criteria of this audit entity
		List<AuditCommentEntity> comments = criterias.stream().map(criterion -> criterion.getComment())
				.flatMap(l -> l.stream()).collect(Collectors.toList());
		auditCommentRepository.deleteAll(comments);
		//delete all compliances associated with all the criteria of this audit entity
		List<AuditComplianceEntity> compliances = criterias.stream().map(criterion -> criterion.getCompliances())
				.flatMap(l -> l.stream()).collect(Collectors.toList());
		auditComplianceRepository.deleteAll(compliances);
		// delete all criteria of this audit entity
		auditCriteriaRepository.deleteAll(criterias);
		//delete CAG audit entity of this audit entity
		auditRepository.delete(cagAudit.getAuditEntity());
		//delete audit entity
		cagAuditRepository.delete(cagAudit);
		return true;

	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "')")
	public Boolean deleteCAGAuditCriterion(Long id) {
		log.debug("deleteCAGAuditCriterion");
		AuditCriteriaEntity auditCriteria= auditCriteriaRepository.findById(id)
				.orElseThrow(()->new EntityNotFoundException("CAG Audit Criteria"+id));
		CAGAuditEntity cagAudit = auditCriteria.getCagAudit();
		ULBEntity ulb = cagAudit.getAuditEntity().getUlb();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!cagAudit.getAuditEntity().getAuditStatus().equals(AuditStatusEnum.DRAFT))
			throw new BadRequestAlertException("Audit entity items can only be deleted in draft stage",
					"CAGAuditEntity", "CAGAuditEntity");

		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb
				.getId();
		if (isULBUserMappedToThisULB.apply(userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString())) == false)
			throw new BadRequestAlertException("User not mapped to ULB for which this draft audit is being deleted",
					"CAGAuditEntity", "CAGAuditEntity");


		// delete all the comments associated with all the criteria of this audit entity
		List<AuditCommentEntity> comments = auditCriteria.getComment();
		auditCommentRepository.deleteAll(comments);
		//delete all compliances associated with all the criteria of this audit entity
		List<AuditComplianceEntity> compliances =auditCriteria.getCompliances();
		auditComplianceRepository.deleteAll(compliances);
		// delete all criteria of this audit entity
		auditCriteriaRepository.delete(auditCriteria);
		return true;

	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "')")
	public BooleanResponseDTO sendCAGAuditForReviewToSLPMU(Long id) {
		log.debug("sendCAGAuditForReviewToSLPMU",id);
		CAGAuditEntity cagAudit = cagAuditRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("CAG Report ID"));
		ULBEntity ulb = cagAudit.getAuditEntity().getUlb();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb.getId();

		if (!(cagAudit.getAuditEntity().getAuditStatus().equals(AuditStatusEnum.DRAFT)||cagAudit.getAuditEntity().getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_ULB)))
			return new BooleanResponseDTO(false,"Audit entity can only be send to review in draft stage or in ulb review stage" );

		if (isULBUserMappedToThisULB.apply(userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString())) == false)
			return new BooleanResponseDTO(false,"User not mapped to ULB for which this audit is being send for next step" );

		if(cagAudit.getAuditCriterias().size()<1){
			return new BooleanResponseDTO(false, "There must be one or more criterias.");
		}
		for(AuditCriteriaEntity criteria : cagAudit.getAuditCriterias()){
			if(criteria.getCompliances().size()<1){
				return new BooleanResponseDTO(false, "The audit criteria with id"+criteria.getId()+" does not have any compliances.");
			}
		}

		AuditEntity audit= cagAudit.getAuditEntity();
		audit.setCurrentStepOwner(null);
		audit.setAuditStatus(AuditStatusEnum.IN_REVIEW_SLPMU);
		return new BooleanResponseDTO(true, "CAG Audit with id "+id+ " has successfully been sent to SLPMU team");

	}

	/**
	 * @param cagAuditId
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +  "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT+"')")
	public CAGAuditDTO assignCAGAuditToMe(Long cagAuditId) {
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_SLPMU_AUDIT)){
			return assignCAGAuditToMeSLPMUAudit(cagAuditId);
		}else if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_ULB_ACCOUNTANT)){
			return assignCAGAuditToMeULBACC(cagAuditId);
		}else{
			throw new BadRequestAlertException("Unauthorised operation","Unauthorised operation","Unauthorised operation");
		}

	}
	private CAGAuditDTO assignCAGAuditToMeULBACC(Long cagAuditEntityId) throws BadRequestAlertException{
		CAGAuditEntity cagAuditEntity=cagAuditRepository.findById(cagAuditEntityId).orElseThrow(()->new EntityNotFoundException("CAG Audit entity does not exist"));
		AuditEntity auditEntity=cagAuditEntity.getAuditEntity();
		if(!(auditEntity.getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_ULB)  &&  auditEntity.getCurrentStepOwner()==null)){
			throw new BadRequestAlertException("Either audit is not in review ulb state, or it is already assigned to another ulb employee","CAG AUDIT Entity","");
		}
		ULBEntity ulb = auditEntity.getUlb();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Function<ULBInfoForULBUsersDTO, Boolean> isULBUserMappedToThisULB = ulbIn -> ulbIn.getUlb().getId() == ulb.getId();

		if (isULBUserMappedToThisULB.apply(userMgtService.getUlbInfoForUlbUser(auth.getPrincipal().toString())) == false)
			throw new BadRequestAlertException("User not mapped to ULB for which this audit is being send for next step" ,"CAG Audit Entity","");

		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		UserProfileEntity userProfile=user.getUserProfile();
		auditEntity.setCurrentStepOwner(userProfile);
		auditRepository.save(auditEntity);
		return cagAuditEntity.getDTO();
	}

	private CAGAuditDTO assignCAGAuditToMeSLPMUAudit(Long cagAuditEntityId) throws BadRequestAlertException{
		CAGAuditEntity cagAuditEntity=cagAuditRepository.findById(cagAuditEntityId).orElseThrow(()->new EntityNotFoundException("CAG Audit entity does not exist"));
		AuditEntity auditEntity=cagAuditEntity.getAuditEntity();
		if(!(auditEntity.getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_SLPMU)  &&  auditEntity.getCurrentStepOwner()==null)){
			throw new BadRequestAlertException("Either audit is not in review slpmu state, or it is already assigned to another auditor","CAG AUDIT Entity","");
		}

		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		UserProfileEntity userProfile=user.getUserProfile();
		auditEntity.setCurrentStepOwner(userProfile);
		auditRepository.save(auditEntity);
		return cagAuditEntity.getDTO();
	}


	/**
	 * @param categoryId
	 * @param comment
	 * @return
	 */
	@Override
	public AuditCommentDTO addCommentsForCAGAuditReviewBySLPMU(Long categoryId, String comment) {
		AuditCriteriaEntity criteria= auditCriteriaRepository.findById(categoryId).orElseThrow(()->new EntityNotFoundException("Category is invalid"));
		AuditCommentEntity aComment=AuditCommentEntity.builder().criteria(criteria).comment(comment).build();
		auditCommentRepository.save(aComment);
		return  aComment.getDTO();
	}

	/**
	 * @param view
	 * @param cagAuditEntityId
	 * @return
	 * @throws BadRequestAlertException
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +"')")
	public CAGAuditDTO approveOrRejectBySLPMUAudit(Boolean view, Long cagAuditEntityId) throws BadRequestAlertException {
		CAGAuditEntity cagAuditEntity=cagAuditRepository.findById(cagAuditEntityId).orElseThrow(()->new EntityNotFoundException("CAG Audit entity does not exist"));
		AuditEntity auditEntity=cagAuditEntity.getAuditEntity();
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		if(!(auditEntity.getAuditStatus().equals(AuditStatusEnum.IN_REVIEW_SLPMU)  &&  auditEntity.getCurrentStepOwner().equals(user.getUserProfile()))){
			throw new BadRequestAlertException("Either audit is not in review slpmu state, or you are not the current reviewer for this audit","CAG AUDIT Entity","");
		}
		auditEntity.setCurrentStepOwner(null);
		if(view){
			auditEntity.setAuditStatus(AuditStatusEnum.PUBLISHED);
		}else{
			auditEntity.setAuditStatus(AuditStatusEnum.IN_REVIEW_ULB);
		}
		auditRepository.save(auditEntity);
		return cagAuditEntity.getDTO();

	}

	/**
	 * @param data
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +  "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT+"')")
	public GenericResponseObject<CAGAuditDTO> loadCAGAuditUnassingedByPage(Pagination<CAGAuditDTO> data) {
		Pageable pageable = paginationInfoServiceForCAGAudit.getPaginationRequestInfo(data);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		AuditStatusEnum status=null;
		if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_SLPMU_AUDIT)){
			status=AuditStatusEnum.IN_REVIEW_SLPMU;
		} else if (user.getAuthority().getName().equals(AuthorityConstants.ROLE_ULB_ACCOUNTANT)|| user.getAuthority().equals(AuthorityConstants.ROLE_FLIA)) {
			status=AuditStatusEnum.IN_REVIEW_ULB;
		}else {
			throw new BadRequestAlertException("Unauthorised role accessing data","Unauthorised role accessing data","Unauthorised role accessing data");
		}

		Page<CAGAuditEntity> cagAuditEntityPage = null;
		if (data.getQueryString() != null) {
			cagAuditEntityPage = cagAuditRepository
					.findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContainingAndAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,
							data.getQueryString(), data.getQueryString(),status,null);
		} else {
			cagAuditEntityPage = cagAuditRepository.findAllByAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,status,null);
		}

		Page<CAGAuditDTO> cagAuditDTOPage = paginationInfoServiceForCAGAudit.getDataAsDTO(cagAuditEntityPage,
				en -> en.getDTO());

		return new GenericResponseObject<CAGAuditDTO>(cagAuditDTOPage.getTotalElements(), cagAuditDTOPage,
				data.getPageNo(), data.getPageSize());


	}

	/**
	 * @param data
	 * @return
	 */
	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_AUDIT +  "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT+"')")
	public GenericResponseObject<CAGAuditDTO> loadCAGAuditAssingedToMeByPage(Pagination<CAGAuditDTO> data) {
		Pageable pageable = paginationInfoServiceForCAGAudit.getPaginationRequestInfo(data);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserEntity user= userMgtService.getCurrentUserInfoEntity();
		AuditStatusEnum status=null;
		if(user.getAuthority().getName().equals(AuthorityConstants.ROLE_SLPMU_AUDIT)){
			status=AuditStatusEnum.IN_REVIEW_SLPMU;
		} else if (user.getAuthority().getName().equals(AuthorityConstants.ROLE_ULB_ACCOUNTANT)|| user.getAuthority().equals(AuthorityConstants.ROLE_FLIA)) {
			status=AuditStatusEnum.IN_REVIEW_ULB;
		}else {
			throw new BadRequestAlertException("Unauthorised role accessing data","Unauthorised role accessing data","Unauthorised role accessing data");
		}

		Page<CAGAuditEntity> cagAuditEntityPage = null;
		if (data.getQueryString() != null) {
			cagAuditEntityPage = cagAuditRepository
					.findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContainingAndAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,
							data.getQueryString(), data.getQueryString(),status,user.getUserProfile());
		} else {
			cagAuditEntityPage = cagAuditRepository.findAllByAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(pageable,status,user.getUserProfile());
		}

		Page<CAGAuditDTO> cagAuditDTOPage = paginationInfoServiceForCAGAudit.getDataAsDTO(cagAuditEntityPage,
				en -> en.getDTO());

		return new GenericResponseObject<CAGAuditDTO>(cagAuditDTOPage.getTotalElements(), cagAuditDTOPage,
				data.getPageNo(), data.getPageSize());



	}

}
