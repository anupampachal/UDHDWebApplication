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

import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;

/**
 * @author aditya-server
 *
 *         27-Aug-2021 -- 11:52:27 pm
 */
@Repository
public interface IGeoDistrictRepository extends JpaRepository<GeoDistrictEntity, Long> {
	Optional<GeoDistrictEntity> findByName(String name);

	Optional<GeoDistrictEntity> findByNameAndDivision_Name(String name, String divisionName);
	Optional<GeoDistrictEntity> findByNameIgnoreCaseAndDivision_NameIgnoreCase(String name, String divisionName);

	Page<GeoDistrictEntity> findAllByNameIgnoreCaseContainingOrCodeIgnoreCaseContainingOrDivision_NameIgnoreCaseContaining(
			Pageable pageable, String name, String code, String divisionName);

	Page<GeoDistrictEntity> findAllByDivision_NameIgnoreCaseContaining(Pageable pageable, String divisionName);

	List<GeoDistrictEntity> findAllByDivision_Id(Long divisionId);
}
