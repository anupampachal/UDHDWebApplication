/**
 * 
 */
package com.scsinfinity.udhd.services.settings;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;
import com.scsinfinity.udhd.dao.repositories.geography.IGeoDivisionRepository;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IGeoDivisionService;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author aditya-server
 *
 *         03-Sep-2021 -- 7:28:40 pm
 */
@Slf4j
@Service
@AllArgsConstructor
@CacheConfig(cacheNames = { "division" })
public class GeoDivisionService implements IGeoDivisionService {

	private final IGeoDivisionRepository geoDivisionRepository;
	private final IPaginationInfoService<GeoDivisionDTO, GeoDivisionEntity> paginationInfoService;

	@Override
	@Cacheable(key = "#id")
	public GeoDivisionDTO findByGeoDivisionId(Long id) {
		log.info("findByGeoDivisionId");
		return geoDivisionRepository.findById(id).map(res -> res.getDTO())
				.orElseThrow(() -> new EntityNotFoundException("Division with id " + id));
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<GeoDivisionDTO> loadGeoDivisionByPage(Pagination<GeoDivisionDTO> data) {
		log.info("loadGeoDivisionByPage", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);
		Page<GeoDivisionEntity> geoDivisionPage = null;
		if (data.getQueryString() != null) {
			geoDivisionPage = geoDivisionRepository.findAllByNameIgnoreCaseContainingOrCodeIgnoreCaseContaining(
					pageable, data.getQueryString(), data.getQueryString());
		} else {
			geoDivisionPage = geoDivisionRepository.findAll(pageable);
		}
		Page<GeoDivisionDTO> divisionsDtoPage = paginationInfoService.getDataAsDTO(geoDivisionPage, en -> en.getDTO());

		return new GenericResponseObject<GeoDivisionDTO>(divisionsDtoPage.getTotalElements(), divisionsDtoPage,
				data.getPageNo(), data.getPageSize());

	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public GeoDivisionDTO createGeoDivision(GeoDivisionDTO geoDivisionDTO) {
		GeoDivisionDTO data = geoDivisionRepository.save(geoDivisionDTO.getEntity()).getDTO();
		return data;
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public GeoDivisionDTO updateGeoDivision(GeoDivisionDTO geoDivisionDTO) {
		return geoDivisionRepository.saveAndFlush(geoDivisionDTO.getEntity()).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public GeoDivisionDTO deactivate(Long id) {
		GeoDivisionEntity geoDivision = geoDivisionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Division with id " + id));
		geoDivision.setActive(false);
		return geoDivisionRepository.save(geoDivision).getDTO();
	}

	@Override

	public List<GeoDivisionDTO> getAllActiveGeoDivision() {
		return geoDivisionRepository.findAll().stream().filter(geoDiv -> geoDiv.getActive() == true)
				.map(geoDiv -> geoDiv.getDTO()).collect(Collectors.toList());
	}


	public List<GeoDivisionEntity> getAllActiveGeoDivisionEntity() {
		return geoDivisionRepository.findAll().stream().filter(geoDiv -> geoDiv.getActive() == true)
				.collect(Collectors.toList());
	}

}
