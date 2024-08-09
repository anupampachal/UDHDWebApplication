package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAdministrationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditTeamMunicipalAuditAssistantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditTeamMunicipalAuditExpertsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditTeamTeamLeadersEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAdministrationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditTeamMunicipalAuditAssistantsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditTeamMunicipalAuditExpertsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditTeamTeamLeadersRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.InternalAuditRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.AdminAuditTeamTypeEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAdministrationAuditTeamDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAdministrationDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAdministrationService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IAAdministrationService implements IIAAdministrationService {
	private final IIAAdministrationRepository administrationRepository;
	private final InternalAuditRepository internalAuditRepository;
	private final SecuredUserInfoService securedUser;
	private final IInternalAuditService internalAuditService;
	private final IIAAuditTeamTeamLeadersRepository teamLeadRepo;
	private final IIAAuditTeamMunicipalAuditExpertsRepository municipalAuditExpertsRepo;
	private final IIAAuditTeamMunicipalAuditAssistantsRepository municipalAuditAssistantRepo;

	@Override
	public IAAdministrationDTO createAdministration(final IAAdministrationDTO administrationDTO) {
		InternalAuditEntity internalAudit = internalAuditRepository.getById(administrationDTO.getInternalAuditId());
		if (!verifyData(internalAudit)) {
			return null;
		}
		IAAdministrationEntity adminstration = internalAudit.getAdministration();
		IAAdministrationEntity detailAuditEntity = administrationRepository
				.save(administrationDTO.getAdministrationEntityWithInternalAudit(
						adminstration != null ? adminstration.getId() : null, internalAudit));
		return createAdministrationDto(detailAuditEntity);
	}

	@Override
	public IAAdministrationDTO retrieveAdministration(final Long iaId) {
		InternalAuditEntity internalAudit = internalAuditRepository.getById(iaId);
		return createAdministrationDto(internalAudit.getAdministration());
	}

	@Override
	public IAAdministrationAuditTeamDTO createAuditTeamMemberByType(Long iaId,
			IAAdministrationAuditTeamDTO teamMember) {
		InternalAuditEntity ia = internalAuditRepository.getById(iaId);
		if (!verifyData(ia)) {
			return null;
		}
		IAAdministrationEntity administration = ia.getAdministration();
		if (administration == null) {
			administration = administrationRepository.save(IAAdministrationEntity.builder().internalAudit(ia).build());
		}
		return createUpdateAdminAuditTeamByType(ia, administration, teamMember);
	}

	@Override
	public List<IAAdministrationAuditTeamDTO> getListByType(Long iaId, AdminAuditTeamTypeEnum type) {
		InternalAuditEntity ia = internalAuditRepository.getById(iaId);
		return getAuditTeamByType(ia, type);
	}

	@Override
	public IAAdministrationAuditTeamDTO getMemberByTypeAndId(Long iaId, Long memId, AdminAuditTeamTypeEnum type) {
		InternalAuditEntity ia = internalAuditRepository.getById(iaId);
		return getAuditTeamMemberByType(ia, memId, type);

	}

	@Override
	public Boolean deleteMemberByType(Long iaId, Long id, AdminAuditTeamTypeEnum type) {
		InternalAuditEntity ia = internalAuditRepository.getById(iaId);
		return deleteAuditTeamMemberByType(ia, id, type);
	}

	private Boolean deleteAuditTeamMemberByType(InternalAuditEntity ia, Long id, AdminAuditTeamTypeEnum type) {
		IAAdministrationEntity administration = ia.getAdministration();
		if (administration == null) {
			return null;
		}
		switch (type) {
		case TEAM_LEAD: {
			List<IAAuditTeamTeamLeadersEntity> teamLeads = administration.getTeamLeaders();
			if (teamLeads == null)
				return false;
			Optional<IAAuditTeamTeamLeadersEntity> memberToRemoveO = teamLeads.stream().filter(en -> en.getId() == id)
					.collect(Collectors.reducing((a, b) -> null));
			if (!memberToRemoveO.isPresent())
				return false;
			teamLeads.remove(memberToRemoveO.get());
			administrationRepository.save(administration);
			teamLeadRepo.delete(memberToRemoveO.get());
			return true;
		}
		case MUNICIPAL_EXPERTS: {
			List<IAAuditTeamMunicipalAuditExpertsEntity> experts = administration.getMunicipalExperts();
			if (experts == null)
				return false;
			Optional<IAAuditTeamMunicipalAuditExpertsEntity> memberToRemoveO = experts.stream()
					.filter(en -> en.getId() == id).collect(Collectors.reducing((a, b) -> null));
			if (!memberToRemoveO.isPresent())
				return false;
			experts.remove(memberToRemoveO.get());
			administrationRepository.save(administration);
			municipalAuditExpertsRepo.delete(memberToRemoveO.get());
			return true;
		}
		case MUNICIPAL_ASSISTANTS: {
			List<IAAuditTeamMunicipalAuditAssistantsEntity> assistants = administration.getMunicipalAssistants();
			if (assistants == null)
				return false;
			Optional<IAAuditTeamMunicipalAuditAssistantsEntity> memberToRemoveO = assistants.stream()
					.filter(en -> en.getId() == id).collect(Collectors.reducing((a, b) -> null));
			if (!memberToRemoveO.isPresent())
				return false;
			assistants.remove(memberToRemoveO.get());
			administrationRepository.save(administration);
			municipalAuditAssistantRepo.delete(memberToRemoveO.get());
			return true;
		}
		}
		return false;
	}

	private List<IAAdministrationAuditTeamDTO> getAuditTeamByType(InternalAuditEntity ia, AdminAuditTeamTypeEnum type) {
		IAAdministrationEntity administration = ia.getAdministration();
		if (administration == null) {
			return null;
		}
		List<IAAdministrationAuditTeamDTO> membersOfAuditTeam = null;
		switch (type) {
		case TEAM_LEAD: {
			List<IAAuditTeamTeamLeadersEntity> teamLeads = administration.getTeamLeaders();
			membersOfAuditTeam = teamLeads.stream().map(en -> getTeamLeadAsDTO.apply(ia, en))
					.collect(Collectors.toList());
			break;
		}
		case MUNICIPAL_EXPERTS: {
			List<IAAuditTeamMunicipalAuditExpertsEntity> experts = administration.getMunicipalExperts();
			membersOfAuditTeam = experts.stream().map(en -> getAuditExpertsAsDTO.apply(ia, en))
					.collect(Collectors.toList());
			break;
		}
		case MUNICIPAL_ASSISTANTS: {
			List<IAAuditTeamMunicipalAuditAssistantsEntity> assistants = administration.getMunicipalAssistants();
			membersOfAuditTeam = assistants.stream().map(en -> getAuditAssistantsAsDTO.apply(ia, en))
					.collect(Collectors.toList());

		}
		}
		return membersOfAuditTeam;
	}

	private IAAdministrationAuditTeamDTO getAuditTeamMemberByType(InternalAuditEntity ia, Long id,
			AdminAuditTeamTypeEnum type) {
		IAAdministrationEntity administration = ia.getAdministration();
		if (administration == null) {
			return null;
		}
		Optional<IAAdministrationAuditTeamDTO> tmO = null;
		switch (type) {
		case TEAM_LEAD: {
			;
			tmO = administration.getTeamLeaders().stream().filter(en -> en.getId() == id)
					.map(en -> getTeamLeadAsDTO.apply(ia, en)).collect(Collectors.reducing((a, b) -> null));
			break;

		}
		case MUNICIPAL_EXPERTS: {
			tmO = administration.getMunicipalExperts().stream().filter(en -> en.getId() == id)
					.map(en -> getAuditExpertsAsDTO.apply(ia, en)).collect(Collectors.reducing((a, b) -> null));
			break;
		}
		case MUNICIPAL_ASSISTANTS: {
			tmO = administration.getMunicipalAssistants().stream().filter(en -> en.getId() == id)
					.map(en -> getAuditAssistantsAsDTO.apply(ia, en)).collect(Collectors.reducing((a, b) -> null));

		}
		}
		if (tmO.isPresent()) {
			return tmO.get();
		} else {
			return null;
		}
	}

	private IAAdministrationAuditTeamDTO createUpdateAdminAuditTeamByType(InternalAuditEntity ia,
			IAAdministrationEntity administration, IAAdministrationAuditTeamDTO teamMember) {
		switch (teamMember.getType()) {
		case TEAM_LEAD: {
			List<IAAuditTeamTeamLeadersEntity> teamLeads = administration.getTeamLeaders();
			if (teamLeads == null) {
				teamLeads = new ArrayList<IAAuditTeamTeamLeadersEntity>();
			}
			IAAuditTeamTeamLeadersEntity tl = getTeamLeadFromDTO.apply(teamMember);
			tl = teamLeadRepo.save(tl);
			if (teamMember.getId() == null) {
				teamLeads.add(tl);
				administrationRepository.save(administration);
			}
			return getTeamLeadAsDTO.apply(ia, tl);
		}
		case MUNICIPAL_EXPERTS: {
			List<IAAuditTeamMunicipalAuditExpertsEntity> experts = administration.getMunicipalExperts();
			if (experts == null) {
				experts = new ArrayList<IAAuditTeamMunicipalAuditExpertsEntity>();
			}
			IAAuditTeamMunicipalAuditExpertsEntity tl = getMunicipalAuditExpertsEntityFromDTO.apply(teamMember);
			tl = municipalAuditExpertsRepo.save(tl);
			if (teamMember.getId() == null) {
				experts.add(tl);
				administrationRepository.save(administration);
			}
			return getAuditExpertsAsDTO.apply(ia, tl);

		}
		case MUNICIPAL_ASSISTANTS: {
			List<IAAuditTeamMunicipalAuditAssistantsEntity> assistants = administration.getMunicipalAssistants();
			if (assistants == null) {
				assistants = new ArrayList<IAAuditTeamMunicipalAuditAssistantsEntity>();
			}
			IAAuditTeamMunicipalAuditAssistantsEntity tl = getMunicipalAuditAssistantsEntityFromDTO.apply(teamMember);
			tl = municipalAuditAssistantRepo.save(tl);
			if (teamMember.getId() == null) {
				assistants.add(tl);
				administrationRepository.save(administration);
			}
			return getAuditAssistantsAsDTO.apply(ia, tl);

		}
		}
		return null;
	}

	private Function<IAAdministrationAuditTeamDTO, IAAuditTeamTeamLeadersEntity> getTeamLeadFromDTO = dto -> IAAuditTeamTeamLeadersEntity
			.builder().id(dto.getId()).name(dto.getName()).build();

	private BiFunction<InternalAuditEntity, IAAuditTeamTeamLeadersEntity, IAAdministrationAuditTeamDTO> getTeamLeadAsDTO = (
			ia, tle) -> IAAdministrationAuditTeamDTO.builder().iaId(ia.getId()).id(tle.getId()).name(tle.getName())
					.type(AdminAuditTeamTypeEnum.TEAM_LEAD).build();
	private Function<IAAdministrationAuditTeamDTO, IAAuditTeamMunicipalAuditExpertsEntity> getMunicipalAuditExpertsEntityFromDTO = dto -> IAAuditTeamMunicipalAuditExpertsEntity
			.builder().id(dto.getId()).name(dto.getName()).build();
	private BiFunction<InternalAuditEntity, IAAuditTeamMunicipalAuditExpertsEntity, IAAdministrationAuditTeamDTO> getAuditExpertsAsDTO = (
			ia, tle) -> IAAdministrationAuditTeamDTO.builder().type(AdminAuditTeamTypeEnum.MUNICIPAL_EXPERTS)
					.iaId(ia.getId()).id(tle.getId()).name(tle.getName()).build();
	private Function<IAAdministrationAuditTeamDTO, IAAuditTeamMunicipalAuditAssistantsEntity> getMunicipalAuditAssistantsEntityFromDTO = dto -> IAAuditTeamMunicipalAuditAssistantsEntity
			.builder().id(dto.getId()).name(dto.getName()).build();
	private BiFunction<InternalAuditEntity, IAAuditTeamMunicipalAuditAssistantsEntity, IAAdministrationAuditTeamDTO> getAuditAssistantsAsDTO = (
			ia, tle) -> IAAdministrationAuditTeamDTO.builder().type(AdminAuditTeamTypeEnum.MUNICIPAL_ASSISTANTS)
					.iaId(ia.getId()).id(tle.getId()).name(tle.getName()).build();

	private Boolean verifyData(InternalAuditEntity ia) {

		UserEntity user = securedUser.getCurrentUserInfo();
		if (!internalAuditService.isUserAllowedToCreateUpdateDeleteIA(user, ia.getAuditEntity().getUlb())) {
			log.error("unauthorised access tried");
			throw new BadRequestAlertException("UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS");
		}
		if (!ia.getAuditEntity().getAuditStatus().equals(AuditStatusEnum.DRAFT)) {
			log.error("trying to modify data of executive summary which is not in draft status");
			return false;
		}
		return true;

	}

	private IAAdministrationDTO createAdministrationDto(IAAdministrationEntity detailAuditEntity) {
		if (detailAuditEntity == null)
			return null;
		return IAAdministrationDTO.builder().internalAuditId(detailAuditEntity.getInternalAudit().getId())
				.nameOfChairmanMayor(detailAuditEntity.getNameOfChairmanMayor())
				.periodOfServiceChairman(detailAuditEntity.getPeriodOfServiceChairman())
				.nameOfCMO(detailAuditEntity.getNameOfCMO())
				.periodOfServiceCMO(detailAuditEntity.getPeriodOfServiceCMO()).build();
	}

}
