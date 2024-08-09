/**
 * 
 */
package com.scsinfinity.udhd.services.settings.interfaces;

import java.util.List;

import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.settings.dto.GeoDivisionDTO;

/**
 * @author aditya-server
 *
 *         03-Sep-2021 -- 6:51:41 pm
 */

public interface IGeoDivisionService {

	/**
	 * @param id
	 * @return
	 */
	GeoDivisionDTO findByGeoDivisionId(Long id);

	/**
	 * @param data
	 * @return
	 */
	GenericResponseObject<GeoDivisionDTO> loadGeoDivisionByPage(Pagination<GeoDivisionDTO> data);


	/**
	 * @param id
	 */
	GeoDivisionDTO deactivate(Long id);

	List<GeoDivisionDTO> getAllActiveGeoDivision();
	
	List<GeoDivisionEntity> getAllActiveGeoDivisionEntity();

	GeoDivisionDTO createGeoDivision(GeoDivisionDTO geoDivisionDTO);

	GeoDivisionDTO updateGeoDivision(GeoDivisionDTO geoDivisionDTO);

}
