package com.scsinfinity.udhd.services.audit.internalaudit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAFinanceStatementStatusDeasEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAParticularsWithFinanceStatusEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAStatusOfImplementationOfDeasEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IATallyInfoEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAFinanceRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAFinanceStatementStatusDeasRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAParticularsWithFinanceStatusRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAStatusOfImplementationOfDeasRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIATallyInfoRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IInternalAuditRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAFinanceStatementStatusDeasDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAParticularsWithFinanceStatusDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAStatusOfImplementationOfDeasDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IATallyInfoDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IStatusOfImplementationOfDeasService;
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
public class StatusOfImplementationOfDeasService implements IStatusOfImplementationOfDeasService {

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String internalAuditFolderNickName;

	private final IIAStatusOfImplementationOfDeasRepository statusOfImplementationRepo;
	private final IInternalAuditService internalAuditService;
	private final IInternalAuditRepository iaRepo;
	private final IIAFinanceRepository financeRepo;
	private final IIATallyInfoRepository tallyRepo;
	private final IIAFinanceStatementStatusDeasRepository financeStatementDeasRepo;
	private final IIAParticularsWithFinanceStatusRepository particularsWithFinanceStatusRepo;
	private final IFileService fileService;
	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final DateUtil dateUtil = new DateUtil();

	@Override
	public IATallyInfoDTO createUpdateTallyStatus(IATallyInfoDTO dto) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(dto.getIaId());
		IAFinanceEntity financeEntity = ia.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(ia).build());
			ia.setFinance(financeEntity);
			iaRepo.save(ia);
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();

		if (status == null) {
			// create case
			status = statusOfImplementationRepo
					.save(IAStatusOfImplementationOfDeasEntity.builder().finance(financeEntity).build());
		}

		LocalDate periodFrom = dateUtil.getDateFromPattern(dto.getPeriodFrom());
		LocalDate periodTo = dateUtil.getDateFromPattern(dto.getPeriodTo());
		IATallyInfoEntity tallyInfo = tallyRepo
				.save(getTallyInfoEntityFromDTO.apply(dto, status, periodFrom, periodTo));

		return getTallyInfoDTOFromEntity.apply(tallyInfo, status, ia);
	}

	@Override
	public Long createUpdateDescription(Long ia, String dto) {
		InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(ia);
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(iaEn).build());
			iaEn.setFinance(financeEntity);
			iaRepo.save(iaEn);
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			// create case
			status = statusOfImplementationRepo
					.save(IAStatusOfImplementationOfDeasEntity.builder().finance(financeEntity).build());
		}

		status.setDescription(dto);
		status = statusOfImplementationRepo.save(status);
		return status.getId();
	}

	private Function3<IAFinanceStatementStatusDeasEntity, Long, Long, IAFinanceStatementStatusDeasDTO> getFinanceDTO = (
			en, iaId, stEnId) -> IAFinanceStatementStatusDeasDTO.builder().id(en.getId())
					.annualFinanceStatement(en.getAnnualFinanceStatement())
					.fixedAssetsRegister(en.getFixedAssetsRegister()).iaId(iaId).id(en.getId())
					.openingBalanceRegister(en.getOpeningBalanceRegister()).particulars(en.getParticulars())
					.propertyTaxRegister(en.getPropertyTaxRegister()).statusOfDeasId(stEnId).build();

	private Function3<IAParticularsWithFinanceStatusEntity, Long, Long, IAParticularsWithFinanceStatusDTO> getParticularsWithFinanceStatusDTO = (
			paEn, IaId, stEnId) -> IAParticularsWithFinanceStatusDTO.builder()
			.iaId(IaId).id(paEn.getId())
					.particulars(paEn.getParticulars()).statusOfDeasId(stEnId).value(paEn.getValue()).build();

	private BiFunction<IAParticularsWithFinanceStatusDTO, IAStatusOfImplementationOfDeasEntity, IAParticularsWithFinanceStatusEntity> getParticularsEntity = (
			pDto, stEn) -> IAParticularsWithFinanceStatusEntity.builder().id(pDto.getId())
					.particulars(pDto.getParticulars()).value(pDto.getValue()).build();

	private BiFunction<IAFinanceStatementStatusDeasDTO, IAStatusOfImplementationOfDeasEntity, IAFinanceStatementStatusDeasEntity> getFinanceStatusEntity = (
			dto, st) -> IAFinanceStatementStatusDeasEntity.builder().id(dto.getId())
					.annualFinanceStatement(dto.getAnnualFinanceStatement())
					.fixedAssetsRegister(dto.getFixedAssetsRegister())
					.openingBalanceRegister(dto.getOpeningBalanceRegister()).particulars(dto.getParticulars())
					.propertyTaxRegister(dto.getPropertyTaxRegister()).build();

	@Override
	public IAFinanceStatementStatusDeasDTO createUpdateFinanceStatementStatus(
			IAFinanceStatementStatusDeasDTO financeStatementDTO) {
		InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(financeStatementDTO.getIaId());
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(iaEn).build());
			iaEn.setFinance(financeEntity);
			iaRepo.save(iaEn);
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			// create case
			status = statusOfImplementationRepo
					.save(IAStatusOfImplementationOfDeasEntity.builder().finance(financeEntity).build());
		}

		IAFinanceStatementStatusDeasEntity financeStatementStatusDeas = getFinanceStatusEntity
				.apply(financeStatementDTO, status);
		financeStatementStatusDeas = financeStatementDeasRepo.save(financeStatementStatusDeas);
		status.setFinanceStatementStatus(financeStatementStatusDeas);
		statusOfImplementationRepo.save(status);
		return getFinanceDTO.apply(financeStatementStatusDeas, iaEn.getId(), status.getId());
	}

	@Override
	public IAParticularsWithFinanceStatusDTO createUpdateParticularsWithFinanceStatus(
			IAParticularsWithFinanceStatusDTO particularsWithFinanceStatus) {
		InternalAuditEntity iaEn = internalAuditService
				.findInternalAuditEntityById(particularsWithFinanceStatus.getIaId());
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(iaEn).build());
			iaEn.setFinance(financeEntity);
			iaRepo.save(iaEn);
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			// create case
			status = statusOfImplementationRepo
					.save(IAStatusOfImplementationOfDeasEntity.builder().finance(financeEntity).build());
		}
		IAParticularsWithFinanceStatusEntity particulars = getParticularsEntity.apply(particularsWithFinanceStatus,
				status);
		particulars = particularsWithFinanceStatusRepo.save(particulars);
		if (particularsWithFinanceStatus.getId() == null) {
			List<IAParticularsWithFinanceStatusEntity> partList = status.getParticularsWithFinanceStatus();
			if (partList == null) {
				partList = new ArrayList<>();
			}
			partList.add(particulars);
			status.setParticularsWithFinanceStatus(partList);
			status = statusOfImplementationRepo.save(status);
		}

		return getParticularsWithFinanceStatusDTO.apply(particulars, iaEn.getId(), status.getId());
	}

	@Override
	public Long createUpdateStatusOfMunicipalAccCommittee(String statusOfMunicipalAccCommitteeText, Long iaId) {
		InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			financeEntity = financeRepo.save(IAFinanceEntity.builder().internalAudit(iaEn).build());
			iaEn.setFinance(financeEntity);
			iaRepo.save(iaEn);
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			// create case
			status = statusOfImplementationRepo
					.save(IAStatusOfImplementationOfDeasEntity.builder().finance(financeEntity)
							.statusOfMunicipalAccountsCommittee(statusOfMunicipalAccCommitteeText).build());
		} else {
			status.setStatusOfMunicipalAccountsCommittee(statusOfMunicipalAccCommitteeText);
		}
		return status.getId();
	}

	@Override
	public IAStatusOfImplementationOfDeasDTO getStatusOfImplementationOfDeas(Long iaId) {
		InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			return null;
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			return null;
		}
		List<IAParticularsWithFinanceStatusDTO> particularsWithFinanceStatus = status
				.getParticularsWithFinanceStatus() != null
						? status.getParticularsWithFinanceStatus().stream()
								.map(en -> getParticularsWithFinanceStatusDTO.apply(en, iaId, status.getId())).collect(
										Collectors.toList())
						: null;

		IATallyInfoDTO tallyInfo = status.getTallyDetails() != null
				? getTallyInfoDTOFromEntity.apply(status.getTallyDetails(), status, iaEn)
				: null;
		return IAStatusOfImplementationOfDeasDTO.builder().description(status.getDescription()).iaId(iaId)
				.statusId(status.getId())
				.financeStatementStatus(status.getFinanceStatementStatus() != null
						? getFinanceDTO.apply(status.getFinanceStatementStatus(), iaId, status.getId())
						: null)
				.particularsWithFinanceStatus(particularsWithFinanceStatus)
				.statusOfMunicipalAccCommitteeFileId(status.getStatusOfMunicipalAccCommitteeFile() != null
						? status.getStatusOfMunicipalAccCommitteeFile().getFileId()
						: null)
				.statusOfMunicipalAccountsCommittee(status.getStatusOfMunicipalAccountsCommittee())
				.tallyDetails(tallyInfo).build();
	}

	@Override
	public IATallyInfoDTO getTallyInfoDTO(Long iaId) {
		InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			return null;
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			return null;
		}
		IATallyInfoEntity tallyInfo = status.getTallyDetails();

		return getTallyInfoDTOFromEntity.apply(tallyInfo, status, iaEn);
	}

	@Override
	public IAFinanceStatementStatusDeasDTO getFinanceStatementStatusDeas(Long iaId) {
		InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(iaId);
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			return null;
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			return null;
		}
		IAFinanceStatementStatusDeasEntity finEn = status.getFinanceStatementStatus();
		return getFinanceDTO.apply(finEn, iaId, status.getId());
	}

	@Override
	public Resource getFile(Long ia) {
		try {
			InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(ia);
			IAFinanceEntity financeEntity = iaEn.getFinance();
			if (financeEntity == null) {
				return null;
			}
			IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
			if (status == null) {
				return null;
			}
			if (status.getStatusOfMunicipalAccCommitteeFile() != null)
				return fileService.getFile(status.getStatusOfMunicipalAccCommitteeFile().getFileId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String uploadStatusOfMunicipalAccCommitteeFile(MultipartFile fileM, Long ia) {

		InternalAuditEntity iaEn = internalAuditService.findInternalAuditEntityById(ia);
		IAFinanceEntity financeEntity = iaEn.getFinance();
		if (financeEntity == null) {
			return null;
		}
		IAStatusOfImplementationOfDeasEntity status = financeEntity.getDeasImplementationStatus();
		if (status == null) {
			// create case
			status = statusOfImplementationRepo
					.save(IAStatusOfImplementationOfDeasEntity.builder().finance(financeEntity).build());
		}
		if (!verifyData(iaEn)) {
			return null;
		}

		FileEntity file = handleFileSave(fileM);
		if (status != null) {
			FileEntity fileToDelete = status.getStatusOfMunicipalAccCommitteeFile();
			try {
				fileService.deleteFileById(fileToDelete.getFileId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		status.setStatusOfMunicipalAccCommitteeFile(file);
		status = statusOfImplementationRepo.save(status);
		return file.getFileId();
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

	private Function4<IATallyInfoDTO, IAStatusOfImplementationOfDeasEntity, LocalDate, LocalDate, IATallyInfoEntity> getTallyInfoEntityFromDTO = (
			tallyDTO, stEn, periodFrom, periodTO) -> IATallyInfoEntity.builder().id(tallyDTO.getId())
					.periodFrom(periodFrom).periodTo(periodTO).statusOfDeas(stEn).tallyId(tallyDTO.getTallyId())
					.tallySerialNo(tallyDTO.getTallySerialNo()).build();

	private Function3<IATallyInfoEntity, IAStatusOfImplementationOfDeasEntity, InternalAuditEntity, IATallyInfoDTO> getTallyInfoDTOFromEntity = (
			en, status, ia) -> IATallyInfoDTO.builder().iaId(ia.getId()).id(en.getId())
					.periodFrom(en.getPeriodFrom().toString()).periodTo(en.getPeriodTo().toString())
					.statusOfDeasId(status.getId()).tallyId(en.getTallyId()).tallySerialNo(en.getTallySerialNo())
					.build();
}
