package com.scsinfinity.udhd.dao.repositories.audit.acdc;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.acdc.ULBWiseACDCStatusEntity;

@Repository
public interface IULBWiseACDCStatusRepository extends JpaRepository<ULBWiseACDCStatusEntity, Long> {

	Optional<ULBWiseACDCStatusEntity> findByTreasuryName(String name);

	Page<ULBWiseACDCStatusEntity> findAllByTreasuryNameIgnoreCaseContainingOrUlb_NameIgnoreCaseContaining(Pageable pageable,
			String name, String ulbName);

	Page<ULBWiseACDCStatusEntity> findAllByUlb_Id(Pageable pageable, Long ulbId);

	Page<ULBWiseACDCStatusEntity> findAllByTreasuryNameIgnoreCaseContainingAndUlb_Id(Pageable pageable, String name,
			Long ulbId);
}
