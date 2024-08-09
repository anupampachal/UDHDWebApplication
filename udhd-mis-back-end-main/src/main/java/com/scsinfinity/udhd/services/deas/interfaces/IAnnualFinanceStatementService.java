package com.scsinfinity.udhd.services.deas.interfaces;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestBody;

import com.scsinfinity.udhd.services.common.GenericResponseObject;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.AnnualFinanceStatementDTO;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

public interface IAnnualFinanceStatementService {

	AnnualFinanceStatementDTO uploadAnnualFinanceStatement(AnnualFinanceStatementDTO annualData);

	AnnualFinanceStatementDTO getAnnualFinanceStatementById(Long id);

	GenericResponseObject<AnnualFinanceStatementDTO> getAnnualFinanceStatementByPage(
			@RequestBody Pagination<AnnualFinanceStatementDTO> data);

	Resource getFile(String fileId);

	List<ULBDTO> getULBsMappedToMe();
}
