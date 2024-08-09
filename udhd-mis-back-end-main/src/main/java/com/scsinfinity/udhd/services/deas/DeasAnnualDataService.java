package com.scsinfinity.udhd.services.deas;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.configurations.security.SecuredUserInfoService;
import com.scsinfinity.udhd.dao.entities.deas.AnnualFinanceStatementEntity;
import com.scsinfinity.udhd.dao.entities.deas.DeasAnnualDataEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.repositories.deas.IDeasAnnualDataRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.common.Pagination;
import com.scsinfinity.udhd.services.deas.dto.DeasAnnualDataDTO;
import com.scsinfinity.udhd.services.deas.interfaces.IDeasAnnualDataService;
import com.scsinfinity.udhd.services.settings.interfaces.IPaginationInfoService;
import com.scsinfinity.udhd.services.settings.interfaces.IULBService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DeasAnnualDataService implements IDeasAnnualDataService {

	private final IDeasAnnualDataRepository deasAnnualRepository;
	private final SecuredUserInfoService securedUser;
	private final IPaginationInfoService<DeasAnnualDataDTO, DeasAnnualDataEntity> paginationInfoService;
	private final IULBService ulbService;

	@Override
	public DeasAnnualDataDTO getDeasAnnualDataDTOByInfo(LocalDate startDate, LocalDate endDate, TypeStatusEnum status,
			ULBEntity ulb) {
		return getDeasAnnualDataDTOByInfoEntity(startDate, endDate, status, ulb).getDTO();
	}

	@Override
	public DeasAnnualDataEntity getDeasAnnualDataDTOByInfoEntity(LocalDate startDate, LocalDate endDate,
			TypeStatusEnum status, ULBEntity ulb) {
		List<DeasAnnualDataEntity> deasDataList = deasAnnualRepository
				.findByYearStartDateAndYearEndDateAndStatusAndUlb(startDate, endDate, status, null);
		if (deasDataList.size() > 1)
			throw new BadRequestAlertException("INVALID_DATA", "DATA_DUPLICACY_AT_DEASANNUALDATAENTITY",
					"DATA_DUPLICACY_AT_DEASANNUALDATAENTITY");

		else if (deasDataList.size() == 1)
			return deasDataList.get(0);
		return null;
	}

	@Override
	public Page<DeasAnnualDataDTO> getDeasAnnualDataByPage(Pagination<DeasAnnualDataDTO> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeasAnnualDataEntity updateDeasAnnualDataForAnnualFinanceStatementUpload(
			AnnualFinanceStatementEntity financeStatement) {
		// TODO Auto-generated method stub
		return null;
	}

}
