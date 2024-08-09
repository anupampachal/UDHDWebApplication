package com.scsinfinity.udhd.services.audit.acdc;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.audit.acdc.ULBWiseACDCStatusEntity;
import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.repositories.audit.acdc.IULBWiseACDCStatusRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.audit.acdc.dto.ACDCULBBasedDTO;
import com.scsinfinity.udhd.services.audit.acdc.interfaces.IACDCService;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IUserMgtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ACDCService implements IACDCService {

	private final IULBWiseACDCStatusRepository acDCStatusRepository;
	private final IPaginationInfoService<ACDCULBBasedDTO, ULBWiseACDCStatusEntity> paginationInfoService;
	private final IUserMgtService userService;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public ACDCULBBasedDTO findById(Long id) {
		log.info("findById");
		return acDCStatusRepository.findById(id).map(res -> res.getDTO())
				.orElseThrow(() -> new EntityNotFoundException("ACDCULBBasedDTO with id" + id));
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_AUDIT + "','"
			+ AuthorityConstants.ROLE_UDHD_SEC + "','" + AuthorityConstants.ROLE_UDHD_PSEC + "','"
			+ AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<ACDCULBBasedDTO> loadACDCByPage(Pagination<ACDCULBBasedDTO> data) {
		log.info("loadACDCByPage", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		Page<ULBWiseACDCStatusEntity> acdcPage = null;

		UserEntity user = userService.getCurrentUserInfoEntity();

		if (user.getAuthority().getName().equalsIgnoreCase(AuthorityConstants.ROLE_ULB_ACCOUNTANT)
				|| user.getAuthority().getName().equalsIgnoreCase(AuthorityConstants.ROLE_ULB_CMO)) {
			if (data.getQueryString() != null && user.getUserProfile() != null
					&& user.getUserProfile().getUserUlbInfo().getUlbs() != null && user.getUserProfile().getUserUlbInfo().getUlbs().get(0) != null) {
				acdcPage = acDCStatusRepository.findAllByTreasuryNameIgnoreCaseContainingAndUlb_Id(pageable,
						data.getQueryString(), user.getUserProfile().getUserUlbInfo().getUlbs().get(0).getId());
			} else if (user.getUserProfile() != null && user.getUserProfile().getUserUlbInfo().getUlbs() != null
					&& user.getUserProfile().getUserUlbInfo().getUlbs().get(0) != null) {
				acdcPage = acDCStatusRepository.findAllByUlb_Id(pageable,
						user.getUserProfile().getUserUlbInfo().getUlbs().get(0).getId());
			} else
				throw new BadRequestAlertException("User not mapped to any ULB", "USER_WITHOUT_ULB",
						"USER_WITHOUT_ULB");
		} else if (data.getQueryString() != null) {
			acdcPage = acDCStatusRepository.findAllByTreasuryNameIgnoreCaseContainingOrUlb_NameIgnoreCaseContaining(pageable,
					data.getQueryString(), data.getQueryString());
		} else {
			acdcPage = acDCStatusRepository.findAll(pageable);
		}
		Page<ACDCULBBasedDTO> publishers = paginationInfoService.getDataAsDTO(acdcPage, en -> en.getDTO());

		return new GenericResponseObject<ACDCULBBasedDTO>(publishers.getTotalElements(), publishers, data.getPageNo(),
				data.getPageSize());

	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_UDHD_SO
			+ "')")
	@Transactional
	public ACDCULBBasedDTO createUpdateACDC(ACDCULBBasedDTO acDC) {
		return acDCStatusRepository.save(acDC.getEntity()).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','" + AuthorityConstants.ROLE_UDHD_SO
			+ "')")
	public ACDCULBBasedDTO deactivate(Long id) {
		ULBWiseACDCStatusEntity acdc = acDCStatusRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("ACDC with id " + id));
		acdc.setStatus(false);
		return acDCStatusRepository.save(acdc).getDTO();
	}

}
