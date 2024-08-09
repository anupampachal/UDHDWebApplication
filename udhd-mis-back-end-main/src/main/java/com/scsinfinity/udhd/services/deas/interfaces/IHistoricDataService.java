package com.scsinfinity.udhd.services.deas.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.HistoricDataDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

public interface IHistoricDataService {
	HistoricDataDTO uploadHistoricData(HistoricDataDTO historicDataDTO);

	HistoricDataDTO getHistoricDataById(Long id);

	GenericResponseObject<HistoricDataDTO> getHistoricDataByPage(@RequestBody Pagination<HistoricDataDTO> data);

	Resource getFile(String fileId);

	List<ULBDTO> getULBsMappedToMe();

}
