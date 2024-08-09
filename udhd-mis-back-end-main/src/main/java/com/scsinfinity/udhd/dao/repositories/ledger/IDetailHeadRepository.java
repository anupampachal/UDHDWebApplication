/**
 * 
 */
package com.scsinfinity.udhd.dao.repositories.ledger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.ledger.DetailHeadEntity;

/**
 * @author aditya-server
 *
 *         29-Aug-2021 -- 12:32:01 pm
 */
@Repository
public interface IDetailHeadRepository extends JpaRepository<DetailHeadEntity, Long> {

}
