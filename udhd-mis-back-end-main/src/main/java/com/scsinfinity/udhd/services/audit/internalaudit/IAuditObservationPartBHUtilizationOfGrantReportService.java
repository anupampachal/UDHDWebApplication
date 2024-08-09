package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditObservationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditObservationPartBEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAUtilisationOfGrantsEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAUtilisationOfGrantsLineItemEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationPartBRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAUtilisationOfGrantsLineItemRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAUtilisationOfGrantsRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAUtilisationOfGrantsLineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAuditObservationPartBHUtilizationOfGrantReportService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IAuditObservationPartBHUtilizationOfGrantReportService
		implements IIAuditObservationPartBHUtilizationOfGrantReportService {

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String internalAuditFolderNickName;
	private final IInternalAuditService internalAuditService;
	private final IFileService fileService;
	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IIAAuditObservationRepository auditObservationRepository;
	private final IIAAuditObservationPartBRepository auditObservationPartBRepository;
	private final IIAUtilisationOfGrantsRepository grantsRepo;
	private final IIAUtilisationOfGrantsLineItemRepository utilisationGrantsLineItemRepo;

	@Override
	public IAUtilisationOfGrantsLineItemDTO createUpdateUtilisationOfGrantLineItem(
			IAUtilisationOfGrantsLineItemDTO lineItemDTO) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(lineItemDTO.getIaId());
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();

		if (auditObservation == null) {
			auditObservation = auditObservationRepository
					.save(IAAuditObservationEntity.builder().internalAudit(internalAudit).build());
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();

		if (partB == null) {
			partB = auditObservationPartBRepository
					.save(IAAuditObservationPartBEntity.builder().auditObservation(auditObservation).build());
			auditObservation.setPartB(partB);
			auditObservationRepository.save(auditObservation);
		}

		IAUtilisationOfGrantsEntity grantsData = partB.getHUtilisationOfGrants();
		if (grantsData == null) {
			grantsData = grantsRepo.save(IAUtilisationOfGrantsEntity.builder()
					//.auditObservationPartB(partB)
					.build());
		}
		partB.setHUtilisationOfGrants(grantsData);
		auditObservationPartBRepository.save(partB);

		List<IAUtilisationOfGrantsLineItemEntity> lineItems = grantsData.getUtilisationLineItemList();
		if (lineItems == null) {
			lineItems = new ArrayList<IAUtilisationOfGrantsLineItemEntity>();
		}
		IAUtilisationOfGrantsLineItemEntity lineItemEnToAddOrModify = utilisationGrantsLineItemRepo
				.save(getEntityFromLineItemDTO.apply(lineItemDTO));

		if (lineItemDTO.getId() == null) {
			// create case
			lineItems.add(lineItemEnToAddOrModify);
			grantsData.setUtilisationLineItemList(lineItems);
		}
		grantsRepo.save(grantsData);

		return getLineItemDTOFromEn.apply(lineItemEnToAddOrModify, internalAudit);
	}

	@Override
	public IAUtilisationOfGrantsLineItemDTO getLineItemById(Long id, Long iaId) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IAUtilisationOfGrantsLineItemEntity lineItem = getLineItemForId(id, iaId, internalAudit);
		return getLineItemDTOFromEn.apply(lineItem, internalAudit);
	}

	@Override
	public List<IAUtilisationOfGrantsLineItemDTO> getAllLineItemsByIaId(Long iaId) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();

		if (auditObservation == null)
			return null;

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();

		if (partB == null)
			return null;

		IAUtilisationOfGrantsEntity grantsData = partB.getHUtilisationOfGrants();
		if (grantsData == null)
			return null;

		List<IAUtilisationOfGrantsLineItemEntity> lineItems = grantsData.getUtilisationLineItemList();
		return lineItems.stream().map(en -> getLineItemDTOFromEn.apply(en, internalAudit)).collect(Collectors.toList());
	}

	@Override
	public Boolean deleteLineItem(Long id, Long iaId) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();

		if (auditObservation == null)
			return false;

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();

		if (partB == null)
			return false;

		IAUtilisationOfGrantsEntity grantsData = partB.getHUtilisationOfGrants();
		if (grantsData == null)
			return false;

		List<IAUtilisationOfGrantsLineItemEntity> lineItems = grantsData.getUtilisationLineItemList();

		Optional<IAUtilisationOfGrantsLineItemEntity> grantsO = lineItems.stream().filter(en -> en.getId() == id)
				.collect(Collectors.reducing((a, b) -> null));
		if (!grantsO.isPresent())
			return false;

		IAUtilisationOfGrantsLineItemEntity lineItem = grantsO.get();
		lineItems.remove(lineItem);
		grantsRepo.save(grantsData);
		utilisationGrantsLineItemRepo.delete(lineItem);
		return true;
	}

	@Override
	public String uploadFileUtilizationOfGrantLine(MultipartFile file, Long iaId) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IAUtilisationOfGrantsEntity utilization = getGrantsEntity(internalAudit);

		if (!verifyData(internalAudit))
			return null;

		FileEntity fileE = uploadFile(file, utilization.getFile());
		utilization.setFile(fileE);
		grantsRepo.save(utilization);
		return fileE.getFileId();
	}

	@Override
	public Resource getUtilizationOfGrantLineItemFile(Long id) {
		try {
			InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(id);
			IAUtilisationOfGrantsEntity utilization = getGrantsEntity(internalAudit);
			if (utilization.getFile() == null)
				return null;
			return fileService.getFile(utilization.getFile().getFileId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private BiFunction<IAUtilisationOfGrantsLineItemEntity, InternalAuditEntity, IAUtilisationOfGrantsLineItemDTO> getLineItemDTOFromEn = (
			en, ia) -> IAUtilisationOfGrantsLineItemDTO.builder().expenses(en.getExpenses())
					.fundReceived(en.getFundReceived()).iaId(ia.getId()).id(en.getId())
					.letterNoNDate(en.getLetterNoNDate()).pendingUC(en.getPendingUC()).schemeName(en.getSchemeName())
					.submittedUC(en.getSubmittedUC()).build();

	private Function<IAUtilisationOfGrantsLineItemDTO, IAUtilisationOfGrantsLineItemEntity> getEntityFromLineItemDTO = dto -> IAUtilisationOfGrantsLineItemEntity
			.builder().id(dto.getId()).expenses(dto.getExpenses()).fundReceived(dto.getFundReceived())
			.letterNoNDate(dto.getLetterNoNDate()).pendingUC(dto.getPendingUC()).schemeName(dto.getSchemeName())
			.submittedUC(dto.getSubmittedUC()).build();

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

	private FileEntity uploadFile(MultipartFile fileM, FileEntity existingFile) {

		FileEntity file = handleFileSave(fileM);
		if (existingFile != null) {
			try {
				fileService.deleteFileById(existingFile.getFileId());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return file;
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

	private IAUtilisationOfGrantsEntity getGrantsEntity(InternalAuditEntity internalAudit) {
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();

		if (auditObservation == null)
			return null;

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();

		if (partB == null)
			return null;

		return partB.getHUtilisationOfGrants();
	}

	private IAUtilisationOfGrantsLineItemEntity getLineItemForId(Long id, Long iaId,
			InternalAuditEntity internalAudit) {

		IAUtilisationOfGrantsEntity grantsData = getGrantsEntity(internalAudit);
		if (grantsData == null)
			return null;

		List<IAUtilisationOfGrantsLineItemEntity> lineItems = grantsData.getUtilisationLineItemList();
		if (lineItems == null)
			return null;

		Optional<IAUtilisationOfGrantsLineItemEntity> grants = lineItems.stream().filter(en -> en.getId() == id)
				.collect(Collectors.reducing((a, b) -> null));
		if (!grants.isPresent())
			return null;

		return grants.get();
	}
}
