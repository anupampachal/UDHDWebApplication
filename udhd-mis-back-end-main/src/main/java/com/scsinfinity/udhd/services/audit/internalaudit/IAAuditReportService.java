package com.scsinfinity.udhd.services.audit.internalaudit;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.AuditStepEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.AuditEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.repositories.audit.IAuditRepository;
import com.scsinfinity.udhd.services.audit.dto.AuditCreationDto;
import com.scsinfinity.udhd.services.audit.dto.AuditDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IAuditService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IAAuditReportService implements IAuditService {

	private final SecuredUserInfoService securedUser;
	private final IAuditRepository auditRepository;
	private final IULBService ulbService;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	@Transactional
	public AuditEntity createUpdateAuditEntity(AuditCreationDto auditCreationDto) {
		AuditEntity auditEntity = creatAuditEntityFromAuditDto(auditCreationDto);
		return auditRepository.save(auditEntity);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public AuditDTO createAudit(AuditCreationDto auditCreationDto) {
		AuditEntity auditEntity = creatAuditEntityFromAuditDto(auditCreationDto);
		auditRepository.save(auditEntity);
		return createAuditDto(auditEntity);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public AuditDTO retrieveAudit(final Long id) {
		AuditEntity auditEntity = auditRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Audit Entity not found for id: %s", id)));
		return createAuditDto(auditEntity);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public void deleteAudit(final Long id) {
		auditRepository.deleteById(id);
	}

	private AuditDTO createAuditDto(AuditEntity auditEntity) {
		return AuditDTO.builder().id(auditEntity.getId()).title(auditEntity.getTitle())
				.startDate(auditEntity.getStartDate()).endDate(auditEntity.getEndDate())
				.description(auditEntity.getDescription()).auditStatus(auditEntity.getAuditStatus()).build();
	}

	@Transactional
	private AuditEntity creatAuditEntityFromAuditDto(AuditCreationDto auditCreationDto) {
		Long ulbId = auditCreationDto.getUlbId();

		log.info("Fetching ULB By Id: {}", ulbId);
		ULBEntity ulbEntity = ulbService.findULBEntityById(ulbId);
		UserEntity user = securedUser.getCurrentUserInfo();

		return AuditEntity.builder().id(auditCreationDto.getId()).title(auditCreationDto.getTitle())
				.startDate(auditCreationDto.getStartDate()).endDate(auditCreationDto.getEndDate())
				.stepEnum(AuditStepEnum.IA_DRAFT)
				.currentStepOwner(user.getUserProfile())
				.auditType(auditCreationDto.getType()).description(auditCreationDto.getDescription()).ulb(ulbEntity)
				.auditStatus(auditCreationDto.getAuditStatus())
				.build();
	}
}
