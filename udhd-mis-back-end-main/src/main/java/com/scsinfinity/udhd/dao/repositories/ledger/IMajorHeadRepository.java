/**
 * 
 */
package com.scsinfinity.udhd.dao.repositories.ledger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.ledger.MajorHeadEntity;

/**
 * @author aditya-server
 *
 *         29-Aug-2021 -- 12:30:10 pm
 */
@Repository
public interface IMajorHeadRepository extends JpaRepository<MajorHeadEntity, Long> {

}
