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

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.configurations.application.date.FinYearDateDTO;
import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.deas.BudgetUploadsInfoEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.deas.IBudgetUploadsInfoRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.BudgetUploadDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IBudgetUploadService;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetUploadService implements IBudgetUploadService {

	@Value("${scs.setting.folder.deas-data.nickname}")
	private String deasFolderNickname;

	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IFileService fileService;
	private final IPaginationInfoService<BudgetUploadDTO, BudgetUploadsInfoEntity> paginationInfoService;
	private final DateUtil dateUtil;
	private final IULBService ulbService;
	private final IBudgetUploadsInfoRepository budgetRepo;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_ULB_CMO + "')")
	public BudgetUploadDTO uploadBudgetStatement(BudgetUploadDTO budgetData) {
		BudgetUploadsInfoEntity hData = null;
		try {
			// save file
			FileEntity file = handleFileSave(budgetData);
			UserEntity user = securedUser.getCurrentUserInfo();
			ULBEntity ulb = ulbService.findULBEntityById(budgetData.getUlbId());
			TypeStatusEnum status = TypeStatusEnum.ACTIVE;
			FinYearDateDTO stringDates = FinYearDateDTO.builder().endDate(budgetData.getEndDate())
					.startDate(budgetData.getStartDate()).inputDate(budgetData.getInputDate())
					.build();

			FinancialRealDatesDTO finDates = dateUtil.interpretDateFromFinYearAndQuarter(stringDates);
			List<BudgetUploadsInfoEntity> existingData = budgetRepo.findByGivenCriteria(finDates.getStartDate(),
					finDates.getEndDate(), ulb.getId(), status.ordinal());

			if (!validateIfAllowed(user, ulb)) {
				status = TypeStatusEnum.ERROR;
			}

			hData = BudgetUploadsInfoEntity.builder().yearEndDate(finDates.getEndDate()).overWritten(false).file(file)
					.yearStartDate(finDates.getStartDate()).status(status).ulb(ulb).build();

			List<BudgetUploadsInfoEntity> annualDataList = new ArrayList<BudgetUploadsInfoEntity>();
			// check if overwrite by newfile
			if (existingData.size() > 0 && status != TypeStatusEnum.ERROR) {
				existingData.forEach(ex -> {

					ex.setOverWritten(true);
					ex.setStatus(TypeStatusEnum.INACTIVE);
					annualDataList.add(ex);
				});
				dataOverwriteHappened(existingData, budgetData);

			}
			budgetRepo.saveAll(annualDataList);
			hData = budgetRepo.save(hData);

			createUpdateDeasPeriodicDataEntity(hData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hData.getDto();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public BudgetUploadDTO getBudgetDataById(Long id) {
		BudgetUploadsInfoEntity budgetData = budgetRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("BudgetUploadsInfoEntity" + id));
		UserEntity user = securedUser.getCurrentUserInfo();
		if (user.getAuthority().getAuthorityType() == AuthorityTypeEnum.ULB
				|| user.getAuthority().getAuthorityType() == AuthorityTypeEnum.OTHERS) {
			if (!validateIfAllowed(user, budgetData.getUlb())) {
				throw new BadRequestAlertException("UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS");
			}
		}
		return budgetData.getDto();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<BudgetUploadDTO> getBudgetDataByPage(Pagination<BudgetUploadDTO> data) {
		UserEntity user = securedUser.getCurrentUserInfo();
		GenericResponseObject<BudgetUploadDTO> budgetData = null;
		switch (user.getAuthority().getAuthorityType()) {
		case ULB: {
			budgetData = getPageForULBUsers(data, user);
		}
		case OTHERS: {
			budgetData = getPageForOtherUsers(data, user);
		}
		case UDHD:
		case SLPMU: {
			budgetData = getPageForUDHDOrSLPMUUsers(data);
		}
		}

		return budgetData;
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
			Optional<BudgetUploadsInfoEntity> historicalDataEntityO = budgetRepo.findByFile_FileId(fileId);
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

	private void createUpdateDeasPeriodicDataEntity(BudgetUploadsInfoEntity afsData) {
		// List<DeasAnnualDataEntity>
		// findByYearStartDateAndYearEndDateAndStatusAndUlbIn();

	}

	private GenericResponseObject<BudgetUploadDTO> getPageForULBUsers(Pagination<BudgetUploadDTO> data,
			UserEntity user) {
		Page<BudgetUploadDTO> budgets = null;
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<BudgetUploadsInfoEntity> annualFsPage = null;
			annualFsPage = budgetRepo.findAllByUlb_Id(ulbs.get(0).getId(), pageable);
			budgets = paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());
		}
		return new GenericResponseObject<BudgetUploadDTO>(budgets.getTotalElements(), budgets, data.getPageNo(),
				data.getPageSize());
	}

	private GenericResponseObject<BudgetUploadDTO> getPageForOtherUsers(Pagination<BudgetUploadDTO> data,
			UserEntity user) {
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<BudgetUploadsInfoEntity> annualFsPage = null;
			annualFsPage = budgetRepo.findAllByUlbIn(ulbs, pageable);
			Page<BudgetUploadDTO> budgets = paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());
			return new GenericResponseObject<BudgetUploadDTO>(budgets.getTotalElements(), budgets, data.getPageNo(),
					data.getPageSize());
		}
		return null;
	}

	private GenericResponseObject<BudgetUploadDTO> getPageForUDHDOrSLPMUUsers(Pagination<BudgetUploadDTO> data) {
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
		Page<BudgetUploadsInfoEntity> annualFsPage = null;
		annualFsPage = budgetRepo.findAll(pageable);
		Page<BudgetUploadDTO> budgets = paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());
		return new GenericResponseObject<BudgetUploadDTO>(budgets.getTotalElements(), budgets, data.getPageNo(),
				data.getPageSize());
	}

	private Boolean validateIfAllowed(UserEntity user, ULBEntity ulb) {
		UserProfileEntity upe = user.getUserProfile();
		return upe.getUserUlbInfo().getUlbs().contains(ulb);
	}

	private FileEntity handleFileSave(BudgetUploadDTO budgetUploadDto) {
		try {
			if (!budgetUploadDto.getFile().getContentType().toString().equals("application/pdf")) {
				throw new BadRequestAlertException("INVALID_UPLOAD", "INVALID_UPLOAD_ONLY_PDF_ALLOWED",
						"INVALID_UPLOAD_ONLY_PDF_ALLOWED");
			}
			UserEntity currentUser = securedUser.getCurrentUserInfo();
			FolderUserGroupsEnum fodlerUserGroup = FolderUserGroupsEnum.AUTHENTICATED;
			if (currentUser == null) {
				fodlerUserGroup = FolderUserGroupsEnum.UNAUTHENTICATED;
			}
			FolderEntity folder = folderService.getFolderEntityByNickname(deasFolderNickname, fodlerUserGroup);

			return fileService.saveFileAndReturnEntity(budgetUploadDto.getFile(), folder.getFolderId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void dataOverwriteHappened(List<BudgetUploadsInfoEntity> historicDataEn, BudgetUploadDTO annualFinStmtDto) {
		// TODO Auto-generated method stub
		// send msg
	}

}
