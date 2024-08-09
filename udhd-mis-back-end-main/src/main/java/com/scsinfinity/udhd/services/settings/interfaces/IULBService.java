package com.scsinfinity.udhd.services.settings.interfaces;

import java.util.List;
import java.util.Optional;

import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBType;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

public interface IULBService {

	/*
	 * @param id
	 * 
	 * @return
	 */
	ULBDTO findByULBId(Long id);

	ULBEntity findULBEntityById(Long id);

	/**
	 * @param data
	 * @return
	 */
	GenericResponseObject<ULBDTO> loadULBByPage(Pagination<ULBDTO> data);

	/**
	 * @param geoULBDTO
	 * @return
	 */
	ULBDTO createUpdateULB(ULBDTO geoDistrictDTO);

	/**
	 * @param id
	 */
	ULBDTO deactivate(Long id);

	/**
	 * 
	 * @param data
	 * @return GenericResponseObject<ULBDTO>
	 */
	GenericResponseObject<ULBDTO> loadULBByPageForDistrict(Pagination<ULBDTO> data);

	List<ULBDTO> getULBsForDistrictAndULBType(Long districtId, ULBType ulbType);

	List<ULBDTO> getAllActiveULBs();

	List<ULBEntity> getAllActiveULBEntities();

	Optional<ULBDTO> getULBByName(String name);

	Optional<ULBEntity> getULBEntityByName(String name);

	List<ULBEntity> getAllULBsForDistrict(Long districtId);

	List<ULBEntity> getAllULBsForDivision(Long divisionId);

	List<ULBEntity> getAllULBsForDivisionPTD(Long divisionId);

	List<ULBEntity> getAllULBsForDistrictPTD(Long districtId);
}
