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

import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBType;

/**
 * @author aditya-server
 *
 *         27-Aug-2021 -- 11:53:21 pm
 */
@Repository
public interface IULBRepository extends JpaRepository<ULBEntity, Long> {
	Optional<ULBEntity> findByCode(String ulbCode);
	Optional<ULBEntity> findByCodeIgnoreCase(String ulbCode);
	Page<ULBEntity> findAllByNameIgnoreCaseContainingOrCodeIgnoreCaseContainingOrDistrict_NameIgnoreCaseContaining(
			Pageable pageable, String name, String code, String districtName);

	Page<ULBEntity> findAllByDistrict_NameIgnoreCaseContaining(Pageable pageable, String districtName);

	List<ULBEntity> findByDistrict_IdAndActiveAndType(Long id, Boolean status, ULBType ulbType);

	List<ULBEntity> findByDistrict_Id(Long id);

	List<ULBEntity> findByDistrict_Division_Id(Long id);

	List<ULBEntity> findByActive(Boolean active);

	List<ULBEntity> findByIdIn(List<Long> ids);

	Optional<ULBEntity> findByNameIgnoreCase(String name);
}
