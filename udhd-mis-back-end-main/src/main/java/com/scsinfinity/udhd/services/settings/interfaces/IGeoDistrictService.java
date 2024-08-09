package com.scsinfinity.udhd.services.settings.interfaces;

import java.util.List;

import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.GeoDistrictDTO;

public interface IGeoDistrictService {

	/**
	 * @param id
	 * @return
	 */
	GeoDistrictDTO findByGeoDistrictId(Long id);

	/**
	 * @param data
	 * @return
	 */
	GenericResponseObject<GeoDistrictDTO> loadGeoDistrictByPage(Pagination<GeoDistrictDTO> data);

	/**
	 * @param geoDistrictDTO
	 * @return
	 */
	GeoDistrictDTO createUpdateGeoDistrict(GeoDistrictDTO geoDistrictDTO);

	/**
	 * @param id
	 */
	GeoDistrictDTO deactivate(Long id);

	/**
	 * 
	 * @param data
	 * @return GenericResponseObject<GeoDistrictDTO>
	 */
	GenericResponseObject<GeoDistrictDTO> loadGeoDistrictByPageForDivision(Pagination<GeoDistrictDTO> data);

	List<GeoDistrictDTO> getGeoDistrictForDivision(Long divisionId);

	List<GeoDistrictEntity> getGeoDistrictEntityForDivision(Long divisionId);

}
