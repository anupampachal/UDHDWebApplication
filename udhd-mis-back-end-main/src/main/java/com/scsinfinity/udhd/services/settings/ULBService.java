package com.scsinfinity.udhd.services.settings;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBType;
import com.scsinfinity.udhd.dao.repositories.geography.IULBRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.common.AuthorityConstants;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ULBService implements IULBService {

	private final IULBRepository ulbRepository;
	private final IPaginationInfoService<ULBDTO, ULBEntity> paginationInfoService;

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public ULBDTO findByULBId(Long id) {
		log.info("findByULBId");
		return ulbRepository.findById(id).map(res -> res.getDTO())
				.orElseThrow(() -> new EntityNotFoundException("findByULBId  " + id));
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<ULBDTO> loadULBByPage(Pagination<ULBDTO> data) {
		log.info("loadULBByPage", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		Page<ULBEntity> ulbPage = null;
		if (data.getQueryString() != null) {
			ulbPage = ulbRepository
					.findAllByNameIgnoreCaseContainingOrCodeIgnoreCaseContainingOrDistrict_NameIgnoreCaseContaining(
							pageable, data.getQueryString(), data.getQueryString(), data.getQueryString());
		} else {
			ulbPage = ulbRepository.findAll(pageable);
		}
		Page<ULBDTO> publishers = paginationInfoService.getDataAsDTO(ulbPage, en -> en.getDTO());

		return new GenericResponseObject<ULBDTO>(publishers.getTotalElements(), publishers, data.getPageNo(),
				data.getPageSize());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public ULBDTO createUpdateULB(ULBDTO ulbDto) {
		log.debug("createUpdateULB", ulbDto);
		return ulbRepository.save(ulbDto.getEntity()).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_SLPMU_IT + "','" + AuthorityConstants.ROLE_UDHD_IT
			+ "')")
	public ULBDTO deactivate(Long id) {
		log.debug("deactivate", id);
		ULBEntity ulb = ulbRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ULB with id " + id));
		ulb.setActive(false);
		return ulbRepository.save(ulb).getDTO();
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public GenericResponseObject<ULBDTO> loadULBByPageForDistrict(Pagination<ULBDTO> data) {
		log.info("loadULBByPageForDistrict", data);
		Pageable pageable = paginationInfoService.getPaginationRequestInfo(data);

		Page<ULBEntity> ulbPage = null;
		if (data.getQueryString() != null) {
			ulbPage = ulbRepository.findAllByDistrict_NameIgnoreCaseContaining(pageable, data.getQueryString());
		} else {
			throw new BadRequestAlertException("Information incomplete", "ULBService", "ULBService");
		}
		Page<ULBDTO> publishers = paginationInfoService.getDataAsDTO(ulbPage, en -> en.getDTO());

		return new GenericResponseObject<ULBDTO>(publishers.getTotalElements(), publishers, data.getPageNo(),
				data.getPageSize());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public List<ULBDTO> getAllActiveULBs() {
		return ulbRepository.findByActive(true).stream().map(ulb -> ulb.getDTO()).collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public List<ULBDTO> getULBsForDistrictAndULBType(Long districtId, ULBType ulbType) {
		return ulbRepository.findByDistrict_IdAndActiveAndType(districtId, true, ulbType).stream()
				.map(ulb -> ulb.getDTO()).collect(Collectors.toList());
	}

	@Override
	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	public ULBEntity findULBEntityById(Long id) {
		return ulbRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("findByULBId  " + id));
	}

	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	public Optional<ULBDTO> getULBByName(String name) {
		Optional<ULBEntity> ulbo = ulbRepository.findByNameIgnoreCase(name);

		if (ulbo.isPresent()) {
			ULBEntity ulb = ulbo.get();
			return Optional.of(ULBDTO.builder().aliasName(ulb.getAliasName()).id(ulb.getId()).code(ulb.getCode())
					.name(ulb.getName()).aliasName(ulb.getAliasName()).build());
		}
		return Optional.ofNullable(null);
	}

	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	@Override
	public Optional<ULBEntity> getULBEntityByName(String name) {
		return ulbRepository.findByNameIgnoreCase(name);
	}

	/*@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	@Override
	public List<ULBEntity> getAllULBsForDistrict(Long districtId) {
		return ulbRepository.findByDistrict_Id(districtId);
	}
/*
	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")*/
	@Override
	public List<ULBEntity> getAllULBsForDivision(Long divisionId) {
		return ulbRepository.findByDistrict_Division_Id(divisionId);
	}
	@Override
	public List<ULBEntity> getAllULBsForDivisionPTD(Long divisionId) {
		return ulbRepository.findByDistrict_Division_Id(divisionId);
	}
	
	@Override
	public List<ULBEntity> getAllULBsForDistrictPTD(Long districtId) {
		return ulbRepository.findByDistrict_Id(districtId);
	}

	@PreAuthorize("hasAnyAuthority('" + AuthorityConstants.ROLE_FLIA + "','" + AuthorityConstants.ROLE_INTERNAL_AUDITOR
			+ "','" + AuthorityConstants.ROLE_ULB_ACCOUNTANT + "','" + AuthorityConstants.ROLE_ULB_CMO + "','"
			+ AuthorityConstants.ROLE_SLPMU_ACCOUNT + "','" + AuthorityConstants.ROLE_SLPMU_ADMIN + "','"
			+ AuthorityConstants.ROLE_SLPMU_AUDIT + "','" + AuthorityConstants.ROLE_SLPMU_IT + "','"
			+ AuthorityConstants.ROLE_SLPMU_UC + "','" + AuthorityConstants.ROLE_UDHD_IT + "','"
			+ AuthorityConstants.ROLE_UDHD_PSEC + "','" + AuthorityConstants.ROLE_UDHD_SEC + "','"
			+ AuthorityConstants.ROLE_UDHD_SO + "')")
	@Override
	public List<ULBEntity> getAllActiveULBEntities() {
		return ulbRepository.findByActive(true).stream().collect(Collectors.toList());
	}

}
