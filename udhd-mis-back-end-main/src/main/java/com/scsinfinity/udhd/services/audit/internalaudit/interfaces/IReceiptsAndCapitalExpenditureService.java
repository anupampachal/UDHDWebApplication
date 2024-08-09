package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalExpenditureDTO;

public interface IReceiptsAndCapitalExpenditureService {
	IARevenueNCapitalExpenditureDTO createUpdateReceiptsAndCapitalExpenditureInformation(
			IARevenueNCapitalExpenditureDTO dto);

	IARevenueNCapitalExpenditureDTO findReceiptsAndCapitalExpenditureInformationByIaId(Long iaId);

}
