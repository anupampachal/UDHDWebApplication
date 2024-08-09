package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditObjectsWithMonetaryIrregularitiesEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditObservationEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditObservationPartBEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceTDSVATCustomEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObjectsWithMonetaryIrregularitiesRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationPartBRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAAuditObjectsWithMonetaryIrregularitiesDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAUtilisationOfGrantsLineItemDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.PartByTypeAuditObsEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAAuditObservationPartBService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAuditObservationPartBFNonComplianceOfVATnTDSService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAuditObservationPartBHUtilizationOfGrantReportService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

import io.vavr.Function3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IAAuditObservationPartBService implements IIAAuditObservationPartBService {

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String internalAuditFolderNickName;
	private final IInternalAuditService internalAuditService;
	private final IFileService fileService;
	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IIAAuditObservationRepository auditObservationRepository;
	private final IIAAuditObservationPartBRepository auditObservationPartBRepository;
	private final IIAAuditObjectsWithMonetaryIrregularitiesRepository monetaryIrregularityRepository;
	private final IIAuditObservationPartBFNonComplianceOfVATnTDSService tdsAndVatService;
	private final IIAuditObservationPartBHUtilizationOfGrantReportService partBHUtilisationService;
	@OneToMany
	private List<IANonComplianceTDSVATCustomEntity> nonComplianceTDSVATCustom;

	@Override
	public IAAuditObjectsWithMonetaryIrregularitiesDTO createUpdateAuditObservationPartB(
			IAAuditObjectsWithMonetaryIrregularitiesDTO irregularitiesDTO) {
		// IAAuditObservationPartBEntity
		InternalAuditEntity internalAudit = internalAuditService
				.findInternalAuditEntityById(irregularitiesDTO.getIaId());
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();

		if (auditObservation == null) {
			auditObservation = auditObservationRepository
					.save(IAAuditObservationEntity.builder().internalAudit(internalAudit).build());
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();

		if (partB == null) {
			partB = auditObservationPartBRepository
					.save(IAAuditObservationPartBEntity.builder().auditObservation(auditObservation).build());
		}
		auditObservation.setPartB(partB);
		auditObservation = auditObservationRepository.save(auditObservation);
		return createUpdateAuditObservationGeneralPartB(internalAudit, auditObservation, partB, irregularitiesDTO);

	}

	private IAAuditObjectsWithMonetaryIrregularitiesDTO createUpdateAuditObservationGeneralPartB(
			InternalAuditEntity internalAudit, IAAuditObservationEntity auditObservation,
			IAAuditObservationPartBEntity partB, IAAuditObjectsWithMonetaryIrregularitiesDTO irregularitiesDTO) {
		switch (irregularitiesDTO.getType()) {
		case NON_MAINTENANCE_OF_BOOKS: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> aNonMaintenanceOfBooksOfAccounts = partB
					.getANonMaintenanceOfBooksOfAccounts();
			if (aNonMaintenanceOfBooksOfAccounts == null) {
				aNonMaintenanceOfBooksOfAccounts = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				aNonMaintenanceOfBooksOfAccounts.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.NON_MAINTENANCE_OF_BOOKS);
			return dto;
		}
		case IRREGULARITIES_IN_PROCUREMENT: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> bIrregularatiesInProcurementProcess = partB
					.getBIrregularatiesInProcurementProcess();
			if (bIrregularatiesInProcurementProcess == null) {
				bIrregularatiesInProcurementProcess = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				bIrregularatiesInProcurementProcess.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.IRREGULARITIES_IN_PROCUREMENT);
			return dto;

		}
		case NON_COMPLIANCE_OF_DIRECTIVES: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> cNonComplianceOfDirectivesOfUDHD = partB
					.getCNonComplianceOfDirectivesOfUDHD();
			if (cNonComplianceOfDirectivesOfUDHD == null) {
				cNonComplianceOfDirectivesOfUDHD = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				cNonComplianceOfDirectivesOfUDHD.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.NON_COMPLIANCE_OF_DIRECTIVES);
			return dto;

		}
		case NON_COMPLIANCE_OF_ACT_N_RULES: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> dNonComplianceOfActsNRulesOfUDHD = partB
					.getDNonComplianceOfActsNRulesOfUDHD();
			if (dNonComplianceOfActsNRulesOfUDHD==null ) {
				dNonComplianceOfActsNRulesOfUDHD = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				dNonComplianceOfActsNRulesOfUDHD.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.NON_COMPLIANCE_OF_ACT_N_RULES);
			return dto;

		}
		case LACK_OF_INTERNAL_CONTROL_MEASURES: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> eLackOfInternalControlMeasures = partB
					.getELackOfInternalControlMeasures();
			if (eLackOfInternalControlMeasures == null) {
				eLackOfInternalControlMeasures = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				eLackOfInternalControlMeasures.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.LACK_OF_INTERNAL_CONTROL_MEASURES);
			return dto;

		}
		case DEFICIENCY_IN_PAYROLL_SYSTEM: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> gDeficiencyInPayrollSystem = partB
					.getGDeficiencyInPayrollSystem();
			if (gDeficiencyInPayrollSystem == null) {
				gDeficiencyInPayrollSystem = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				gDeficiencyInPayrollSystem.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.DEFICIENCY_IN_PAYROLL_SYSTEM);
			return dto;

		}
		case PHYSICAL_VERIFICATION_OF_INVENTORY: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> iPhysicalVerificationOfStores = partB
					.getIPhysicalVerificationOfStores();
			if (iPhysicalVerificationOfStores == null) {
				iPhysicalVerificationOfStores = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				iPhysicalVerificationOfStores.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.PHYSICAL_VERIFICATION_OF_INVENTORY);
			return dto;

		}
		case ADVANCES_N_THEIR_ADJUSTMENT: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> jAdvancesAndAdjustment = partB
					.getJAdvancesAndAdjustment();
			if (jAdvancesAndAdjustment == null) {
				jAdvancesAndAdjustment = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null) {
				jAdvancesAndAdjustment.add(en);
			}
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.ADVANCES_N_THEIR_ADJUSTMENT);
			return dto;
		}
		case ANY_OTHER_MATTER_IN_DUE_COURSE: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> kAnyOtherMatter = partB
					.getKAnyOtherMatter();
			if (kAnyOtherMatter == null) {
				kAnyOtherMatter = new ArrayList<>();
			}
			IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository
					.save(getMonetaryEntityFromDTO.apply(irregularitiesDTO));

			if (irregularitiesDTO.getId() == null)
				kAnyOtherMatter.add(en);
			auditObservationPartBRepository.save(partB);
			IAAuditObjectsWithMonetaryIrregularitiesDTO dto = getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.ANY_OTHER_MATTER_IN_DUE_COURSE);
			return dto;
		}
		}
		return null;
	}

	@Override
	public List<IAAuditObjectsWithMonetaryIrregularitiesDTO> getAllIrregularitiesForPartB(Long iaId,
			PartByTypeAuditObsEnum type) {
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = internalAudit.getAuditObservation();

		if (auditObservation == null) {
			return null;
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();

		if (partB == null) {
			return null;
		}
		return fetchListOfMonetaryIrregularitiesGeneralPartB(internalAudit, auditObservation, partB, type);
	}

	private List<IAAuditObjectsWithMonetaryIrregularitiesDTO> fetchListOfMonetaryIrregularitiesGeneralPartB(
			InternalAuditEntity internalAudit, IAAuditObservationEntity auditObservation,
			IAAuditObservationPartBEntity partB, PartByTypeAuditObsEnum type) {
		switch (type) {
		case NON_MAINTENANCE_OF_BOOKS: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> aNonMaintenanceOfBooksOfAccounts = partB
					.getANonMaintenanceOfBooksOfAccounts();
			return aNonMaintenanceOfBooksOfAccounts.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.NON_MAINTENANCE_OF_BOOKS)).collect(Collectors.toList());
		}
		case IRREGULARITIES_IN_PROCUREMENT: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> bIrregularatiesInProcurementProcess = partB
					.getBIrregularatiesInProcurementProcess();
			return bIrregularatiesInProcurementProcess.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.IRREGULARITIES_IN_PROCUREMENT)).collect(Collectors.toList());

		}
		case NON_COMPLIANCE_OF_DIRECTIVES: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> cNonComplianceOfDirectivesOfUDHD = partB
					.getCNonComplianceOfDirectivesOfUDHD();
			return cNonComplianceOfDirectivesOfUDHD.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.NON_COMPLIANCE_OF_DIRECTIVES)).collect(Collectors.toList());

		}
		case NON_COMPLIANCE_OF_ACT_N_RULES: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> dNonComplianceOfActsNRulesOfUDHD = partB
					.getDNonComplianceOfActsNRulesOfUDHD();
			return dNonComplianceOfActsNRulesOfUDHD.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.NON_COMPLIANCE_OF_ACT_N_RULES)).collect(Collectors.toList());

		}
		case LACK_OF_INTERNAL_CONTROL_MEASURES: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> eLackOfInternalControlMeasures = partB
					.getELackOfInternalControlMeasures();
			return eLackOfInternalControlMeasures.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.LACK_OF_INTERNAL_CONTROL_MEASURES)).collect(Collectors.toList());

		}
		case DEFICIENCY_IN_PAYROLL_SYSTEM: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> gDeficiencyInPayrollSystem = partB
					.getGDeficiencyInPayrollSystem();
			return gDeficiencyInPayrollSystem.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.DEFICIENCY_IN_PAYROLL_SYSTEM)).collect(Collectors.toList());

		}
		case PHYSICAL_VERIFICATION_OF_INVENTORY: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> iPhysicalVerificationOfStores = partB
					.getIPhysicalVerificationOfStores();
			return iPhysicalVerificationOfStores.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.PHYSICAL_VERIFICATION_OF_INVENTORY)).collect(Collectors.toList());

		}
		case ADVANCES_N_THEIR_ADJUSTMENT: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> jAdvancesAndAdjustment = partB
					.getJAdvancesAndAdjustment();
			return jAdvancesAndAdjustment.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.ADVANCES_N_THEIR_ADJUSTMENT)).collect(Collectors.toList());
		}
		case ANY_OTHER_MATTER_IN_DUE_COURSE: {
			List<IAAuditObjectsWithMonetaryIrregularitiesEntity> kAnyOtherMatter = partB.getKAnyOtherMatter();
			return kAnyOtherMatter.stream().map(en -> getMonetaryDTOFromEn.apply(en, internalAudit,
					PartByTypeAuditObsEnum.ANY_OTHER_MATTER_IN_DUE_COURSE)).collect(Collectors.toList());
		}
		}
		return null;
	}

	private Function<IAAuditObjectsWithMonetaryIrregularitiesDTO, IAAuditObjectsWithMonetaryIrregularitiesEntity> getMonetaryEntityFromDTO = dto -> IAAuditObjectsWithMonetaryIrregularitiesEntity
			.builder().id(dto.getId()).description(dto.getDescription()).build();

	private Function3<IAAuditObjectsWithMonetaryIrregularitiesEntity, InternalAuditEntity, PartByTypeAuditObsEnum, IAAuditObjectsWithMonetaryIrregularitiesDTO> getMonetaryDTOFromEn = (
			en, ia, type) -> IAAuditObjectsWithMonetaryIrregularitiesDTO.builder().id(en.getId()).type(type)
					.description(en.getDescription()).fileId(en.getFile() != null ? en.getFile().getFileId() : null)
					.iaId(ia != null ? ia.getId() : null).build();

	@Override
	public IAAuditObjectsWithMonetaryIrregularitiesDTO getAuditObsPartBById(Long id) {
		IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		return getMonetaryDTOFromEn.apply(en, null, null);
	}

	@Override
	public Boolean deletePartBAuditObservation(Long id, PartByTypeAuditObsEnum type) {
		// getAllIrregularitiesForPartB(iaId,type)
		monetaryIrregularityRepository.deleteById(id);
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

	@Override
	public String uploadFileForPartBAuditObservation(MultipartFile file, Long id, Long iaId) {
		IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		if (!verifyData(ia)) {
			return null;
		}
		FileEntity fileN = uploadFile(file, en.getFile() != null ? en.getFile() : null);
		en.setFile(fileN);

		en = monetaryIrregularityRepository.save(en);
		return fileN.getFileId();
	}

	@Override
	public Resource getFilePartB(Long id) throws Exception {
		IAAuditObjectsWithMonetaryIrregularitiesEntity en = monetaryIrregularityRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		FileEntity fileEn = en.getFile();
		return fileService.getFile(fileEn.getFileId());
	}

	@Override
	public IANonComplianceOfTDSNVATDTO createUpdateTDSVATStatute(IANonComplianceOfTDSNVATDTO compliance) {
		return tdsAndVatService.createUpdateTDSVATStatute(compliance);
	}

	@Override
	public IANonComplianceOfTDSNVATDTO getTDSVATStatuteOtherById(Long id, Long iaId,
			IANonComplianceOfTDSNVATEnum type) {
		return tdsAndVatService.getTDSVATStatuteOtherById(id, iaId, type);
	}

	@Override
	public List<IANonComplianceOfTDSNVATDTO> getAllTDSVATStatuteOfTypeOfTypeOthers(Long iaId) {
		return tdsAndVatService.getAllTDSVATStatuteOfTypeOfTypeOthers(iaId);
	}

	@Override
	public IANonComplianceOfTDSNVATDTO getTDSVATStatuteByIaId(Long iaId, IANonComplianceOfTDSNVATEnum type) {
		return tdsAndVatService.getTDSVATStatuteByIaId(iaId, type);
	}

	@Override
	public Boolean deletePartBTDSVATStatute(Long iaId, IANonComplianceOfTDSNVATEnum type) {
		return tdsAndVatService.deletePartBTDSVATStatute(iaId, type);
	}

	@Override
	public Boolean deletePartBTDSVATStatuteOthers(Long id, Long iaId) {
		return tdsAndVatService.deletePartBTDSVATStatuteOthers(id, iaId);
	}

	@Override
	public String uploadPartBTDSVATStatute(MultipartFile file, Long id, Long iaId, IANonComplianceOfTDSNVATEnum type) {
		return tdsAndVatService.uploadPartBTDSVATStatute(file, id, iaId, type);
	}

	@Override
	public Resource getTDSVATPartB(String id) {
		return tdsAndVatService.getTDSVATPartB(id);
	}

	@Override
	public IAUtilisationOfGrantsLineItemDTO createUpdateUtilisationOfGrantLineItem(
			IAUtilisationOfGrantsLineItemDTO lineItemDTO) {
		return partBHUtilisationService.createUpdateUtilisationOfGrantLineItem(lineItemDTO);
	}

	@Override
	public IAUtilisationOfGrantsLineItemDTO getLineItemById(Long id, Long iaId) {
		return partBHUtilisationService.getLineItemById(id,iaId);
	}

	@Override
	public List<IAUtilisationOfGrantsLineItemDTO> getAllLineItemsByIaId(Long iaId) {
		return partBHUtilisationService.getAllLineItemsByIaId(iaId);
	}

	@Override
	public Boolean deleteLineItem(Long id, Long iaId) {
		return partBHUtilisationService.deleteLineItem(id,iaId);
	}

	@Override
	public String uploadFileUtilizationOfGrantLine(MultipartFile file, Long iaId) {
		return partBHUtilisationService.uploadFileUtilizationOfGrantLine(file, iaId);
	}

	@Override
	public Resource getUtilizationOfGrantLineItemFile(Long id) {
		return partBHUtilisationService.getUtilizationOfGrantLineItemFile(id);
	}

}
