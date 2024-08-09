package com.scsinfinity.udhd.dao.repositories.deas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.BankAndCashBalanceEntity;

@Repository
public interface IBankAndCashBalanceRepository extends JpaRepository<BankAndCashBalanceEntity, Long> {

	List<BankAndCashBalanceEntity> findByTrialBalance_Id(Long tbId);
}
