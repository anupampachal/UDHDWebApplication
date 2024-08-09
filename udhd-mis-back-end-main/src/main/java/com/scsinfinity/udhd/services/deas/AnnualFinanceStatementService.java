package com.scsinfinity.udhd.services.deas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.deas.AnnualFinanceStatementEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.deas.IDeasAnnualFinancialStatementRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.AnnualFinanceStatementDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IAnnualFinanceStatementService;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnnualFinanceStatementService implements IAnnualFinanceStatementService {

	@Value("${scs.setting.folder.deas-data.nickname}")
	private String deasFolderNickname;

	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IFileService fileService;
	private final IDeasAnnualFinancialStatementRepository deasAFSRepo;
	private final IPaginationInfoService<AnnualFinanceStatementDTO, AnnualFinanceStatementEntity> paginationInfoService;
	private final IULBService ulbService;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_ULB_CMO + "')")
	public AnnualFinanceStatementDTO uploadAnnualFinanceStatement(AnnualFinanceStatementDTO annualData) {
		AnnualFinanceStatementEntity hData = null;
		try {
			// save file
			FileEntity file = handleFileSave(annualData);
			UserEntity user = securedUser.getCurrentUserInfo();
			ULBEntity ulb = ulbService.findULBEntityById(annualData.getUlbId());
			TypeStatusEnum status = TypeStatusEnum.ACTIVE;
			List<AnnualFinanceStatementEntity> existingData = deasAFSRepo.findByGivenCriteria(annualData.getStartDate(),
					annualData.getEndDate(), ulb.getId(), status.ordinal());

			if (!validateIfAllowed(user, ulb)) {
				status = TypeStatusEnum.ERROR;
			}

			hData = AnnualFinanceStatementEntity.builder().yearEndDate(annualData.getEndDate()).overWritten(false)
					.file(file).yearStartDate(annualData.getStartDate()).status(status).ulb(ulb).build();

			List<AnnualFinanceStatementEntity> annualDataList = new ArrayList<AnnualFinanceStatementEntity>();
			// check if overwrite by newfile
			if (existingData.size() > 0 && status != TypeStatusEnum.ERROR) {
				existingData.forEach(ex -> {

					ex.setOverWritten(true);
					ex.setStatus(TypeStatusEnum.INACTIVE);
					annualDataList.add(ex);
				});
				dataOverwriteHappened(existingData, annualData);

			}
			deasAFSRepo.saveAll(annualDataList);
			hData = deasAFSRepo.save(hData);

			createUpdateDeasAnnualDataEntity(hData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hData.getDto();
	}

	private void createUpdateDeasAnnualDataEntity(AnnualFinanceStatementEntity afsData) {
		// List<DeasAnnualDataEntity>
		// findByYearStartDateAndYearEndDateAndStatusAndUlbIn();

	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public AnnualFinanceStatementDTO getAnnualFinanceStatementById(Long id) {
		AnnualFinanceStatementEntity historicData = deasAFSRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("DEASHistoricalDataEntity" + id));
		UserEntity user = securedUser.getCurrentUserInfo();
		if (user.getAuthority().getAuthorityType() == AuthorityTypeEnum.ULB
				|| user.getAuthority().getAuthorityType() == AuthorityTypeEnum.OTHERS) {
			if (!validateIfAllowed(user, historicData.getUlb())) {
				throw new BadRequestAlertException("UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS");
			}
		}
		return historicData.getDto();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<AnnualFinanceStatementDTO> getAnnualFinanceStatementByPage(
			Pagination<AnnualFinanceStatementDTO> dataP) {
		UserEntity user = securedUser.getCurrentUserInfo();
		Page<AnnualFinanceStatementDTO> data = null;
		switch (user.getAuthority().getAuthorityType()) {
		case ULB: {
			data = getPageForULBUsers(dataP, user);
		}
		case OTHERS: {
			data = getPageForOtherUsers(dataP, user);
		}
		case UDHD:
		case SLPMU: {
			data = getPageForUDHDOrSLPMUUsers(dataP);
		}
		}

		return new GenericResponseObject<AnnualFinanceStatementDTO>(data.getTotalElements(), data, dataP.getPageNo(),
				dataP.getPageSize());
	}

	private Page<AnnualFinanceStatementDTO> getPageForULBUsers(Pagination<AnnualFinanceStatementDTO> data,
			UserEntity user) {
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<AnnualFinanceStatementEntity> annualFsPage = null;
			annualFsPage = deasAFSRepo.findAllByUlb_Id(ulbs.get(0).getId(), pageable);
			return paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());

		}
		return null;
	}

	private Page<AnnualFinanceStatementDTO> getPageForOtherUsers(Pagination<AnnualFinanceStatementDTO> data,
			UserEntity user) {
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<AnnualFinanceStatementEntity> annualFsPage = null;
			annualFsPage = deasAFSRepo.findAllByUlbIn(ulbs, pageable);
			return paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());

		}
		return null;
	}

	private Page<AnnualFinanceStatementDTO> getPageForUDHDOrSLPMUUsers(Pagination<AnnualFinanceStatementDTO> data) {
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
		Page<AnnualFinanceStatementEntity> annualFsPage = null;
		annualFsPage = deasAFSRepo.findAll(pageable);
		return paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());

	}

	private Boolean validateIfAllowed(UserEntity user, ULBEntity ulb) {
		UserProfileEntity upe = user.getUserProfile();
		return upe.getUserUlbInfo().getUlbs().contains(ulb);
	}

	private FileEntity handleFileSave(AnnualFinanceStatementDTO historicDataDTO) {
		try {
			if (!historicDataDTO.getFile().getContentType().toString().equals("application/pdf")) {
				throw new BadRequestAlertException("INVALID_UPLOAD", "INVALID_UPLOAD_ONLY_PDF_ALLOWED",
						"INVALID_UPLOAD_ONLY_PDF_ALLOWED");
			}
			UserEntity currentUser = securedUser.getCurrentUserInfo();
			FolderUserGroupsEnum fodlerUserGroup = FolderUserGroupsEnum.AUTHENTICATED;
			if (currentUser == null) {
				fodlerUserGroup = FolderUserGroupsEnum.UNAUTHENTICATED;
			}
			FolderEntity folder = folderService.getFolderEntityByNickname(deasFolderNickname, fodlerUserGroup);

			return fileService.saveFileAndReturnEntity(historicDataDTO.getFile(), folder.getFolderId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void dataOverwriteHappened(List<AnnualFinanceStatementEntity> historicDataEn,
			AnnualFinanceStatementDTO annualFinStmtDto) {
		// TODO Auto-generated method stub
		// send msg
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public Resource getFile(String fileId) {
		try {
			UserEntity user = securedUser.getCurrentUserInfo();
			Optional<AnnualFinanceStatementEntity> historicalDataEntityO = deasAFSRepo.findByFile_FileId(fileId);
			if (!historicalDataEntityO.isPresent()) {
				throw new BadRequestAlertException("INVALID_FILE_FOR_HISTORIC_DATA", "INVALID_FILE_FOR_HISTORIC_DATA",
						"INVALID_FILE_FOR_HISTORIC_DATA");
			}
			if (user.getAuthority().getAuthorityType() == AuthorityTypeEnum.ULB
					|| user.getAuthority().getAuthorityType() == AuthorityTypeEnum.OTHERS) {
				if (!validateIfAllowed(user, historicalDataEntityO.get().getUlb())) {
					throw new BadRequestAlertException("UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS",
							"UNAUTHORISED_ACCESS");
				}
			}
			return fileService.getFile(fileId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_ULB_CMO + "')")
	public List<ULBDTO> getULBsMappedToMe() {
		UserEntity user = securedUser.getCurrentUserInfo();
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		return ulbs.stream().map(ulb -> ulb.getDTO()).collect(Collectors.toList());
	}

}
