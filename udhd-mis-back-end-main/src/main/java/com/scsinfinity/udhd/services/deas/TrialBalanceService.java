package com.scsinfinity.udhd.services.deas;

import java.time.LocalDate;
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
import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceUploadReportSummaryEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.deas.ITrialBalanceFileInfoRepository;
import com.scsinfinity.udhd.dao.repositories.deas.ITrialBalanceUploadReportSummaryRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.TrialBalanceUploadReportSummaryDTO;
import com.scsinfinity.udhd.services.deas.interfaces.ITrialBalanceAsyncDetailService;
import com.scsinfinity.udhd.services.deas.interfaces.ITrialBalanceService;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TrialBalanceService implements ITrialBalanceService {

	@Value("${scs.setting.folder.deas-data.nickname}")
	private String deasFolderNickname;

	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IFileService fileService;
	private final IULBService ulbService;
	private final ITrialBalanceAsyncDetailService asyncDetailTBService;
	private final ITrialBalanceUploadReportSummaryRepository tbBalanceRepoSummary;
	private final ITrialBalanceFileInfoRepository trialBalanceFileInfoRepository;
	private final IPaginationInfoService<TrialBalanceUploadReportSummaryDTO, TrialBalanceUploadReportSummaryEntity> paginationInfoService;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_ULB_CMO + "')")
	public Boolean uploadTrialBalance(MultipartFile file) {
		try {
			UserEntity currentUser = securedUser.getCurrentUserInfo();
			FolderUserGroupsEnum fodlerUserGroup = FolderUserGroupsEnum.AUTHENTICATED;
			if (currentUser == null) {
				fodlerUserGroup = FolderUserGroupsEnum.UNAUTHENTICATED;
			}
			FolderEntity folder = folderService.getFolderEntityByNickname(deasFolderNickname, fodlerUserGroup);

			FileEntity fileEntity = fileService.saveFileAndReturnEntity(file, folder.getFolderId());
			asyncDetailTBService.handleTrialBalanceUploadDataOperation(file, fileEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public TrialBalanceUploadReportSummaryDTO getTrialBalanceFileUploadSummary(Long id) {
		TrialBalanceUploadReportSummaryEntity tbSummaryData = tbBalanceRepoSummary.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("TrialBalanceUploadReportSummaryEntity" + id));
		UserEntity user = securedUser.getCurrentUserInfo();
		if (user.getAuthority().getAuthorityType() == AuthorityTypeEnum.ULB
				|| user.getAuthority().getAuthorityType() == AuthorityTypeEnum.OTHERS) {
			ULBEntity ulb = ulbService.findULBEntityById(tbSummaryData.getUlbId());
			if (!validateIfAllowed(user, ulb)) {
				throw new BadRequestAlertException("UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS", "UNAUTHORISED_ACCESS");
			}
		}
		return tbSummaryData.getDTO();
	}

	private Boolean validateIfAllowed(UserEntity user, ULBEntity ulb) {
		UserProfileEntity upe = user.getUserProfile();
		return upe.getUserUlbInfo().getUlbs().contains(ulb);
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<TrialBalanceUploadReportSummaryDTO> getTrialBalanceFileUploadSummaryPage(
			Pagination<TrialBalanceUploadReportSummaryDTO> data) {
		UserEntity user = securedUser.getCurrentUserInfo();
		GenericResponseObject<TrialBalanceUploadReportSummaryDTO> budgetData = null;
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
	public Resource getSummaryFile(String fileId) {
		try {
			UserEntity user = securedUser.getCurrentUserInfo();
			Optional<TrialBalanceUploadReportSummaryEntity> summary = tbBalanceRepoSummary.findByResponseFileId(fileId);
			if (!summary.isPresent()) {
				throw new BadRequestAlertException("INVALID_FILE_FOR_SUMMARY", "INVALID_FILE_FOR_SUMMARY",
						"INVALID_FILE_FOR_SUMMARY");
			}
			if (user.getAuthority().getAuthorityType() == AuthorityTypeEnum.ULB
					|| user.getAuthority().getAuthorityType() == AuthorityTypeEnum.OTHERS) {
				ULBEntity ulb = ulbService.findULBEntityById(summary.get().getUlbId());
				if (!validateIfAllowed(user, ulb)) {
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
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_UC + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public Resource getTBFile(String fileId) {
		try {
			UserEntity user = securedUser.getCurrentUserInfo();
			Optional<TrialBalanceUploadReportSummaryEntity> summary = tbBalanceRepoSummary
					.findByTrialBalanceFile_File_FileId(fileId);
			if (!summary.isPresent()) {
				throw new BadRequestAlertException("INVALID_FILE_FOR_SUMMARY", "INVALID_FILE_FOR_SUMMARY",
						"INVALID_FILE_FOR_SUMMARY");
			}
			if (user.getAuthority().getAuthorityType() == AuthorityTypeEnum.ULB
					|| user.getAuthority().getAuthorityType() == AuthorityTypeEnum.OTHERS) {
				ULBEntity ulb = ulbService.findULBEntityById(summary.get().getUlbId());
				if (!validateIfAllowed(user, ulb)) {
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
			+ "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "')")
	public Long getTBDuring(LocalDate startDate, LocalDate endDate) {
		return trialBalanceFileInfoRepository.findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatus(startDate,
				endDate, TypeStatusEnum.ACTIVE).stream().count();
	}

	private GenericResponseObject<TrialBalanceUploadReportSummaryDTO> getPageForULBUsers(
			Pagination<TrialBalanceUploadReportSummaryDTO> data, UserEntity user) {
		Page<TrialBalanceUploadReportSummaryDTO> reports = null;
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<TrialBalanceUploadReportSummaryEntity> annualFsPage = null;
			annualFsPage = tbBalanceRepoSummary.findAllByUlbId(ulbs.get(0).getId(), pageable);
			reports = paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDTO());
		}
		return new GenericResponseObject<TrialBalanceUploadReportSummaryDTO>(reports.getTotalElements(), reports,
				data.getPageNo(), data.getPageSize());
	}

	private GenericResponseObject<TrialBalanceUploadReportSummaryDTO> getPageForOtherUsers(
			Pagination<TrialBalanceUploadReportSummaryDTO> data, UserEntity user) {
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<TrialBalanceUploadReportSummaryEntity> annualFsPage = null;

			annualFsPage = tbBalanceRepoSummary
					.findAllByUlbIdIn(ulbs.stream().map(ulb -> ulb.getId()).collect(Collectors.toList()), pageable);
			Page<TrialBalanceUploadReportSummaryDTO> budgets = paginationInfoService.getDataAsDTO(annualFsPage,
					en -> en.getDTO());
			return new GenericResponseObject<TrialBalanceUploadReportSummaryDTO>(budgets.getTotalElements(), budgets,
					data.getPageNo(), data.getPageSize());
		}
		return null;
	}

	private GenericResponseObject<TrialBalanceUploadReportSummaryDTO> getPageForUDHDOrSLPMUUsers(
			Pagination<TrialBalanceUploadReportSummaryDTO> data) {
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
		Page<TrialBalanceUploadReportSummaryEntity> annualFsPage = null;
		annualFsPage = tbBalanceRepoSummary.findAll(pageable);
		Page<TrialBalanceUploadReportSummaryDTO> budgets = paginationInfoService.getDataAsDTO(annualFsPage,
				en -> en.getDTO());
		return new GenericResponseObject<TrialBalanceUploadReportSummaryDTO>(budgets.getTotalElements(), budgets,
				data.getPageNo(), data.getPageSize());
	}

}
