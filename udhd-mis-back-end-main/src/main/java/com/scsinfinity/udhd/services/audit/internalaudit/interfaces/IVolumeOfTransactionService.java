package com.scsinfinity.udhd.services.audit.internalaudit.interfaces;

import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAVolumeOfTransactionsDTO;

@Service
public interface IVolumeOfTransactionService {
	IAVolumeOfTransactionsDTO createUpdateVolumeOfTransaction(IAVolumeOfTransactionsDTO dto);

	IAVolumeOfTransactionsDTO findVolumeOfTransactionByIaId(Long iaId);

}
