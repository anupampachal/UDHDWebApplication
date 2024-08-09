/**
* 
*/
package com.scsinfinity.udhd.dao.repositories.ledger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.ledger.MinorHeadEntity;

/**
 * @author aditya-server
 *
 *         29-Aug-2021 -- 12:31:09 pm
 */

@Repository
public interface IMinorHeadRepository extends JpaRepository<MinorHeadEntity, Long> {

}
