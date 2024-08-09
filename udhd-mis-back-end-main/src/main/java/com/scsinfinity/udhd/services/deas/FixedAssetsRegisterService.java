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
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.configurations.application.date.DateUtil;
import com.scsinfinity.udhd.configurations.application.date.FinYearDateDTO;
import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.base.AuthorityTypeEnum;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.deas.FixedAssetsRegisterEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.deas.IFixedAssetsRegisterRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.FixedAssetsRegisterDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IFixedAssetsRegisterService;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;
import com.scsinfinity.udhd.services.utils.dto.FinancialRealDatesDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FixedAssetsRegisterService implements IFixedAssetsRegisterService {

	@Value("${scs.setting.folder.deas-data.nickname}")
	private String deasFolderNickname;

	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IFileService fileService;
	private final IPaginationInfoService<FixedAssetsRegisterDTO, FixedAssetsRegisterEntity> paginationInfoService;
	private final DateUtil dateUtil;
	private final IULBService ulbService;
	private final IFixedAssetsRegisterRepository fixedAssetsRegisterRepo;

	@Override
	public FixedAssetsRegisterDTO uploadFixedAssetsRegisterStatement(FixedAssetsRegisterDTO budgetData) {
		FixedAssetsRegisterEntity hData = null;
		try {
			// save file
			FileEntity file = handleFileSave(budgetData);
			UserEntity user = securedUser.getCurrentUserInfo();
			ULBEntity ulb = ulbService.findULBEntityById(budgetData.getUlbId());
			TypeStatusEnum status = TypeStatusEnum.ACTIVE;
			FinYearDateDTO stringDates = FinYearDateDTO.builder().endDate(budgetData.getEndDate())
					.startDate(budgetData.getStartDate()).inputDate(budgetData.getInputDate())
					//.quarter(budgetData.getQuarter())
					.build();

			FinancialRealDatesDTO finDates = dateUtil.interpretDateFromFinYearAndQuarter(stringDates);
			List<FixedAssetsRegisterEntity> existingData = fixedAssetsRegisterRepo
					.findByGivenCriteria(finDates.getStartDate(), finDates.getEndDate(), ulb.getId(), status.ordinal());

			if (!validateIfAllowed(user, ulb)) {
				status = TypeStatusEnum.ERROR;
			}

			hData = FixedAssetsRegisterEntity.builder().yearEndDate(finDates.getEndDate()).overWritten(false).file(file)
					.yearStartDate(finDates.getStartDate()).status(status).ulb(ulb).build();

			List<FixedAssetsRegisterEntity> annualDataList = new ArrayList<FixedAssetsRegisterEntity>();
			// check if overwrite by newfile
			if (existingData.size() > 0 && status != TypeStatusEnum.ERROR) {
				existingData.forEach(ex -> {

					ex.setOverWritten(true);
					ex.setStatus(TypeStatusEnum.INACTIVE);
					annualDataList.add(ex);
				});
				dataOverwriteHappened(existingData, budgetData);

			}
			fixedAssetsRegisterRepo.saveAll(annualDataList);
			hData = fixedAssetsRegisterRepo.save(hData);

			createUpdateDeasPeriodicDataEntity(hData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hData.getDto();
	}

	@Override
	public FixedAssetsRegisterDTO getFixedAssetsRegisterDataById(Long id) {
		FixedAssetsRegisterEntity budgetData = fixedAssetsRegisterRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("FixedAssetsRegisterEntity" + id));
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
	public GenericResponseObject<FixedAssetsRegisterDTO> getFixedAssetsRegisterDataByPage(
			Pagination<FixedAssetsRegisterDTO> data) {
		UserEntity user = securedUser.getCurrentUserInfo();
		GenericResponseObject<FixedAssetsRegisterDTO> budgetData = null;
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
	public Resource getFile(String fileId) {
		try {
			UserEntity user = securedUser.getCurrentUserInfo();
			Optional<FixedAssetsRegisterEntity> historicalDataEntityO = fixedAssetsRegisterRepo
					.findByFile_FileId(fileId);
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
	public List<ULBDTO> getULBsMappedToMe() {
		UserEntity user = securedUser.getCurrentUserInfo();
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		return ulbs.stream().map(ulb -> ulb.getDTO()).collect(Collectors.toList());
	}

	private void createUpdateDeasPeriodicDataEntity(FixedAssetsRegisterEntity afsData) {
		// List<DeasAnnualDataEntity>
		// findByYearStartDateAndYearEndDateAndStatusAndUlbIn();

	}

	private GenericResponseObject<FixedAssetsRegisterDTO> getPageForULBUsers(Pagination<FixedAssetsRegisterDTO> data,
			UserEntity user) {
		Page<FixedAssetsRegisterDTO> budgets = null;
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<FixedAssetsRegisterEntity> annualFsPage = null;
			annualFsPage = fixedAssetsRegisterRepo.findAllByUlb_Id(ulbs.get(0).getId(), pageable);
			budgets = paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());
		}
		return new GenericResponseObject<FixedAssetsRegisterDTO>(budgets.getTotalElements(), budgets, data.getPageNo(),
				data.getPageSize());
	}

	private GenericResponseObject<FixedAssetsRegisterDTO> getPageForOtherUsers(Pagination<FixedAssetsRegisterDTO> data,
			UserEntity user) {
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<FixedAssetsRegisterEntity> annualFsPage = null;
			annualFsPage = fixedAssetsRegisterRepo.findAllByUlbIn(ulbs, pageable);
			Page<FixedAssetsRegisterDTO> budgets = paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());
			return new GenericResponseObject<FixedAssetsRegisterDTO>(budgets.getTotalElements(), budgets,
					data.getPageNo(), data.getPageSize());
		}
		return null;
	}

	private GenericResponseObject<FixedAssetsRegisterDTO> getPageForUDHDOrSLPMUUsers(
			Pagination<FixedAssetsRegisterDTO> data) {
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
		Page<FixedAssetsRegisterEntity> annualFsPage = null;
		annualFsPage = fixedAssetsRegisterRepo.findAll(pageable);
		Page<FixedAssetsRegisterDTO> budgets = paginationInfoService.getDataAsDTO(annualFsPage, en -> en.getDto());
		return new GenericResponseObject<FixedAssetsRegisterDTO>(budgets.getTotalElements(), budgets, data.getPageNo(),
				data.getPageSize());
	}

	private Boolean validateIfAllowed(UserEntity user, ULBEntity ulb) {
		UserProfileEntity upe = user.getUserProfile();
		return upe.getUserUlbInfo().getUlbs().contains(ulb);
	}

	private FileEntity handleFileSave(FixedAssetsRegisterDTO budgetUploadDto) {
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

	private void dataOverwriteHappened(List<FixedAssetsRegisterEntity> historicDataEn,
			FixedAssetsRegisterDTO annualFinStmtDto) {
		// TODO Auto-generated method stub
		// send msg
	}

}
