package com.scsinfinity.udhd.services.deas.interfaces;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import com.scsinfinity.udhd.dao.entities.deas.AnnualFinanceStatementEntity;
import com.scsinfinity.udhd.dao.entities.deas.DeasAnnualDataEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.DeasAnnualDataDTO;

public interface IDeasAnnualDataService {

	DeasAnnualDataDTO getDeasAnnualDataDTOByInfo(LocalDate startDate, LocalDate endDate, TypeStatusEnum status,
			ULBEntity ulb);

	DeasAnnualDataEntity getDeasAnnualDataDTOByInfoEntity(LocalDate startDate, LocalDate endDate, TypeStatusEnum status,
			ULBEntity ulb);

	Page<DeasAnnualDataDTO> getDeasAnnualDataByPage(@RequestBody Pagination<DeasAnnualDataDTO> data);

	DeasAnnualDataEntity updateDeasAnnualDataForAnnualFinanceStatementUpload(
			AnnualFinanceStatementEntity financeStatement);
}
