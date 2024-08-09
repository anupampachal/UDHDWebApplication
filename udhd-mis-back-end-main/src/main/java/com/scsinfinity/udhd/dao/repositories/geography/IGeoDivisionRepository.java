/**
 * 
 */
package com.scsinfinity.udhd.dao.repositories.geography;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;

/**
 * @author aditya-server
 *
 * 27-Aug-2021  --  11:51:35 pm
 */
@Repository
public interface IGeoDivisionRepository extends JpaRepository<GeoDivisionEntity, Long>{
	Optional<GeoDivisionEntity> findByName(String name);
	Optional<GeoDivisionEntity> findByNameIgnoreCase(String name);
	Optional<GeoDivisionEntity> findByNameIgnoreCaseContainingAndCodeIgnoreCaseContaining(String name, String code);
	List<GeoDivisionEntity> findAllByNameIgnoreCaseContainingOrCodeIgnoreCaseContaining(String name, String code);
	
	Page<GeoDivisionEntity> findAllByNameIgnoreCaseContainingOrCodeIgnoreCaseContaining(Pageable pageable, String name, String code);
}
