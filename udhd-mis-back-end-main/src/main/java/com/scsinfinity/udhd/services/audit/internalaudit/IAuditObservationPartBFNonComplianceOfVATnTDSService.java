package com.scsinfinity.udhd.services.audit.internalaudit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
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
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceLabourCessEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceOfTDSVATNOtherRelevantStatuteEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceRoyaltyFeeEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceTDSEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceTDSOnGSTEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceTDSVATCustomEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IANonComplianceVATGSTEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationPartBRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIAAuditObservationRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonComplianceLabourCessRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonComplianceOfTDSVATNOtherRelevantStatuteRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonComplianceRoyaltyFeeRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonComplianceTDSOnGSTRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonComplianceTDSRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonComplianceTDSVATCustomRepository;
import com.scsinfinity.udhd.dao.repositories.audit.internalaudit.IIANonComplianceVATGSTRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATDTO;
import com.scsinfinity.udhd.services.audit.internalaudit.dto.IANonComplianceOfTDSNVATEnum;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IIAuditObservationPartBFNonComplianceOfVATnTDSService;
import com.scsinfinity.udhd.services.audit.internalaudit.interfaces.IInternalAuditService;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IAuditObservationPartBFNonComplianceOfVATnTDSService
		implements IIAuditObservationPartBFNonComplianceOfVATnTDSService {

	@Value("${scs.setting.folder.ia-data.nickname}")
	private String internalAuditFolderNickName;
	private final IInternalAuditService internalAuditService;
	private final IFileService fileService;
	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IIAAuditObservationRepository auditObservationRepository;
	private final IIAAuditObservationPartBRepository auditObservationPartBRepository;
	private final IIANonComplianceOfTDSVATNOtherRelevantStatuteRepository vatAndOtherRelevantRepository;
	private final IIANonComplianceTDSRepository nonComplianceTDSRepository;
	private final IIANonComplianceVATGSTRepository nonComplianceVATGSTRepository;
	private final IIANonComplianceRoyaltyFeeRepository nonComplianceRoyaltyFeeRepository;
	private final IIANonComplianceLabourCessRepository nonComplianceLaboutCessRepository;
	private final IIANonComplianceTDSVATCustomRepository nonComplianceTDSVATCustomRepository;
	private final IIANonComplianceTDSOnGSTRepository nonComplianceTDSOnGSTRepository;

	@Override
	public IANonComplianceOfTDSNVATDTO createUpdateTDSVATStatute(IANonComplianceOfTDSNVATDTO compliance) {
		log.debug("createUpdateTDSVATStatute");
		InternalAuditEntity internalAudit = internalAuditService.findInternalAuditEntityById(compliance.getIaId());
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
			auditObservation = auditObservationRepository.save(auditObservation);
		}

		IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn = partB.getFNonComplianceOfTDSVat();

		if (tdsVatEtcEn == null) {
			tdsVatEtcEn = vatAndOtherRelevantRepository
					.save(IANonComplianceOfTDSVATNOtherRelevantStatuteEntity.builder().build());
		}

		partB.setFNonComplianceOfTDSVat(tdsVatEtcEn);
		auditObservationPartBRepository.save(partB);
		return createUpdateTDSVATStatuteByType(internalAudit, auditObservation, partB, tdsVatEtcEn, compliance);
	}

	@Override
	public IANonComplianceOfTDSNVATDTO getTDSVATStatuteOtherById(Long id, Long iaId,
			IANonComplianceOfTDSNVATEnum type) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = ia.getAuditObservation();

		if (auditObservation == null) {
			return null;
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();
		if (partB == null) {
			return null;
		}

		IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn = partB.getFNonComplianceOfTDSVat();

		if (tdsVatEtcEn == null) {
			return null;
		}

		List<IANonComplianceTDSVATCustomEntity> noncomplianceTDSEntityList = tdsVatEtcEn.getNonComplianceTDSVATCustom();
		Optional<IANonComplianceTDSVATCustomEntity> noncomplianceTDSEntityO = noncomplianceTDSEntityList.stream()
				.filter(en -> en.getId() == id).collect(Collectors.reducing((a, b) -> null));
		if (!noncomplianceTDSEntityO.isPresent())
			return null;
		return getENCustomDTO(noncomplianceTDSEntityO.get(), ia);
	}

	@Override
	public List<IANonComplianceOfTDSNVATDTO> getAllTDSVATStatuteOfTypeOfTypeOthers(Long iaId) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = ia.getAuditObservation();

		if (auditObservation == null) {
			return null;
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();
		if (partB == null) {
			return null;
		}

		IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn = partB.getFNonComplianceOfTDSVat();

		if (tdsVatEtcEn == null) {
			return null;
		}

		List<IANonComplianceTDSVATCustomEntity> noncomplianceTDSEntityList = tdsVatEtcEn.getNonComplianceTDSVATCustom();
		return noncomplianceTDSEntityList.stream().map(en -> getENCustomDTO(en, ia)).collect(Collectors.toList());
	}

	@Override
	public String uploadPartBTDSVATStatute(MultipartFile file, Long id, Long iaId, IANonComplianceOfTDSNVATEnum type) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = ia.getAuditObservation();

		if (auditObservation == null) {
			return null;
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();
		if (partB == null) {
			return null;
		}

		IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn = partB.getFNonComplianceOfTDSVat();

		if (tdsVatEtcEn == null) {
			return null;
		}

		if (!verifyData(ia))
			return null;

		return saveFileForTypeEntity(ia, file, id, tdsVatEtcEn, type);

	}

	private String saveFileForTypeEntity(InternalAuditEntity internalAudit, MultipartFile file, Long id,
			IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn, IANonComplianceOfTDSNVATEnum type) {
		FileEntity fileE = null;
		switch (type) {
		case TDS: {
			IANonComplianceTDSEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceTDS();
			if (noncomplianceTDSEntity == null)
				return null;
			fileE = uploadFile(file, noncomplianceTDSEntity.getFile());
			noncomplianceTDSEntity.setFile(fileE);
			nonComplianceTDSRepository.save(noncomplianceTDSEntity);
			return fileE.getFileId();
		}
		case VAT_GST: {
			IANonComplianceVATGSTEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceVATGST();
			if (noncomplianceTDSEntity == null)
				return null;
			fileE = uploadFile(file, noncomplianceTDSEntity.getFile());
			noncomplianceTDSEntity.setFile(fileE);
			nonComplianceVATGSTRepository.save(noncomplianceTDSEntity);
			return fileE.getFileId();
		}
		case ROYALTY: {
			IANonComplianceRoyaltyFeeEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceRoyaltyFee();
			if (noncomplianceTDSEntity == null)
				return null;
			fileE = uploadFile(file, noncomplianceTDSEntity.getFile());
			noncomplianceTDSEntity.setFile(fileE);
			nonComplianceRoyaltyFeeRepository.save(noncomplianceTDSEntity);
			return fileE.getFileId();

		}
		case TGS_ON_GST: {
			IANonComplianceTDSOnGSTEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceTDSOnGST();
			if (noncomplianceTDSEntity == null)
				return null;
			fileE = uploadFile(file, noncomplianceTDSEntity.getFile());
			noncomplianceTDSEntity.setFile(fileE);
			nonComplianceTDSOnGSTRepository.save(noncomplianceTDSEntity);
			return fileE.getFileId();
		}
		case LABOUR_CESS: {
			IANonComplianceLabourCessEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceLaboutCess();
			if (noncomplianceTDSEntity == null)
				return null;
			fileE = uploadFile(file, noncomplianceTDSEntity.getFile());
			noncomplianceTDSEntity.setFile(fileE);
			nonComplianceLaboutCessRepository.save(noncomplianceTDSEntity);
			return fileE.getFileId();
		}
		case OTHERS: {
			List<IANonComplianceTDSVATCustomEntity> noncomplianceTDSEntityList = tdsVatEtcEn
					.getNonComplianceTDSVATCustom();
			if (noncomplianceTDSEntityList == null)
				return null;
			Optional<IANonComplianceTDSVATCustomEntity> noncomplianceTDSEntityO = noncomplianceTDSEntityList.stream()
					.filter(en -> en.getId() == id).collect(Collectors.reducing((a, b) -> null));
			if (!noncomplianceTDSEntityO.isPresent())
				return null;
			IANonComplianceTDSVATCustomEntity noncomplianceTDSEntity = noncomplianceTDSEntityO.get();
			fileE = uploadFile(file, noncomplianceTDSEntity.getFile());
			noncomplianceTDSEntity.setFile(fileE);
			nonComplianceTDSVATCustomRepository.save(noncomplianceTDSEntity);
			return fileE.getFileId();
		}
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

	@Override
	public Resource getTDSVATPartB(String id) {
		try {
			return fileService.getFile(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private IANonComplianceOfTDSNVATDTO createUpdateTDSVATStatuteByType(InternalAuditEntity internalAudit,
			IAAuditObservationEntity auditObservation, IAAuditObservationPartBEntity partB,
			IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn, IANonComplianceOfTDSNVATDTO compliance) {
		switch (compliance.getType()) {
		case TDS: {
			if (compliance.getId() == null && tdsVatEtcEn.getNonComplianceTDS() != null) {
				throw new BadRequestAlertException("Entity exists", "Entity exists", "Entity exists");
			}
			IANonComplianceTDSEntity noncomplianceTDSEntity = nonComplianceTDSRepository
					.save(getENComplianceTDSEntity(compliance));
			tdsVatEtcEn.setNonComplianceTDS(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return getDTOComplianceTDSDTO(noncomplianceTDSEntity, internalAudit);
		}
		case VAT_GST: {
			if (compliance.getId() == null && tdsVatEtcEn.getNonComplianceVATGST() != null) {
				throw new BadRequestAlertException("Entity exists", "Entity exists", "Entity exists");
			}
			IANonComplianceVATGSTEntity noncomplianceTDSEntity = nonComplianceVATGSTRepository
					.save(getENNonComplianceVATGSTEntity(compliance));
			tdsVatEtcEn.setNonComplianceVATGST(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return getENNonComplianceVATGSTDTO(noncomplianceTDSEntity, internalAudit);
		}
		case ROYALTY: {
			if (compliance.getId() == null && tdsVatEtcEn.getNonComplianceRoyaltyFee() != null) {
				throw new BadRequestAlertException("Entity exists", "Entity exists", "Entity exists");
			}
			IANonComplianceRoyaltyFeeEntity noncomplianceTDSEntity = nonComplianceRoyaltyFeeRepository
					.save(getENIANonComplianceRoyaltyFeeEntity(compliance));
			tdsVatEtcEn.setNonComplianceRoyaltyFee(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return getENRoyaltyFeeDTO(noncomplianceTDSEntity, internalAudit);
		}
		case TGS_ON_GST: {
			if (compliance.getId() == null && tdsVatEtcEn.getNonComplianceTDSOnGST() != null) {
				throw new BadRequestAlertException("Entity exists", "Entity exists", "Entity exists");
			}
			IANonComplianceTDSOnGSTEntity noncomplianceTDSEntity = nonComplianceTDSOnGSTRepository
					.save(getENIANonComplianceTDSOnGSTEntity(compliance));
			tdsVatEtcEn.setNonComplianceTDSOnGST(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return getENTDSONGSTDTO(noncomplianceTDSEntity, internalAudit);
		}
		case LABOUR_CESS: {
			if (compliance.getId() == null && tdsVatEtcEn.getNonComplianceLaboutCess() != null) {
				throw new BadRequestAlertException("Entity exists", "Entity exists", "Entity exists");
			}
			IANonComplianceLabourCessEntity noncomplianceTDSEntity = nonComplianceLaboutCessRepository
					.save(getENIANonComplianceLabourCessEntity(compliance));
			tdsVatEtcEn.setNonComplianceLaboutCess(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return getENLabourCessDTO(noncomplianceTDSEntity, internalAudit);
		}
		case OTHERS: {
			List<IANonComplianceTDSVATCustomEntity> enList = tdsVatEtcEn.getNonComplianceTDSVATCustom();
			if (enList == null) {
				enList = new ArrayList<IANonComplianceTDSVATCustomEntity>();
			}
			IANonComplianceTDSVATCustomEntity noncomplianceTDSEntity = nonComplianceTDSVATCustomRepository
					.save(getENIANonComplianceTDSVATCustomEntity(compliance));
			if (compliance.getId() == null) {
				enList.add(noncomplianceTDSEntity);
				tdsVatEtcEn.setNonComplianceTDSVATCustom(enList);
			}
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return getENCustomDTO(noncomplianceTDSEntity, internalAudit);
		}
		}
		return null;
	}

	@Override
	public IANonComplianceOfTDSNVATDTO getTDSVATStatuteByIaId(Long iaId, IANonComplianceOfTDSNVATEnum type) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = ia.getAuditObservation();

		if (auditObservation == null) {
			return null;
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();
		if (partB == null) {
			return null;
		}

		IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn = partB.getFNonComplianceOfTDSVat();

		if (tdsVatEtcEn == null) {
			return null;
		}
		return findByType(ia, tdsVatEtcEn, type);
	}

	@Override
	public Boolean deletePartBTDSVATStatute(Long iaId, IANonComplianceOfTDSNVATEnum type) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = ia.getAuditObservation();

		if (auditObservation == null) {
			return false;
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();
		if (partB == null) {
			return false;
		}

		IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn = partB.getFNonComplianceOfTDSVat();

		if (tdsVatEtcEn == null) {
			return false;
		}
		return deleteByType(ia, tdsVatEtcEn, type);
	}

	private Boolean deleteByType(InternalAuditEntity internalAudit,
			IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn, IANonComplianceOfTDSNVATEnum type) {
		switch (type) {
		case TDS: {
			IANonComplianceTDSEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceTDS();
			if (noncomplianceTDSEntity == null) {
				return false;
			}
			tdsVatEtcEn.setNonComplianceTDS(null);
			nonComplianceTDSRepository.delete(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return true;

		}
		case VAT_GST: {
			IANonComplianceVATGSTEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceVATGST();
			if (noncomplianceTDSEntity == null) {
				return false;
			}
			tdsVatEtcEn.setNonComplianceTDS(null);
			nonComplianceVATGSTRepository.delete(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return true;
		}
		case ROYALTY: {
			IANonComplianceRoyaltyFeeEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceRoyaltyFee();
			if (noncomplianceTDSEntity == null) {
				return false;
			}
			tdsVatEtcEn.setNonComplianceTDS(null);
			nonComplianceRoyaltyFeeRepository.delete(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return true;
		}
		case TGS_ON_GST: {
			IANonComplianceTDSOnGSTEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceTDSOnGST();
			if (noncomplianceTDSEntity == null) {
				return false;
			}
			tdsVatEtcEn.setNonComplianceTDS(null);
			nonComplianceTDSOnGSTRepository.delete(noncomplianceTDSEntity);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return true;
		}
		case LABOUR_CESS: {
			IANonComplianceLabourCessEntity labourCess = tdsVatEtcEn.getNonComplianceLaboutCess();
			if (labourCess == null) {
				return false;
			}
			tdsVatEtcEn.setNonComplianceTDS(null);
			nonComplianceLaboutCessRepository.delete(labourCess);
			vatAndOtherRelevantRepository.save(tdsVatEtcEn);
			return true;
		}
		case OTHERS: {
			return false;
		}
		}
		return null;
	}

	@Override
	public Boolean deletePartBTDSVATStatuteOthers(Long id, Long iaId) {
		InternalAuditEntity ia = internalAuditService.findInternalAuditEntityById(iaId);
		IAAuditObservationEntity auditObservation = ia.getAuditObservation();

		if (auditObservation == null) {
			return false;
		}

		IAAuditObservationPartBEntity partB = auditObservation.getPartB();
		if (partB == null) {
			return false;
		}

		IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn = partB.getFNonComplianceOfTDSVat();

		if (tdsVatEtcEn == null) {
			return false;
		}

		List<IANonComplianceTDSVATCustomEntity> customList = tdsVatEtcEn.getNonComplianceTDSVATCustom();
		if (customList == null) {
			return false;
		}
		Optional<IANonComplianceTDSVATCustomEntity> customEnO = nonComplianceTDSVATCustomRepository.findById(id);
		if (!customEnO.isPresent()) {
			return false;
		}
		customList.remove(customEnO.get());
		nonComplianceTDSVATCustomRepository.delete(customEnO.get());
		vatAndOtherRelevantRepository.save(tdsVatEtcEn);
		return true;
	}

	private IANonComplianceOfTDSNVATDTO findByType(InternalAuditEntity internalAudit,
			IANonComplianceOfTDSVATNOtherRelevantStatuteEntity tdsVatEtcEn, IANonComplianceOfTDSNVATEnum type) {
		switch (type) {
		case TDS: {
			IANonComplianceTDSEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceTDS();
			return getDTOComplianceTDSDTO(noncomplianceTDSEntity, internalAudit);
		}
		case VAT_GST: {
			IANonComplianceVATGSTEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceVATGST();
			return getENNonComplianceVATGSTDTO(noncomplianceTDSEntity, internalAudit);
		}
		case ROYALTY: {
			IANonComplianceRoyaltyFeeEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceRoyaltyFee();
			return getENRoyaltyFeeDTO(noncomplianceTDSEntity, internalAudit);
		}
		case TGS_ON_GST: {
			IANonComplianceTDSOnGSTEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceTDSOnGST();
			return getENTDSONGSTDTO(noncomplianceTDSEntity, internalAudit);
		}
		case LABOUR_CESS: {
			IANonComplianceLabourCessEntity noncomplianceTDSEntity = tdsVatEtcEn.getNonComplianceLaboutCess();
			return getENLabourCessDTO(noncomplianceTDSEntity, internalAudit);
		}
		case OTHERS: {
			return null;
		}
		}
		return null;
	}

	private IANonComplianceOfTDSNVATDTO getDTOComplianceTDSDTO(IANonComplianceTDSEntity en, InternalAuditEntity ia) {
		return IANonComplianceOfTDSNVATDTO.builder().amountInvolved(en.getAmountInvolved())
				.description(en.getDescription()).fileId(en.getFile() != null ? en.getFile().getFileId() : null)
				.type(IANonComplianceOfTDSNVATEnum.TDS).iaId(ia.getId()).id(en.getId()).build();
	}

	private IANonComplianceOfTDSNVATDTO getENNonComplianceVATGSTDTO(IANonComplianceVATGSTEntity en,
			InternalAuditEntity ia) {
		return IANonComplianceOfTDSNVATDTO.builder().amountInvolved(en.getAmountInvolved())
				.description(en.getDescription()).fileId(en.getFile() != null ? en.getFile().getFileId() : null)
				.type(IANonComplianceOfTDSNVATEnum.VAT_GST).iaId(ia.getId()).id(en.getId()).build();
	}

	private IANonComplianceOfTDSNVATDTO getENRoyaltyFeeDTO(IANonComplianceRoyaltyFeeEntity en, InternalAuditEntity ia) {
		return IANonComplianceOfTDSNVATDTO.builder().amountInvolved(en.getAmountInvolved())
				.description(en.getDescription()).fileId(en.getFile() != null ? en.getFile().getFileId() : null)
				.type(IANonComplianceOfTDSNVATEnum.ROYALTY).iaId(ia.getId()).id(en.getId()).build();
	}

	private IANonComplianceOfTDSNVATDTO getENTDSONGSTDTO(IANonComplianceTDSOnGSTEntity en, InternalAuditEntity ia) {
		return IANonComplianceOfTDSNVATDTO.builder().amountInvolved(en.getAmountInvolved())
				.description(en.getDescription()).fileId(en.getFile() != null ? en.getFile().getFileId() : null)
				.type(IANonComplianceOfTDSNVATEnum.TGS_ON_GST).iaId(ia.getId()).id(en.getId()).build();
	}

	private IANonComplianceOfTDSNVATDTO getENLabourCessDTO(IANonComplianceLabourCessEntity en, InternalAuditEntity ia) {
		return IANonComplianceOfTDSNVATDTO.builder().amountInvolved(en.getAmountInvolved())
				.description(en.getDescription()).fileId(en.getFile() != null ? en.getFile().getFileId() : null)
				.type(IANonComplianceOfTDSNVATEnum.LABOUR_CESS).iaId(ia.getId()).id(en.getId()).build();
	}

	private IANonComplianceOfTDSNVATDTO getENCustomDTO(IANonComplianceTDSVATCustomEntity en, InternalAuditEntity ia) {
		return IANonComplianceOfTDSNVATDTO.builder().amountInvolved(en.getAmountInvolved())
				.description(en.getDescription()).fileId(en.getFile() != null ? en.getFile().getFileId() : null)
				.name(en.getName()).type(IANonComplianceOfTDSNVATEnum.OTHERS).iaId(ia.getId()).id(en.getId()).build();
	}

	private IANonComplianceTDSEntity getENComplianceTDSEntity(IANonComplianceOfTDSNVATDTO dto) {
		FileEntity file = null;
		if (dto.getFileId() != null)
			file = fileService.getFileEntityById(dto.getFileId());
		return IANonComplianceTDSEntity.builder().amountInvolved(dto.getAmountInvolved())
				.description(dto.getDescription()).file(file).id(dto.getId()).build();
	}

	private IANonComplianceVATGSTEntity getENNonComplianceVATGSTEntity(IANonComplianceOfTDSNVATDTO dto) {
		FileEntity file = null;
		if (dto.getFileId() != null)
			file = fileService.getFileEntityById(dto.getFileId());
		return IANonComplianceVATGSTEntity.builder().amountInvolved(dto.getAmountInvolved())
				.description(dto.getDescription()).file(file).id(dto.getId()).build();
	}

	private IANonComplianceRoyaltyFeeEntity getENIANonComplianceRoyaltyFeeEntity(IANonComplianceOfTDSNVATDTO dto) {
		FileEntity file = null;
		if (dto.getFileId() != null)
			file = fileService.getFileEntityById(dto.getFileId());
		return IANonComplianceRoyaltyFeeEntity.builder().amountInvolved(dto.getAmountInvolved())
				.description(dto.getDescription()).file(file).id(dto.getId()).build();
	}

	private IANonComplianceTDSOnGSTEntity getENIANonComplianceTDSOnGSTEntity(IANonComplianceOfTDSNVATDTO dto) {
		FileEntity file = null;
		if (dto.getFileId() != null)
			file = fileService.getFileEntityById(dto.getFileId());
		return IANonComplianceTDSOnGSTEntity.builder().amountInvolved(dto.getAmountInvolved())
				.description(dto.getDescription()).file(file).id(dto.getId()).build();
	}

	private IANonComplianceLabourCessEntity getENIANonComplianceLabourCessEntity(IANonComplianceOfTDSNVATDTO dto) {
		FileEntity file = null;
		if (dto.getFileId() != null)
			file = fileService.getFileEntityById(dto.getFileId());
		return IANonComplianceLabourCessEntity.builder().amountInvolved(dto.getAmountInvolved())
				.description(dto.getDescription()).file(file).id(dto.getId()).build();
	}

	private IANonComplianceTDSVATCustomEntity getENIANonComplianceTDSVATCustomEntity(IANonComplianceOfTDSNVATDTO dto) {
		FileEntity file = null;
		if (dto.getFileId() != null)
			file = fileService.getFileEntityById(dto.getFileId());
		return IANonComplianceTDSVATCustomEntity.builder().amountInvolved(dto.getAmountInvolved()).name(dto.getName())
				.description(dto.getDescription()).file(file).id(dto.getId()).build();
	}

}
