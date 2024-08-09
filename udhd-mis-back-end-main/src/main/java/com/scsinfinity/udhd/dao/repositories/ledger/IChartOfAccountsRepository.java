/**
 * 
 */
package com.scsinfinity.udhd.dao.repositories.ledger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.ledger.ChartOfAccountsEntity;

/**
 * @author aditya-server
 *
 *         29-Aug-2021 -- 1:09:45 pm
 */
@Repository
public interface IChartOfAccountsRepository extends JpaRepository<ChartOfAccountsEntity, Long> {

}
