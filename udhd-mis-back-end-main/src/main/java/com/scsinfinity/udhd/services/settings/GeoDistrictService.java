package com.scsinfinity.udhd.services.settings;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;
import com.scsinfinity.udhd.dao.repositories.geography.IGeoDistrictRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.GeoDistrictDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDistrictService;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class GeoDistrictService implements IGeoDistrictService {

	private final IGeoDistrictRepository geoDistrictRepository;
	private final IPaginationInfoService<GeoDistrictDTO, GeoDistrictEntity> paginationInfoService;

	@Override
	public GeoDistrictDTO findByGeoDistrictId(Long id) {
		log.info("findByGeoDistrictId");
		return geoDistrictRepository.findById(id).map(res -> res.getDTO())
				.orElseThrow(() -> new EntityNotFoundException("District with id " + id));
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<GeoDistrictDTO> loadGeoDistrictByPage(Pagination<GeoDistrictDTO> data) {
		log.info("loadGeoDistrictByPage", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		Page<GeoDistrictEntity> geoDistrictPage = null;
		if (data.getQueryString() != null) {
			geoDistrictPage = geoDistrictRepository
					.findAllByNameIgnoreCaseContainingOrCodeIgnoreCaseContainingOrDivision_NameIgnoreCaseContaining(
							pageable, data.getQueryString(), data.getQueryString(), data.getQueryString());
		} else {
			geoDistrictPage = geoDistrictRepository.findAll(pageable);
		}
		Page<GeoDistrictDTO> publishers = paginationInfoService.getDataAsDTO(geoDistrictPage, en -> en.getDTO());

		return new GenericResponseObject<GeoDistrictDTO>(publishers.getTotalElements(), publishers, data.getPageNo(),
				data.getPageSize());

	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public GeoDistrictDTO createUpdateGeoDistrict(GeoDistrictDTO geoDistrictDTO) {
		return geoDistrictRepository.save(geoDistrictDTO.getEntity()).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public GeoDistrictDTO deactivate(Long id) {
		GeoDistrictEntity geoDistrict = geoDistrictRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("District with id " + id));
		geoDistrict.setActive(false);
		return geoDistrictRepository.save(geoDistrict).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<GeoDistrictDTO> loadGeoDistrictByPageForDivision(Pagination<GeoDistrictDTO> data) {
		log.info("loadGeoDistrictByPage", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		Page<GeoDistrictEntity> geoDistrictPage = null;
		if (data.getQueryString() != null) {
			geoDistrictPage = geoDistrictRepository.findAllByDivision_NameIgnoreCaseContaining(pageable,
					data.getQueryString());
		} else {
			throw new BadRequestAlertException("Information incomplete", "District Service", "DistrictService");
		}
		Page<GeoDistrictDTO> publishers = paginationInfoService.getDataAsDTO(geoDistrictPage, en -> en.getDTO());

		return new GenericResponseObject<GeoDistrictDTO>(publishers.getTotalElements(), publishers, data.getPageNo(),
				data.getPageSize());

	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public List<GeoDistrictDTO> getGeoDistrictForDivision(Long divisionId) {
		return geoDistrictRepository.findAllByDivision_Id(divisionId).stream().map(geoD -> geoD.getDTO())
				.collect(Collectors.toList());
	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public List<GeoDistrictEntity> getGeoDistrictEntityForDivision(Long divisionId) {
		return geoDistrictRepository.findAllByDivision_Id(divisionId).stream().collect(Collectors.toList());
	}

}
