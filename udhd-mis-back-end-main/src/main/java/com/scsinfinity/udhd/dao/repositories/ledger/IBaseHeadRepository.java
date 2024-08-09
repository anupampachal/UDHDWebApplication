/**
 * 
 */
package com.scsinfinity.udhd.dao.repositories.ledger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.ledger.BaseHeadEntity;

/**
 * @author aditya-server
 *
 * 29-Aug-2021  --  11:25:31 am
 */
@Repository
public interface IBaseHeadRepository extends JpaRepository<BaseHeadEntity, Long>{

}
