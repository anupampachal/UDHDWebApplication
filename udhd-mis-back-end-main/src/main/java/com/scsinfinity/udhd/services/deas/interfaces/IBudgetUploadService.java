package com.scsinfinity.udhd.services.deas.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.BudgetUploadDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

public interface IBudgetUploadService {

	BudgetUploadDTO uploadBudgetStatement(BudgetUploadDTO budgetData);

	BudgetUploadDTO getBudgetDataById(Long id);

	GenericResponseObject<BudgetUploadDTO> getBudgetDataByPage(@RequestBody Pagination<BudgetUploadDTO> data);
	
	Resource getFile(String fileId);

	List<ULBDTO> getULBsMappedToMe();
}
