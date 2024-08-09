package com.scsinfinity.udhd.services.deas.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.FixedAssetsRegisterDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

public interface IFixedAssetsRegisterService {

	FixedAssetsRegisterDTO uploadFixedAssetsRegisterStatement(FixedAssetsRegisterDTO budgetData);

	FixedAssetsRegisterDTO getFixedAssetsRegisterDataById(Long id);

	GenericResponseObject<FixedAssetsRegisterDTO> getFixedAssetsRegisterDataByPage(
			@RequestBody Pagination<FixedAssetsRegisterDTO> data);

	Resource getFile(String fileId);

	List<ULBDTO> getULBsMappedToMe();
}
