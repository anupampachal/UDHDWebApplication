package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IARevenueNCapitalReceiptDTO;

public interface IReceiptsAndCapitalReceiptsService {

	IARevenueNCapitalReceiptDTO createUpdateReceiptsAndCapitalInformation(IARevenueNCapitalReceiptDTO dto);

	IARevenueNCapitalReceiptDTO findReceiptsAndCapitalInformationByIaId(Long iaId);
}
