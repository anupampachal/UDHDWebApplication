package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IADetailAuditEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IADetailAuditParaInfoEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAStatusOfAuditObservationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIADetailAuditParaInfoRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIADetailAuditRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAStatusOfAuditObservationRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IADetailAuditDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IADetailAuditParaInfoDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStatusOfAuditObservationDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIADetailAuditService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IADetailAuditService implements IIADetailAuditService {

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String internalAuditFolderNickName;

	private final IIADetailAuditRepository detailAuditRepository;
	private final IIADetailAuditParaInfoRepository auditDetailparaRepo;
	private final IInternalAuditService internalAuditService;
	private final IIAStatusOfAuditObservationRepository statusOfAuditRepository;
	private final IFileService fileService;
	private final SecuredUserInfoService securedUser;
	private final IFolderService folderService;

	@Override
	public IADetailAuditDTO retrieveDetailedAudit(Long iaId) {
		log.debug("retrieveDetailedAudit");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IADetailAuditEntity detailAuditEntity = internalAudit.getDetails();
		if (detailAuditEntity == null)
			return null;
		return getDetailAuditDTO.apply(detailAuditEntity, internalAudit);
	}

	@Override
	public IAStatusOfAuditObservationDTO createUpdateAuditObservation(Long iaId, IAStatusOfAuditObservationDTO dto) {
		log.debug("createUpdateAuditObservation");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IADetailAuditEntity details = internalAudit.getDetails();
		if (details == null) {
			details = detailAuditRepository.save(IADetailAuditEntity.builder().internalAudit(internalAudit).build());
		}
		IAStatusOfAuditObservationEntity obs = getAuditObservationEntityFromDTO.apply(dto);
		List<IAStatusOfAuditObservationEntity> auditStatuses = details.getAuditStatuses();
		obs = statusOfAuditRepository.save(obs);
		if (dto.getId() == null) {
			if (auditStatuses == null) {
				auditStatuses = new ArrayList<IAStatusOfAuditObservationEntity>();
			}
			auditStatuses.add(obs);
			details.setAuditStatuses(auditStatuses);
			details = detailAuditRepository.save(details);
		}

		return getAuditObservationDTOFromEn.apply(obs, internalAudit);
	}

	@Override
	public IADetailAuditParaInfoDTO createUpdateAuditParaInfo(Long id, IADetailAuditParaInfoDTO dto) {
		log.debug("createUpdateAuditParaInfo");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(id);
		IADetailAuditEntity details = internalAudit.getDetails();
		if (details == null) {
			details = detailAuditRepository.save(IADetailAuditEntity.builder().internalAudit(internalAudit).build());
		}
		IADetailAuditParaInfoEntity paras = getAuditParaInfoEnFromDTO.apply(dto, fileService);
		List<IADetailAuditParaInfoEntity> auditPara = details.getAuditParaInfos();
		paras = auditDetailparaRepo.save(paras);
		if (dto.getId() == null) {
			if (auditPara==null) {
				auditPara = new ArrayList<IADetailAuditParaInfoEntity>();
			}
			auditPara.add(paras);
			details.setAuditParaInfos(auditPara);
			details = detailAuditRepository.save(details);
		}

		return getAuditParaInfoDTOFromEn.apply(paras, internalAudit);
	}

	@Override
	public List<IAStatusOfAuditObservationDTO> getListOfAuditObservation(Long iaId) {
		log.debug("getListOfAuditObservation");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IADetailAuditEntity details = internalAudit.getDetails();
		return details.getAuditStatuses().stream().map(st -> getAuditObservationDTOFromEn.apply(st, internalAudit))
				.collect(Collectors.toList());
	}

	@Override
	public List<IADetailAuditParaInfoDTO> getListOfAuditPara(Long iaId) {
		log.debug("getListOfAuditPara");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IADetailAuditEntity details = internalAudit.getDetails();
		return details.getAuditParaInfos().stream().map(st -> getAuditParaInfoDTOFromEn.apply(st, internalAudit))
				.collect(Collectors.toList());
	}

	@Override
	public IAStatusOfAuditObservationDTO getAuditObservationById(Long auditObsId, Long iaId) {
		log.debug("getAuditObservationById");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		return getAuditObservationDTOFromEn.apply(
				(statusOfAuditRepository.findById(auditObsId).orElseThrow(() -> new EntityNotFoundException())),
				internalAudit);
	}

	@Override
	public IADetailAuditParaInfoDTO getAuditParaById(Long auditParaId, Long iaId) {
		log.debug("getAuditParaById");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		return getAuditParaInfoDTOFromEn.apply(
				auditDetailparaRepo.findById(auditParaId).orElseThrow(() -> new EntityNotFoundException()),
				internalAudit);
	}

	@Override
	public IADetailAuditParaInfoDTO uploadParaInfoFile(MultipartFile file, Long paraInfoId, Long iaId) {
		IADetailAuditParaInfoEntity paraInfo = auditDetailparaRepo.findById(paraInfoId)
				.orElseThrow(() -> new EntityNotFoundException());
		FileEntity fileE = handleFileSave(file);
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		paraInfo.setFile(fileE);
		return getAuditParaInfoDTOFromEn.apply(auditDetailparaRepo.save(paraInfo), ia);
	}

	@Override
	public Resource getFile(Long paraInfoId) {
		IADetailAuditParaInfoEntity paraInfo = auditDetailparaRepo.findById(paraInfoId)
				.orElseThrow(() -> new EntityNotFoundException());

		try {
			if (paraInfo.getFile() != null) {
				return fileService.getFile(paraInfo.getFile().getFileId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
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

	private BiFunction<IADetailAuditParaInfoEntity, InternalAuditEntity, IADetailAuditParaInfoDTO> getAuditParaInfoDTOFromEn = (
			en, ia) -> IADetailAuditParaInfoDTO.builder().iaId(ia.getId()).id(en.getId())
					.auditParaNo(en.getAuditParaNo()).actionStatus(en.getActionStatus())
					.amountInvolved(en.getAmountInvolved()).auditParaHeading(en.getAuditParaHeading())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.recoveryCompleted(en.getRecoveryCompleted()).recoveryProposed(en.getRecoveryProposed()).build();

	private BiFunction<IAStatusOfAuditObservationEntity, InternalAuditEntity, IAStatusOfAuditObservationDTO> getAuditObservationDTOFromEn = (
			en, ia) -> IAStatusOfAuditObservationDTO.builder().iaId(ia.getId()).id(en.getId())
					.auditParticularsWithDate(en.getAuditParticularsWithDate())
					.nosAndDateOfComplianceReport(en.getNosAndDateOfComplianceReport())
					.totalAmtOfRecovery(en.getTotalAmtOfRecovery())
					.totalAuditParasWithCashRecMade(en.getTotalAuditParasWithCashRecMade())
					.totalAuditParasWithCashRecProposed(en.getTotalAuditParasWithCashRecProposed())
					.totalAuditParasWithCorrectiveActionReq(en.getTotalAuditParasWithCorrectiveActionReq())
					.totalNosOfAuditParas(en.getTotalNosOfAuditParas())
					.totalNosOfParasWithNoAction(en.getTotalNosOfParasWithNoAction()).build();

	private BiFunction<IADetailAuditEntity, InternalAuditEntity, IADetailAuditDTO> getDetailAuditDTO = (en,
			ia) -> IADetailAuditDTO.builder()
					.auditStatuses(en.getAuditStatuses().parallelStream()
							.map(st -> getAuditObservationDTOFromEn.apply(st, en.getInternalAudit()))
							.collect(Collectors.toList()))
					.auditParaInfos(en.getAuditParaInfos().parallelStream()
							.map(dt -> getAuditParaInfoDTOFromEn.apply(dt, ia)).collect(Collectors.toList()))
					.iaId(en.getInternalAudit().getId()).build();

	private Function<IAStatusOfAuditObservationDTO, IAStatusOfAuditObservationEntity> getAuditObservationEntityFromDTO = dto -> IAStatusOfAuditObservationEntity
			.builder().id(dto.getId()).auditParticularsWithDate(dto.getAuditParticularsWithDate())
			.nosAndDateOfComplianceReport(dto.getNosAndDateOfComplianceReport())
			.totalAmtOfRecovery(dto.getTotalAmtOfRecovery())
			.totalAuditParasWithCashRecMade(dto.getTotalAuditParasWithCashRecMade())
			.totalAuditParasWithCashRecProposed(dto.getTotalAuditParasWithCashRecProposed())
			.totalAuditParasWithCorrectiveActionReq(dto.getTotalAuditParasWithCorrectiveActionReq())
			.totalNosOfAuditParas(dto.getTotalNosOfAuditParas())
			.totalNosOfParasWithNoAction(dto.getTotalNosOfParasWithNoAction()).build();

	private BiFunction<IADetailAuditParaInfoDTO, IFileService, IADetailAuditParaInfoEntity> getAuditParaInfoEnFromDTO = (
			dto, fileService) -> IADetailAuditParaInfoEntity.builder().auditParaNo(dto.getAuditParaNo())
					.actionStatus(dto.getActionStatus()).amountInvolved(dto.getAmountInvolved())
					.auditParaHeading(dto.getAuditParaHeading())
					.file(dto.getFileId() != null ? fileService.getFileEntityById(dto.getFileId()) : null)
					.recoveryCompleted(dto.getRecoveryCompleted()).recoveryProposed(dto.getRecoveryProposed()).build();

}
