package com.scsinfinity.udhd.dao.repositories.deas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.LedgerTransactionEntity;
import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceFileInfoEntity;

@Repository
public interface ILedgerTransactionRepository extends JpaRepository<LedgerTransactionEntity, Long> {
	List<LedgerTransactionEntity> findByTrialBalance_Id(Long trialBalanceId);

	List<LedgerTransactionEntity> findByTrialBalance_InAndBaseHead_Code(List<TrialBalanceFileInfoEntity> files,
			Integer code);
}
