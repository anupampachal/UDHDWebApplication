package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.scsinfinity.udhd.services.common.BooleanResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditRecommendationsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAExecutiveSummaryEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAResultsAndFindingsStrengthEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAResultsAndFindingsWeaknessEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditRecommendationsRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAExecutiveSummaryRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAResultsAndFindingsStrengthRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAResultsAndFindingsWeaknessRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExSumCommentOpinionAcknowledgementDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExSumCommentOpinionAcknowledgementEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecSummaryStrengthWeaknessOrRecommendationInputDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummaryDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummaryIntroductionRequestDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAExecutiveSummarySWOREnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStengthWeaknessOrRecommendationOutputDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAExecutiveSummaryService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

import io.vavr.Function3;
import io.vavr.Function4;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IAExecutiveSummaryService implements IIAExecutiveSummaryService {

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String internalAuditFolderNickName;

	private final IInternalAuditService internalAuditService;
	private final IIAExecutiveSummaryRepository executiveSummaryRepository;
	private final SecuredUserInfoService securedUser;
	private final IIAResultsAndFindingsStrengthRepository stengthRepo;
	private final IIAResultsAndFindingsWeaknessRepository weaknessRepo;
	private final IIAAuditRecommendationsRepository recommendationRepo;
	private final IFolderService folderService;
	private final IFileService fileService;

	@Override
	public Resource getFile(Long ia) {
		try {
			InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(ia);
			IAExecutiveSummaryEntity summary = internalAudit.getExecutiveSummary();
			FileEntity file = summary.getFileCommentFromMgt();
			return fileService.getFile(file.getFileId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IAExecutiveSummaryDTO uploadExSumCommentFile(MultipartFile fileM, Long ia) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(ia);
		IAExecutiveSummaryEntity summary = internalAudit.getExecutiveSummary();
		if (!verifyData(internalAudit)) {
			return null;
		}
		FileEntity file = handleFileSave(fileM);
		if (summary != null) {
			FileEntity fileToDelete = summary.getFileCommentFromMgt();
			summary.setFileCommentFromMgt(file);

			try {
				fileService.deleteFileById(fileToDelete.getFileId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			summary = IAExecutiveSummaryEntity.builder().internalAudit(internalAudit).fileCommentFromMgt(file).build();
		}
		summary = executiveSummaryRepository.save(summary);
		return getDTO(summary);
	}

	private FileEntity handleFileSave(MultipartFile file) {
		try {
			if (!file.getContentType().toString().equals("application/pdf")) {
				throw new BadRequestAlertException("INVALID_UPLOAD", "INVALID_UPLOAD_ONLY_PDF_ALLOWED",
						"INVALID_UPLOAD_ONLY_PDF_ALLOWED");
			}
			UserEntity currentUser = securedUser.getCurrentUserInfo();
			FolderUserGroupsEnum fodlerUserGroup = FolderUserGroupsEnum.AUTHENTICATED;
			if (currentUser == null) {
				fodlerUserGroup = FolderUserGroupsEnum.UNAUTHENTICATED;
			}
			FolderEntity folder = folderService.getFolderEntityByNickname(internalAuditFolderNickName, fodlerUserGroup);

			return fileService.saveFileAndReturnEntity(file, folder.getFolderId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IAExecutiveSummaryDTO createUpdateIntroductionForExecutiveSummary(
			IAExecutiveSummaryIntroductionRequestDTO dto) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(dto.getIa());
		IAExecutiveSummaryEntity summary = null;

		if (!verifyData(internalAudit)) {
			return null;
		}

		if (internalAudit.getExecutiveSummary() == null) {
			// create case
			summary = IAExecutiveSummaryEntity.builder().internalAudit(internalAudit)
					.periodCovered(dto.getPeriodCovered())
					.executiveOfficerNameForPeriod(dto.getExecutiveOfficerNameForPeriod()).build();
		} else {
			// update case
			summary = internalAudit.getExecutiveSummary();
			summary.setPeriodCovered(dto.getPeriodCovered());
			summary.setExecutiveOfficerNameForPeriod(dto.getExecutiveOfficerNameForPeriod());

		}
		if (summary != null)
			summary = executiveSummaryRepository.save(summary);
		return getDTO(summary);
	}

	@Override
	public IAStengthWeaknessOrRecommendationOutputDTO createUpdateStrengthWeaknessRecommendation(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(swrDTO.getIa());
		if (!verifyData(internalAudit)) {
			return null;
		}
		IAExecutiveSummaryEntity summaryEn = internalAudit.getExecutiveSummary();
		IAStengthWeaknessOrRecommendationOutputDTO summary = null;
		if (summaryEn == null) {
			// create case
			summaryEn = executiveSummaryRepository
					.save(IAExecutiveSummaryEntity.builder().internalAudit(internalAudit).build());
		}
		switch (swrDTO.getType()) {
		case STRENGTH: {
			summary = createUpdateStength(swrDTO, summaryEn);
			break;
		}
		case WEAKNESS: {
			summary = createUpdateWeakness(swrDTO, summaryEn);
			break;
		}
		case RECOMMENDATION: {
			summary = createUpdateRecommendations(swrDTO, summaryEn);
			break;
		}
		}
		return summary;
	}

	@Override
	public IAStengthWeaknessOrRecommendationOutputDTO getSworById(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO) {
		IAStengthWeaknessOrRecommendationOutputDTO output = null;
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(swrDTO.getIa());
		IAExecutiveSummaryEntity execSummary = internalAudit.getExecutiveSummary();
		switch (swrDTO.getType()) {
		case STRENGTH: {
			IAResultsAndFindingsStrengthEntity stength = getStengthById.apply(swrDTO.getSworId(), stengthRepo);
			List<IAResultsAndFindingsStrengthEntity> stengths = execSummary.getStrengths();
			if (!stengths.contains(stength))
				throw new BadRequestAlertException("INCORRECT_INFORMATION_REQUESTED", "INCORRECT_INFORMATION_REQUESTED",
						"INCORRECT_INFORMATION_REQUESTED");
			output = convertToStengthOutputDtO.apply(stength, internalAudit, swrDTO.getType());
			break;
		}
		case WEAKNESS: {
			IAResultsAndFindingsWeaknessEntity weakness = getWeaknessById.apply(swrDTO.getSworId(), weaknessRepo);
			List<IAResultsAndFindingsWeaknessEntity> weaknesses = execSummary.getWeaknesses();
			if (!weaknesses.contains(weakness))
				throw new BadRequestAlertException("INCORRECT_INFORMATION_REQUESTED", "INCORRECT_INFORMATION_REQUESTED",
						"INCORRECT_INFORMATION_REQUESTED");

			output = convertToWeaknessOutputDtO.apply(weakness, internalAudit, swrDTO.getType());
			break;
		}
		case RECOMMENDATION: {
			IAAuditRecommendationsEntity recommendation = getRecommendationById.apply(swrDTO.getSworId(),
					recommendationRepo);
			List<IAAuditRecommendationsEntity> recommendations = execSummary.getRecommendations();
			if (!recommendations.contains(recommendation))
				throw new BadRequestAlertException("INCORRECT_INFORMATION_REQUESTED", "INCORRECT_INFORMATION_REQUESTED",
						"INCORRECT_INFORMATION_REQUESTED");

			output = convertToRecommendationOutputDtO.apply(recommendation, internalAudit, swrDTO.getType());
			break;
		}

		}
		return output;
	}

	@Override
	public IAExecutiveSummaryDTO createUpdateCommentOpinionAcknowledgement(
			IAExSumCommentOpinionAcknowledgementDTO inputDTO) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(inputDTO.getIaId());
		IAExecutiveSummaryEntity execSummary = internalAudit.getExecutiveSummary();
		if (execSummary == null) {
			IAExecutiveSummaryEntity.builder().internalAudit(internalAudit).commentFromMgt(
					inputDTO.getType() == IAExSumCommentOpinionAcknowledgementEnum.COMMENT ? inputDTO.getText() : null)
					.acknowledgement(inputDTO.getType() == IAExSumCommentOpinionAcknowledgementEnum.ACKNOWLEDGEMENT
							? inputDTO.getText()
							: null)
					.overAllOpinion(
							inputDTO.getType() == IAExSumCommentOpinionAcknowledgementEnum.OPINION ? inputDTO.getText()
									: null)
					.build();
		} else {
			switch (inputDTO.getType()) {
			case COMMENT:
				execSummary.setCommentFromMgt(inputDTO.getText());
				break;
			case ACKNOWLEDGEMENT:
				execSummary.setAcknowledgement(inputDTO.getText());
				break;
			case OPINION:
				execSummary.setOverAllOpinion(inputDTO.getText());
				break;

			}

			execSummary = executiveSummaryRepository.save(execSummary);
		}
		return getDTO(execSummary);
	}

	@Override
	public List<IAStengthWeaknessOrRecommendationOutputDTO> getListOfStrengthsWeaknessesOrRecommendations(Long iaId,
			IAExecutiveSummarySWOREnum type) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IAExecutiveSummaryEntity execSummary = internalAudit.getExecutiveSummary();
		switch (type) {
		case STRENGTH:
			return execSummary.getStrengths().stream().map(str -> getSWOR.apply(str.getData(), str.getId(),
					internalAudit.getId(), IAExecutiveSummarySWOREnum.STRENGTH)).collect(Collectors.toList());
		case WEAKNESS:
			return execSummary.getWeaknesses().stream().map(str -> getSWOR.apply(str.getData(), str.getId(),
					internalAudit.getId(), IAExecutiveSummarySWOREnum.STRENGTH)).collect(Collectors.toList());
		case RECOMMENDATION:
			return execSummary.getRecommendations().stream().map(str -> getSWOR.apply(str.getData(), str.getId(),
					internalAudit.getId(), IAExecutiveSummarySWOREnum.STRENGTH)).collect(Collectors.toList());
		}
		return null;
	}

	private Function4<String, Long, Long, IAExecutiveSummarySWOREnum, IAStengthWeaknessOrRecommendationOutputDTO> getSWOR = (
			text, sworId, iaId, type) -> IAStengthWeaknessOrRecommendationOutputDTO.builder().text(text).sworId(sworId)
					.ia(iaId).type(type).build();

	@Override
	public String getCommentOpinionAcknowledgement(IAExSumCommentOpinionAcknowledgementDTO inputDTO) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(inputDTO.getIaId());
		IAExecutiveSummaryEntity execSummary = internalAudit.getExecutiveSummary();
		if (execSummary == null) {
			return null;
		}
		switch (inputDTO.getType()) {
		case COMMENT:
			return execSummary.getCommentFromMgt();
		case ACKNOWLEDGEMENT:
			return execSummary.getAcknowledgement();
		case OPINION:
			return execSummary.getOverAllOpinion();
		}
		return null;
	}

	@Override
	public IAExecutiveSummaryDTO getExecutiveSummaryById(Long ia) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(ia);
		return getDTO(internalAudit.getExecutiveSummary());
	}


	/**
	 * @param swrDTO
	 * @return
	 */
	@Override
	public BooleanResponseDTO deleteStrengthWeaknessRecommendation(IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(swrDTO.getIa());
		if (!verifyData(internalAudit)) {
			return null;
		}
		IAExecutiveSummaryEntity summaryEn = internalAudit.getExecutiveSummary();
		IAStengthWeaknessOrRecommendationOutputDTO summary = null;
		if (summaryEn == null) {
			// create case
			summaryEn = executiveSummaryRepository
					.save(IAExecutiveSummaryEntity.builder().internalAudit(internalAudit).build());
		}
		BooleanResponseDTO responseDTO=null;
		switch (swrDTO.getType()) {
			case STRENGTH: {
				responseDTO= deleteStrength(swrDTO, summaryEn);
				break;
			}
			case WEAKNESS: {
				responseDTO = deleteWeakness(swrDTO, summaryEn);
				break;
			}
			case RECOMMENDATION: {
				responseDTO = deleteRecommendation(swrDTO, summaryEn);
				break;
			}
		}
		return responseDTO;
	}


	private BiFunction<Long, IIAResultsAndFindingsStrengthRepository, IAResultsAndFindingsStrengthEntity> getStengthById = (
			id, repo) -> repo.findById(id).orElseThrow(EntityNotFoundException::new);
	private BiFunction<Long, IIAResultsAndFindingsWeaknessRepository, IAResultsAndFindingsWeaknessEntity> getWeaknessById = (
			id, repo) -> repo.findById(id).orElseThrow(EntityNotFoundException::new);
	private BiFunction<Long, IIAAuditRecommendationsRepository, IAAuditRecommendationsEntity> getRecommendationById = (
			id, repo) -> repo.findById(id).orElseThrow(EntityNotFoundException::new);

	private Function3<IAResultsAndFindingsStrengthEntity, InternalAuditEntity, IAExecutiveSummarySWOREnum, IAStengthWeaknessOrRecommendationOutputDTO> convertToStengthOutputDtO = (
			en, ia, type) -> IAStengthWeaknessOrRecommendationOutputDTO.builder().text(en.getData()).sworId(en.getId())
					.ia(ia.getId()).type(type).build();

	private Function3<IAResultsAndFindingsWeaknessEntity, InternalAuditEntity, IAExecutiveSummarySWOREnum, IAStengthWeaknessOrRecommendationOutputDTO> convertToWeaknessOutputDtO = (
			en, ia, type) -> IAStengthWeaknessOrRecommendationOutputDTO.builder().text(en.getData()).sworId(en.getId())
					.ia(ia.getId()).type(type).build();
	private Function3<IAAuditRecommendationsEntity, InternalAuditEntity, IAExecutiveSummarySWOREnum, IAStengthWeaknessOrRecommendationOutputDTO> convertToRecommendationOutputDtO = (
			en, ia, type) -> IAStengthWeaknessOrRecommendationOutputDTO.builder().text(en.getData()).sworId(en.getId())
					.ia(ia.getId()).type(type).build();

	private IAStengthWeaknessOrRecommendationOutputDTO createUpdateStength(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO, IAExecutiveSummaryEntity summaryEn) {
		IAResultsAndFindingsStrengthEntity strength = IAResultsAndFindingsStrengthEntity.builder()
				.data(swrDTO.getText()).id(swrDTO.getSworId()).build();

		if (swrDTO.getSworId() != null) {
			// update
			strength = stengthRepo.save(strength);
		} else {
			// create
			strength = stengthRepo.save(strength);
			List<IAResultsAndFindingsStrengthEntity> stengths = summaryEn.getStrengths();
			if (stengths == null || stengths.isEmpty()) {
				stengths = new ArrayList<IAResultsAndFindingsStrengthEntity>();
			}
			stengths.add(strength);
			summaryEn.setStrengths(stengths);
			summaryEn = executiveSummaryRepository.save(summaryEn);

		}
		return createIAStengthOutputDTO.apply(strength, summaryEn, IAExecutiveSummarySWOREnum.STRENGTH);// getDTO(summaryEn);
	}

	private BooleanResponseDTO deleteStrength(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO, IAExecutiveSummaryEntity summaryEn) {

		Optional<IAResultsAndFindingsStrengthEntity> strengthO =stengthRepo.findById(swrDTO.getSworId());
		if(!strengthO.isPresent()){
			return new BooleanResponseDTO(false,"Strength not found");
		}
		IAResultsAndFindingsStrengthEntity strength=strengthO.get();
		summaryEn.getStrengths().remove(strength);
		executiveSummaryRepository.save(summaryEn);
		stengthRepo.deleteById(strength.getId());
		return new BooleanResponseDTO(true,"Strength deleted successfully");

	}

	private BooleanResponseDTO deleteWeakness(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO, IAExecutiveSummaryEntity summaryEn) {

		Optional<IAResultsAndFindingsWeaknessEntity> weaknessO =weaknessRepo.findById(swrDTO.getSworId());
		if(!weaknessO.isPresent()){
			return new BooleanResponseDTO(false,"Weakness not found");
		}
		IAResultsAndFindingsWeaknessEntity weaknessEntity=weaknessO.get();
		summaryEn.getWeaknesses().remove(weaknessEntity);
		executiveSummaryRepository.save(summaryEn);
		weaknessRepo.deleteById(weaknessEntity.getId());
		return new BooleanResponseDTO(true,"Weakness deleted successfully");

	}

	private BooleanResponseDTO deleteRecommendation(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO, IAExecutiveSummaryEntity summaryEn) {

		Optional<IAAuditRecommendationsEntity> recommendationO =recommendationRepo.findById(swrDTO.getSworId());
		if(!recommendationO.isPresent()){
			return new BooleanResponseDTO(false,"Recommendation not found");
		}
		IAAuditRecommendationsEntity recommendationE=recommendationO.get();
		summaryEn.getWeaknesses().remove(recommendationE);
		executiveSummaryRepository.save(summaryEn);
		recommendationRepo.deleteById(recommendationE.getId());
		return new BooleanResponseDTO(true,"Recommendation deleted successfully");

	}


	private Function3<IAResultsAndFindingsStrengthEntity, IAExecutiveSummaryEntity, IAExecutiveSummarySWOREnum, IAStengthWeaknessOrRecommendationOutputDTO> createIAStengthOutputDTO = (
			req, summary, sworType) -> IAStengthWeaknessOrRecommendationOutputDTO.builder().text(req.getData())
					.sworId(req.getId()).type(sworType).ia(summary.getInternalAudit().getId()).build();
	private Function3<IAResultsAndFindingsWeaknessEntity, IAExecutiveSummaryEntity, IAExecutiveSummarySWOREnum, IAStengthWeaknessOrRecommendationOutputDTO> createIAWeaknessOutputDTO = (
			req, summary, sworType) -> IAStengthWeaknessOrRecommendationOutputDTO.builder().text(req.getData())
					.sworId(req.getId()).type(sworType).ia(summary.getInternalAudit().getId()).build();
	private Function3<IAAuditRecommendationsEntity, IAExecutiveSummaryEntity, IAExecutiveSummarySWOREnum, IAStengthWeaknessOrRecommendationOutputDTO> createIARecommendationsOutputDTO = (
			req, summary, sworType) -> IAStengthWeaknessOrRecommendationOutputDTO.builder().text(req.getData())
					.sworId(req.getId()).type(sworType).ia(summary.getInternalAudit().getId()).build();

	private IAStengthWeaknessOrRecommendationOutputDTO createUpdateWeakness(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO, IAExecutiveSummaryEntity summaryEn) {
		IAResultsAndFindingsWeaknessEntity weakness = IAResultsAndFindingsWeaknessEntity.builder()
				.data(swrDTO.getText()).id(swrDTO.getSworId()).build();

		if (swrDTO.getSworId() != null) {
			// update
			weakness = weaknessRepo.save(weakness);
		} else {
			// create
			weakness = weaknessRepo.save(weakness);
			List<IAResultsAndFindingsWeaknessEntity> weaknesses = summaryEn.getWeaknesses();
			if (weaknesses == null || weaknesses.isEmpty()) {
				weaknesses = new ArrayList<IAResultsAndFindingsWeaknessEntity>();
			}
			weaknesses.add(weakness);
			summaryEn.setWeaknesses(weaknesses);
			summaryEn = executiveSummaryRepository.save(summaryEn);

		}
		return createIAWeaknessOutputDTO.apply(weakness, summaryEn, IAExecutiveSummarySWOREnum.WEAKNESS);

	}

	private IAStengthWeaknessOrRecommendationOutputDTO createUpdateRecommendations(
			IAExecSummaryStrengthWeaknessOrRecommendationInputDTO swrDTO, IAExecutiveSummaryEntity summaryEn) {
		IAAuditRecommendationsEntity recommendation = IAAuditRecommendationsEntity.builder().data(swrDTO.getText())
				.id(swrDTO.getSworId()).build();

		if (swrDTO.getSworId() != null) {
			// update
			recommendation = recommendationRepo.save(recommendation);
		} else {
			// create
			recommendation = recommendationRepo.save(recommendation);
			List<IAAuditRecommendationsEntity> recommendations = summaryEn.getRecommendations();
			if (recommendations == null || recommendations.isEmpty()) {
				recommendations = new ArrayList<IAAuditRecommendationsEntity>();
			}
			recommendations.add(recommendation);
			summaryEn.setRecommendations(recommendations);
			summaryEn = executiveSummaryRepository.save(summaryEn);

		}
		return createIARecommendationsOutputDTO.apply(recommendation, summaryEn,
				IAExecutiveSummarySWOREnum.RECOMMENDATION);
	}

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

	private IAExecutiveSummaryDTO getDTO(IAExecutiveSummaryEntity en) {
		//@formatter:off
		Function<IAExecutiveSummaryEntity, IAExecutiveSummaryDTO> getDTOFromEntity = ent -> IAExecutiveSummaryDTO
				.builder().id(ent.getId()).internalAuditId(ent.getInternalAudit().getId())
				.periodCovered(ent.getPeriodCovered())
				.executiveOfficerNameForPeriod(ent.getExecutiveOfficerNameForPeriod())
				.overAllOpinion(ent.getOverAllOpinion()).commentFromMgt(ent.getCommentFromMgt())
				.acknowledgement(ent.getAcknowledgement())
				.fileCommentFromMgt(en.getFileCommentFromMgt()!=null?en.getFileCommentFromMgt().getFileId():null)
				.ulbName((ent!=null && ent.getInternalAudit()!=null && ent.getInternalAudit().getAuditEntity()!=null && ent.getInternalAudit().getAuditEntity().getUlb()!=null)?  ent.getInternalAudit().getAuditEntity().getUlb().getName():null)
				 //.strengths(ent.getStrengths())
				 //.weaknesses(ent.getWeaknesses())
				 //.recommendations(ent.getRecommendations())
				.build();
		return getDTOFromEntity.apply(en);
		//@formatter:on
	}

}
