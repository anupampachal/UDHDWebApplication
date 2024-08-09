package com.scsinfinity.udhd.dao.repositories.deas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.BalanceEntity;

@Repository
public interface IBalanceRepository extends JpaRepository<BalanceEntity, Long>{

}
