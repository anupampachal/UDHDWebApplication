package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAdvertisementTaxEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditObservationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IACustomAuditObservationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IADelayDepositTaxCollectedEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAExcessPaymentAgainstBillEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAMobileTowerTaxEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonLevyOfPropertyHoldingTaxEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IARentOnMunicipalPropertiesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAReportOnFindingsOfFieldEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAdvertisementTaxRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIACustomAuditObservationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIADelayDepositTaxCollectedRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAExcessPaymentAgainstBillRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAMobileTowerTaxRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonLevyOfPropertyHoldingTaxRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIARentOnMunicipalPropertiesRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAReportOnFindingsOfFieldRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAuditObservationPartALineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartAAuditObsTypeEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationPartAService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IAAuditObservationPartAService implements IIAAuditObservationPartAService {

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String internalAuditFolderNickName;
	private final IInternalAuditService internalAuditService;
	private final IIAAuditObservationRepository auditObservationRepository;
	private final IIANonLevyOfPropertyHoldingTaxRepository nonLevyOfPropertyHoldingRepo;
	private final IIADelayDepositTaxCollectedRepository delayDepositTaxCollectedRepo;
	private final IIAMobileTowerTaxRepository mobileTowerTaxRepo;
	private final IIARentOnMunicipalPropertiesRepository rentOnMunicipalPropertiesRepo;
	private final IIAAdvertisementTaxRepository advertisementTaxRepo;
	private final IIAExcessPaymentAgainstBillRepository excessPaymentAgainstBillRepo;
	private final IIAReportOnFindingsOfFieldRepository reportOnFindingOfFieldRepo;
	private final IIACustomAuditObservationRepository customAuditObservationRepo;
	private final IFileService fileService;
	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;

	@Override
	public IAAuditObservationPartALineItemDTO createUpdateAuditObservationPartALineItem(
			IAAuditObservationPartALineItemDTO auditObservation) {
		log.debug("createUpdateAuditObservationPartA");
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(auditObservation.getIaId());
		IAAuditObservationEntity auditObservationEn = ia.getAuditObservation();
		if (auditObservationEn == null) {
			auditObservationEn = auditObservationRepository
					.save(IAAuditObservationEntity.builder().internalAudit(ia).build());
		}

		return saveAsPerType(auditObservation, ia, auditObservationEn);
	}

	@Override
	public IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemByType( Long iaId,
			IAPartAAuditObsTypeEnum partAType) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservationEn = ia.getAuditObservation();
		if (auditObservationEn == null)
			return null;
		if (partAType.equals(partAType.OTHERS)) {
			return null;
		}
		return fetchPerTypeWithoutId(ia, partAType, auditObservationEn);
	}

	@Override
	public List<IAAuditObservationPartALineItemDTO> getOthersTypeList(Long iaId) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservationEn = ia.getAuditObservation();
		if (auditObservationEn == null)
			return null;
		return auditObservationEn.getCustomAuditObservation().stream()
				.map(en -> getAuditObservationFromIACustomAuditObservationEntity.apply(en, ia))
				.collect(Collectors.toList());
	}
	

	private IAAuditObservationPartALineItemDTO fetchPerTypeWithoutId(InternalAuditEntity ia,
			IAPartAAuditObsTypeEnum type, IAAuditObservationEntity auditObservationEn) {
		switch (type) {
		case PROPERTY_HOLDING_TAX: {
			IANonLevyOfPropertyHoldingTaxEntity propertyHoldingTax = auditObservationEn.getPropertyHoldingTax();
			return getAuditObservationFromPropertyTaxEntity.apply(propertyHoldingTax, ia);
		}
		case DELAY_IN_TAX: {
			IADelayDepositTaxCollectedEntity delayDepositTaxCollected = auditObservationEn
					.getDelayDepositTaxCollected();
			return getAuditObservationFromIADelayDepositTaxCollectedEntity.apply(delayDepositTaxCollected, ia);
		}
		case MOBILE_TOWER_TAX: {
			IAMobileTowerTaxEntity mobileTowerTax = auditObservationEn.getMobileTowerTax();
			return getAuditObservationFromIAMobileTowerTaxEntity.apply(mobileTowerTax, ia);
		}
		case RENT_ON_MUNICIPAL_PROPERTIES: {
			IARentOnMunicipalPropertiesEntity rentOnMunicipalProperties = auditObservationEn
					.getRentOnMunicipalProperties();
			return getAuditObservationFromIARentOnMunicipalPropertiesEntity.apply(rentOnMunicipalProperties, ia);
		}
		case ADVERTISEMENT_TAX: {
			IAAdvertisementTaxEntity advertisementTax = auditObservationEn.getAdvertisementTax();
			return getAuditObservationFromIAAdvertisementTaxEntity.apply(advertisementTax, ia);
		}
		case EXCESS_PAYMENT_AGAINST_BILL: {
			IAExcessPaymentAgainstBillEntity excessPaymentAgainstBill = auditObservationEn
					.getExcessPaymentAgainstBill();
			return getAuditObservationFromIAExcessPaymentAgainstBillEntity.apply(excessPaymentAgainstBill, ia);
		}
		case REPORT_ON_FINDINGS_OF_FIELD_SURVEYS: {
			IAReportOnFindingsOfFieldEntity reportOnFindingsOfField = auditObservationEn.getReportOnFindingsOfField();
			return getAuditObservationFromIAReportOnFindingsOfFieldEntity.apply(reportOnFindingsOfField, ia);
		}
		default:
			return null;
		}
	}

	@Override
	public IAAuditObservationPartALineItemDTO getAuditObservationPartALineItemById(Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservationEn = ia.getAuditObservation();
		if (auditObservationEn == null)
			return null;
		return fetchPerType(id, ia, partAType);
	}

	@Override
	public Boolean deleteAuditObservationLineItem(Long auditObservationId, Long id, IAPartAAuditObsTypeEnum partAType) {
		IAAuditObservationEntity auditObservationEn = auditObservationRepository.findById(auditObservationId)
				.orElseThrow(() -> new EntityNotFoundException());
		InternalAuditEntity ia = auditObservationEn.getInternalAudit();
		deletePerType(id, ia, partAType, auditObservationEn);
		return true;
	}

	@Override
	public String uploadFileForAuditObservationPartA(MultipartFile file, Long id, Long iaId,
			IAPartAAuditObsTypeEnum partAType) {
		IAAuditObservationEntity auditObservationEn = auditObservationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		InternalAuditEntity ia = auditObservationEn.getInternalAudit();
		return uploadPerType(id, ia, partAType, file);
	}

	@Override
	public Resource getFilePartA(Long id, IAPartAAuditObsTypeEnum partAType) {
		IAAuditObservationEntity auditObservationEn = auditObservationRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		InternalAuditEntity ia = auditObservationEn.getInternalAudit();
		try {
			return fetchFilePerType(id, ia, partAType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private IAAuditObservationPartALineItemDTO fetchPerType(Long id, InternalAuditEntity ia,
			IAPartAAuditObsTypeEnum type) {
		switch (type) {
		case PROPERTY_HOLDING_TAX: {
			IANonLevyOfPropertyHoldingTaxEntity propertyHoldingTax = nonLevyOfPropertyHoldingRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromPropertyTaxEntity.apply(propertyHoldingTax, ia);
		}
		case DELAY_IN_TAX: {
			IADelayDepositTaxCollectedEntity delayDepositTaxCollected = delayDepositTaxCollectedRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromIADelayDepositTaxCollectedEntity.apply(delayDepositTaxCollected, ia);
		}
		case MOBILE_TOWER_TAX: {
			IAMobileTowerTaxEntity mobileTowerTax = mobileTowerTaxRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromIAMobileTowerTaxEntity.apply(mobileTowerTax, ia);
		}
		case RENT_ON_MUNICIPAL_PROPERTIES: {
			IARentOnMunicipalPropertiesEntity rentOnMunicipalProperties = rentOnMunicipalPropertiesRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromIARentOnMunicipalPropertiesEntity.apply(rentOnMunicipalProperties, ia);
		}
		case ADVERTISEMENT_TAX: {
			IAAdvertisementTaxEntity advertisementTax = advertisementTaxRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromIAAdvertisementTaxEntity.apply(advertisementTax, ia);
		}
		case EXCESS_PAYMENT_AGAINST_BILL: {
			IAExcessPaymentAgainstBillEntity excessPaymentAgainstBill = excessPaymentAgainstBillRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromIAExcessPaymentAgainstBillEntity.apply(excessPaymentAgainstBill, ia);
		}
		case REPORT_ON_FINDINGS_OF_FIELD_SURVEYS: {
			IAReportOnFindingsOfFieldEntity reportOnFindingsOfField = reportOnFindingOfFieldRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromIAReportOnFindingsOfFieldEntity.apply(reportOnFindingsOfField, ia);
		}
		case OTHERS: {
			IACustomAuditObservationEntity auditCustomObservation = customAuditObservationRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return getAuditObservationFromIACustomAuditObservationEntity.apply(auditCustomObservation, ia);
		}
		}
		return null;
	}

	private IAAuditObservationPartALineItemDTO saveAsPerType(IAAuditObservationPartALineItemDTO audit,
			InternalAuditEntity ia, IAAuditObservationEntity auditObservationEn) {
		IAAuditObservationPartALineItemDTO dto = null;
		switch (audit.getPartAType()) {
		case PROPERTY_HOLDING_TAX: {
			IANonLevyOfPropertyHoldingTaxEntity propertyHoldingTax = getPropertyTaxEntityFromDTO(audit);
			propertyHoldingTax = nonLevyOfPropertyHoldingRepo.save(propertyHoldingTax);
			auditObservationEn.setPropertyHoldingTax(propertyHoldingTax);
			auditObservationRepository.save(auditObservationEn);
			return getAuditObservationFromPropertyTaxEntity.apply(propertyHoldingTax, ia);
		}
		case DELAY_IN_TAX: {
			IADelayDepositTaxCollectedEntity delayDepositTaxCollected = getIADelayDepositTaxCollectedEntityFromDTO(
					audit);
			delayDepositTaxCollected = delayDepositTaxCollectedRepo.save(delayDepositTaxCollected);
			auditObservationEn.setDelayDepositTaxCollected(delayDepositTaxCollected);
			auditObservationRepository.save(auditObservationEn);
			return getAuditObservationFromIADelayDepositTaxCollectedEntity.apply(delayDepositTaxCollected, ia);
		}
		case MOBILE_TOWER_TAX: {
			IAMobileTowerTaxEntity mobileTowerTax = getIAMobileTowerTaxEntityFromDTO(audit);
			mobileTowerTax = mobileTowerTaxRepo.save(mobileTowerTax);
			auditObservationEn.setMobileTowerTax(mobileTowerTax);
			auditObservationRepository.save(auditObservationEn);
			return getAuditObservationFromIAMobileTowerTaxEntity.apply(mobileTowerTax, ia);
		}
		case RENT_ON_MUNICIPAL_PROPERTIES: {
			IARentOnMunicipalPropertiesEntity rentOnMunicipalProperties = getIARentOnMunicipalPropertiesEntityFromDTO(
					audit);
			rentOnMunicipalProperties = rentOnMunicipalPropertiesRepo.save(rentOnMunicipalProperties);
			auditObservationEn.setRentOnMunicipalProperties(rentOnMunicipalProperties);
			auditObservationRepository.save(auditObservationEn);
			return getAuditObservationFromIARentOnMunicipalPropertiesEntity.apply(rentOnMunicipalProperties, ia);
		}
		case ADVERTISEMENT_TAX: {
			IAAdvertisementTaxEntity advertisementTax = getIAAdvertisementTaxEntityFromDTO(audit);
			advertisementTax = advertisementTaxRepo.save(advertisementTax);
			auditObservationEn.setAdvertisementTax(advertisementTax);
			auditObservationRepository.save(auditObservationEn);
			return getAuditObservationFromIAAdvertisementTaxEntity.apply(advertisementTax, ia);
		}
		case EXCESS_PAYMENT_AGAINST_BILL: {
			IAExcessPaymentAgainstBillEntity excessPaymentAgainstBill = getIAExcessPaymentAgainstBillEntityFromDTO(
					audit);
			excessPaymentAgainstBill = excessPaymentAgainstBillRepo.save(excessPaymentAgainstBill);
			auditObservationEn.setExcessPaymentAgainstBill(excessPaymentAgainstBill);
			auditObservationRepository.save(auditObservationEn);
			return getAuditObservationFromIAExcessPaymentAgainstBillEntity.apply(excessPaymentAgainstBill, ia);
		}
		case REPORT_ON_FINDINGS_OF_FIELD_SURVEYS: {
			IAReportOnFindingsOfFieldEntity reportOnFindingsOfField = getIAReportOnFindingsOfFieldEntityFromDTO(audit);
			reportOnFindingsOfField = reportOnFindingOfFieldRepo.save(reportOnFindingsOfField);
			auditObservationEn.setReportOnFindingsOfField(reportOnFindingsOfField);
			auditObservationRepository.save(auditObservationEn);
			return getAuditObservationFromIAReportOnFindingsOfFieldEntity.apply(reportOnFindingsOfField, ia);
		}
		case OTHERS: {
			IACustomAuditObservationEntity auditCustomObservation = getIACustomAuditObservationEntityFromDTO(audit);
			auditCustomObservation = customAuditObservationRepo.save(auditCustomObservation);
			List<IACustomAuditObservationEntity> auditObs= auditObservationEn.getCustomAuditObservation();
			if(auditObs==null) {
				auditObs = new ArrayList<>();
			}
			if (audit.getId() == null ) {
				auditObs.add(auditCustomObservation);
				auditObservationEn.setCustomAuditObservation(auditObs);
				auditObservationRepository.save(auditObservationEn);
			}
			return getAuditObservationFromIACustomAuditObservationEntity.apply(auditCustomObservation, ia);
		}
		}
		return null;
	}

	private String uploadPerType(Long id, InternalAuditEntity ia, IAPartAAuditObsTypeEnum type, MultipartFile file) {
		switch (type) {
		case PROPERTY_HOLDING_TAX: {
			IANonLevyOfPropertyHoldingTaxEntity propertyHoldingTax = nonLevyOfPropertyHoldingRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, propertyHoldingTax.getFile());
			propertyHoldingTax.setFile(fileEn);
			nonLevyOfPropertyHoldingRepo.save(propertyHoldingTax);
			return fileEn.getFileId();
		}
		case DELAY_IN_TAX: {
			IADelayDepositTaxCollectedEntity delayDepositTaxCollected = delayDepositTaxCollectedRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, delayDepositTaxCollected.getFile());
			delayDepositTaxCollected.setFile(fileEn);
			delayDepositTaxCollectedRepo.save(delayDepositTaxCollected);
			return fileEn.getFileId();
		}
		case MOBILE_TOWER_TAX: {
			IAMobileTowerTaxEntity mobileTowerTax = mobileTowerTaxRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, mobileTowerTax.getFile());
			mobileTowerTax.setFile(fileEn);
			mobileTowerTaxRepo.save(mobileTowerTax);
			return fileEn.getFileId();
		}
		case RENT_ON_MUNICIPAL_PROPERTIES: {
			IARentOnMunicipalPropertiesEntity rentOnMunicipalProperties = rentOnMunicipalPropertiesRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, rentOnMunicipalProperties.getFile());
			rentOnMunicipalProperties.setFile(fileEn);
			rentOnMunicipalPropertiesRepo.save(rentOnMunicipalProperties);
			return fileEn.getFileId();
		}
		case ADVERTISEMENT_TAX: {
			IAAdvertisementTaxEntity advertisementTax = advertisementTaxRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, advertisementTax.getFile());
			advertisementTax.setFile(fileEn);
			advertisementTaxRepo.save(advertisementTax);
			return fileEn.getFileId();
		}
		case EXCESS_PAYMENT_AGAINST_BILL: {
			IAExcessPaymentAgainstBillEntity excessPaymentAgainstBill = excessPaymentAgainstBillRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, excessPaymentAgainstBill.getFile());
			excessPaymentAgainstBill.setFile(fileEn);
			excessPaymentAgainstBillRepo.save(excessPaymentAgainstBill);
			return fileEn.getFileId();
		}
		case REPORT_ON_FINDINGS_OF_FIELD_SURVEYS: {
			IAReportOnFindingsOfFieldEntity reportOnFindingsOfField = reportOnFindingOfFieldRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, reportOnFindingsOfField.getFile());
			reportOnFindingsOfField.setFile(fileEn);
			reportOnFindingOfFieldRepo.save(reportOnFindingsOfField);
			return fileEn.getFileId();
		}
		case OTHERS: {
			IACustomAuditObservationEntity auditCustomObservation = customAuditObservationRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			FileEntity fileEn = uploadFile(file, auditCustomObservation.getFile());
			auditCustomObservation.setFile(fileEn);
			customAuditObservationRepo.save(auditCustomObservation);
			return fileEn.getFileId();
		}
		}
		return null;
	}

	private FileEntity uploadFile(MultipartFile fileM, FileEntity existingFile) {

		FileEntity file = handleFileSave(fileM);
		if (existingFile != null) {
			try {
				fileService.deleteFileById(existingFile.getFileId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return file;
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

	private Resource fetchFilePerType(Long id, InternalAuditEntity ia, IAPartAAuditObsTypeEnum type) throws Exception {
		switch (type) {
		case PROPERTY_HOLDING_TAX: {
			IANonLevyOfPropertyHoldingTaxEntity propertyHoldingTax = nonLevyOfPropertyHoldingRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService
					.getFile(propertyHoldingTax.getFile() != null ? propertyHoldingTax.getFile().getFileId() : null);
		}
		case DELAY_IN_TAX: {
			IADelayDepositTaxCollectedEntity delayDepositTaxCollected = delayDepositTaxCollectedRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService.getFile(
					delayDepositTaxCollected.getFile() != null ? delayDepositTaxCollected.getFile().getFileId() : null);
		}
		case MOBILE_TOWER_TAX: {
			IAMobileTowerTaxEntity mobileTowerTax = mobileTowerTaxRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService.getFile(mobileTowerTax.getFile() != null ? mobileTowerTax.getFile().getFileId() : null);
		}
		case RENT_ON_MUNICIPAL_PROPERTIES: {
			IARentOnMunicipalPropertiesEntity rentOnMunicipalProperties = rentOnMunicipalPropertiesRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService.getFile(
					rentOnMunicipalProperties.getFile() != null ? rentOnMunicipalProperties.getFile().getFileId()
							: null);
		}
		case ADVERTISEMENT_TAX: {
			IAAdvertisementTaxEntity advertisementTax = advertisementTaxRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService
					.getFile(advertisementTax.getFile() != null ? advertisementTax.getFile().getFileId() : null);
		}
		case EXCESS_PAYMENT_AGAINST_BILL: {
			IAExcessPaymentAgainstBillEntity excessPaymentAgainstBill = excessPaymentAgainstBillRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService.getFile(
					excessPaymentAgainstBill.getFile() != null ? excessPaymentAgainstBill.getFile().getFileId() : null);
		}
		case REPORT_ON_FINDINGS_OF_FIELD_SURVEYS: {
			IAReportOnFindingsOfFieldEntity reportOnFindingsOfField = reportOnFindingOfFieldRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService.getFile(
					reportOnFindingsOfField.getFile() != null ? reportOnFindingsOfField.getFile().getFileId() : null);
		}
		case OTHERS: {
			IACustomAuditObservationEntity auditCustomObservation = customAuditObservationRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
			return fileService.getFile(
					auditCustomObservation.getFile() != null ? auditCustomObservation.getFile().getFileId() : null);
		}
		}
		return null;
	}

	private void deletePerType(Long id, InternalAuditEntity ia, IAPartAAuditObsTypeEnum type,
			IAAuditObservationEntity auditObservationEn) {
		switch (type) {
		case PROPERTY_HOLDING_TAX: {
			auditObservationEn.setPropertyHoldingTax(null);
			auditObservationRepository.save(auditObservationEn);
			nonLevyOfPropertyHoldingRepo.deleteById(id);
			break;
		}
		case DELAY_IN_TAX: {
			auditObservationEn.setDelayDepositTaxCollected(null);
			auditObservationRepository.save(auditObservationEn);
			delayDepositTaxCollectedRepo.deleteById(id);
			break;
		}
		case MOBILE_TOWER_TAX: {
			auditObservationEn.setMobileTowerTax(null);
			auditObservationRepository.save(auditObservationEn);
			mobileTowerTaxRepo.deleteById(id);
			break;
		}
		case RENT_ON_MUNICIPAL_PROPERTIES: {
			auditObservationEn.setRentOnMunicipalProperties(null);
			auditObservationRepository.save(auditObservationEn);
			rentOnMunicipalPropertiesRepo.deleteById(id);
			break;
		}
		case ADVERTISEMENT_TAX: {
			auditObservationEn.setAdvertisementTax(null);
			auditObservationRepository.save(auditObservationEn);
			advertisementTaxRepo.deleteById(id);
			break;
		}
		case EXCESS_PAYMENT_AGAINST_BILL: {
			auditObservationEn.setExcessPaymentAgainstBill(null);
			auditObservationRepository.save(auditObservationEn);
			excessPaymentAgainstBillRepo.deleteById(id);
			break;
		}
		case REPORT_ON_FINDINGS_OF_FIELD_SURVEYS: {
			auditObservationEn.setReportOnFindingsOfField(null);
			auditObservationRepository.save(auditObservationEn);
			reportOnFindingOfFieldRepo.deleteById(id);
			break;
		}
		case OTHERS: {
			List<IACustomAuditObservationEntity> customs = auditObservationEn.getCustomAuditObservation();
			customs.remove(customAuditObservationRepo.findById(id).orElseThrow(EntityNotFoundException::new));
			auditObservationEn.setCustomAuditObservation(customs);
			auditObservationRepository.save(auditObservationEn);
			customAuditObservationRepo.deleteById(id);
			break;
		}
		}
	}

	private IANonLevyOfPropertyHoldingTaxEntity getPropertyTaxEntityFromDTO(IAAuditObservationPartALineItemDTO dto) {
		return IANonLevyOfPropertyHoldingTaxEntity.builder().cause(dto.getCause()).id(dto.getId())
				.condition(dto.getCondition()).consequences(dto.getConsequences())
				.correctiveAction(dto.getCorrectiveAction()).criteria(dto.getCriteria()).objective(dto.getObjective())
				.build();
	}

	private BiFunction<IANonLevyOfPropertyHoldingTaxEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromPropertyTaxEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.partAType(IAPartAAuditObsTypeEnum.PROPERTY_HOLDING_TAX).build();

	private IADelayDepositTaxCollectedEntity getIADelayDepositTaxCollectedEntityFromDTO(
			IAAuditObservationPartALineItemDTO dto) {
		return IADelayDepositTaxCollectedEntity.builder().cause(dto.getCause()).id(dto.getId())
				.condition(dto.getCondition()).consequences(dto.getConsequences())
				.correctiveAction(dto.getCorrectiveAction()).criteria(dto.getCriteria()).objective(dto.getObjective())
				.build();
	}

	private BiFunction<IADelayDepositTaxCollectedEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromIADelayDepositTaxCollectedEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.partAType(IAPartAAuditObsTypeEnum.DELAY_IN_TAX).build();

	private IAMobileTowerTaxEntity getIAMobileTowerTaxEntityFromDTO(IAAuditObservationPartALineItemDTO dto) {
		return IAMobileTowerTaxEntity.builder().cause(dto.getCause()).id(dto.getId()).condition(dto.getCondition())
				.consequences(dto.getConsequences()).correctiveAction(dto.getCorrectiveAction())
				.criteria(dto.getCriteria()).objective(dto.getObjective()).build();
	}

	private BiFunction<IAMobileTowerTaxEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromIAMobileTowerTaxEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.partAType(IAPartAAuditObsTypeEnum.MOBILE_TOWER_TAX).build();

	private IARentOnMunicipalPropertiesEntity getIARentOnMunicipalPropertiesEntityFromDTO(
			IAAuditObservationPartALineItemDTO dto) {
		return IARentOnMunicipalPropertiesEntity.builder().cause(dto.getCause()).id(dto.getId())
				.condition(dto.getCondition()).consequences(dto.getConsequences())
				.correctiveAction(dto.getCorrectiveAction()).criteria(dto.getCriteria()).objective(dto.getObjective())
				.build();
	}

	private BiFunction<IARentOnMunicipalPropertiesEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromIARentOnMunicipalPropertiesEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.partAType(IAPartAAuditObsTypeEnum.RENT_ON_MUNICIPAL_PROPERTIES).build();

	private IAAdvertisementTaxEntity getIAAdvertisementTaxEntityFromDTO(IAAuditObservationPartALineItemDTO dto) {
		return IAAdvertisementTaxEntity.builder().cause(dto.getCause()).id(dto.getId()).condition(dto.getCondition())
				.consequences(dto.getConsequences()).correctiveAction(dto.getCorrectiveAction())
				.criteria(dto.getCriteria()).objective(dto.getObjective()).build();
	}

	private BiFunction<IAAdvertisementTaxEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromIAAdvertisementTaxEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.partAType(IAPartAAuditObsTypeEnum.ADVERTISEMENT_TAX).build();

	private IAExcessPaymentAgainstBillEntity getIAExcessPaymentAgainstBillEntityFromDTO(
			IAAuditObservationPartALineItemDTO dto) {
		return IAExcessPaymentAgainstBillEntity.builder().cause(dto.getCause()).id(dto.getId())
				.condition(dto.getCondition()).consequences(dto.getConsequences())
				.correctiveAction(dto.getCorrectiveAction()).criteria(dto.getCriteria()).objective(dto.getObjective())
				.build();
	}

	private BiFunction<IAExcessPaymentAgainstBillEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromIAExcessPaymentAgainstBillEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.partAType(IAPartAAuditObsTypeEnum.EXCESS_PAYMENT_AGAINST_BILL).build();

	private IAReportOnFindingsOfFieldEntity getIAReportOnFindingsOfFieldEntityFromDTO(
			IAAuditObservationPartALineItemDTO dto) {
		return IAReportOnFindingsOfFieldEntity.builder().cause(dto.getCause()).id(dto.getId())
				.condition(dto.getCondition()).consequences(dto.getConsequences())
				.correctiveAction(dto.getCorrectiveAction()).criteria(dto.getCriteria()).objective(dto.getObjective())
				.build();
	}

	private BiFunction<IAReportOnFindingsOfFieldEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromIAReportOnFindingsOfFieldEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.partAType(IAPartAAuditObsTypeEnum.REPORT_ON_FINDINGS_OF_FIELD_SURVEYS).build();

	private IACustomAuditObservationEntity getIACustomAuditObservationEntityFromDTO(
			IAAuditObservationPartALineItemDTO dto) {
		return IACustomAuditObservationEntity.builder().cause(dto.getCause()).id(dto.getId())
				.condition(dto.getCondition()).consequences(dto.getConsequences())
				.correctiveAction(dto.getCorrectiveAction()).criteria(dto.getCriteria()).objective(dto.getObjective())
				.build();
	}

	private BiFunction<IACustomAuditObservationEntity, InternalAuditEntity, IAAuditObservationPartALineItemDTO> getAuditObservationFromIACustomAuditObservationEntity = (
			en, ia) -> IAAuditObservationPartALineItemDTO.builder().iaId(ia.getId()).cause(en.getCause()).id(en.getId())
					.condition(en.getCondition()).consequences(en.getConsequences())
					.fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.correctiveAction(en.getCorrectiveAction()).criteria(en.getCriteria()).objective(en.getObjective())
					.partAType(IAPartAAuditObsTypeEnum.OTHERS).build();

}
