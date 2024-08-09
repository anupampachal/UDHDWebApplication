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
import com.scsinfinity.udhd.dao.entities.deas.DEASHistoricalDataEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderEntity;
import com.scsinfinity.udhd.dao.entities.storage.FolderUserGroupsEnum;
import com.scsinfinity.udhd.dao.repositories.deas.IDeasHistoricDataRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.base.interfaces.IFileService;
import com.scsinfinity.udhd.services.base.interfaces.IFolderService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.HistoricDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IHistoricDataService;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HistoricDataService implements IHistoricDataService {

	@Value("${scs.setting.folder.deas-data.nickname}")
	private String deasFolderNickname;

	private final IFolderService folderService;
	private final SecuredUserInfoService securedUser;
	private final IFileService fileService;
	private final IULBService ulbService;
	private final IDeasHistoricDataRepository historicDataRepo;
	private final IPaginationInfoService<HistoricDataDTO, DEASHistoricalDataEntity> paginationInfoService;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT
			+ "','" + AuthorityConstants.ROLE_ULB_CMO + "')")
	public HistoricDataDTO uploadHistoricData(HistoricDataDTO historicDataDTO) {
		DEASHistoricalDataEntity hData = null;
		try {
			// save file
			FileEntity file = handleFileSave(historicDataDTO);
			UserEntity user = securedUser.getCurrentUserInfo();
			ULBEntity ulb = ulbService.findULBEntityById(historicDataDTO.getUlbId());
			TypeStatusEnum status = TypeStatusEnum.ACTIVE;
			List<DEASHistoricalDataEntity> existingData = historicDataRepo.findByGivenCriteria(
					historicDataDTO.getStartDate(), historicDataDTO.getEndDate(), ulb.getId(), status.ordinal());

			if (!validateIfAllowed(user, ulb)) {
				status = TypeStatusEnum.ERROR;
			}

			hData = DEASHistoricalDataEntity.builder().endDate(historicDataDTO.getEndDate()).overWritten(false)
					.file(file).startDate(historicDataDTO.getStartDate()).status(status).ulb(ulb).build();

			List<DEASHistoricalDataEntity> historicList = new ArrayList<DEASHistoricalDataEntity>();
			// check if overwrite by newfile
			if (existingData.size() > 0 && status != TypeStatusEnum.ERROR) {
				existingData.forEach(ex -> {

					ex.setOverWritten(true);
					ex.setStatus(TypeStatusEnum.INACTIVE);
					historicList.add(ex);
				});
				dataOverwriteHappened(existingData, historicDataDTO);

			}
			historicDataRepo.saveAll(historicList);
			hData = historicDataRepo.save(hData);

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
	public HistoricDataDTO getHistoricDataById(Long id) {
		DEASHistoricalDataEntity historicData = historicDataRepo.findById(id)
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
	public GenericResponseObject<HistoricDataDTO> getHistoricDataByPage(Pagination<HistoricDataDTO> data) {
		UserEntity user = securedUser.getCurrentUserInfo();
		Page<HistoricDataDTO> historicData = null;
		switch (user.getAuthority().getAuthorityType()) {
		case ULB: {
			historicData = getPageForULBUsers(data, user);
		}
		case OTHERS: {
			historicData = getPageForOtherUsers(data, user);
		}
		case UDHD:
		case SLPMU: {
			historicData = getPageForUDHDOrSLPMUUsers(data);
		}
		}

		return new GenericResponseObject<HistoricDataDTO>(historicData.getTotalElements(), historicData,
				data.getPageNo(), data.getPageSize());
	}

	private Page<HistoricDataDTO> getPageForULBUsers(Pagination<HistoricDataDTO> data, UserEntity user) {
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<DEASHistoricalDataEntity> historicalDataEntityPage = null;
			historicalDataEntityPage = historicDataRepo.findAllByUlb_Id(ulbs.get(0).getId(), pageable);
			return paginationInfoService.getDataAsDTO(historicalDataEntityPage, en -> en.getDto());

		}
		return null;
	}

	private Page<HistoricDataDTO> getPageForOtherUsers(Pagination<HistoricDataDTO> data, UserEntity user) {
		List<ULBEntity> ulbs = user.getUserProfile().getUserUlbInfo().getUlbs();
		if (ulbs.size() > 0) {
			Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
			Page<DEASHistoricalDataEntity> historicalDataEntityPage = null;
			historicalDataEntityPage = historicDataRepo.findAllByUlbIn(ulbs, pageable);
			return paginationInfoService.getDataAsDTO(historicalDataEntityPage, en -> en.getDto());

		}
		return null;
	}

	private Page<HistoricDataDTO> getPageForUDHDOrSLPMUUsers(Pagination<HistoricDataDTO> data) {
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
		Page<DEASHistoricalDataEntity> historicalDataEntityPage = null;
		historicalDataEntityPage = historicDataRepo.findAll(pageable);
		return paginationInfoService.getDataAsDTO(historicalDataEntityPage, en -> en.getDto());

	}

	private Boolean validateIfAllowed(UserEntity user, ULBEntity ulb) {
		UserProfileEntity upe = user.getUserProfile();
		return upe.getUserUlbInfo().getUlbs().contains(ulb);
	}

	private FileEntity handleFileSave(HistoricDataDTO historicDataDTO) {
		try {
			if (!historicDataDTO.getFile().getContentType().toString().equals("application/zip")) {
				throw new BadRequestAlertException("INVALID_UPLOAD", "INVALID_UPLOAD_ONLY_ZIP_ALLOWED",
						"INVALID_UPLOAD_ONLY_ZIP_ALLOWED");
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

	private void dataOverwriteHappened(List<DEASHistoricalDataEntity> historicDataEn, HistoricDataDTO historicDataDTO) {
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
			Optional<DEASHistoricalDataEntity> historicalDataEntityO = historicDataRepo.findByFile_FileId(fileId);
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
