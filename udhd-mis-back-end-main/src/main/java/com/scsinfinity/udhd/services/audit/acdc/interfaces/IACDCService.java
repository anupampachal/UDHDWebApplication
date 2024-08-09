package com.scsinfinity.udhd.services.audit.acdc.interfaces;

import com.scsinfinity.udhd.services.audit.acdc.dto.ACDCULBBasedDTO;
import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;

public interface IACDCService {

	/**
	 * @param id
	 * @return
	 */
	ACDCULBBasedDTO findById(Long id);

	/**
	 * @param data
	 * @return
	 */
	GenericResponseObject<ACDCULBBasedDTO> loadACDCByPage(Pagination<ACDCULBBasedDTO> data);


	/**
	 * @param ACDCULBBasedDTO
	 * @return
	 */
	ACDCULBBasedDTO createUpdateACDC(ACDCULBBasedDTO acDC);

	/**
	 * @param id
	 */
	ACDCULBBasedDTO deactivate(Long id);

}
