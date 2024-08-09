package com.scsinfinity.udhd.dao.repositories.deas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.DeasAnnualDataEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

@Repository
public interface IDeasAnnualDataRepository extends JpaRepository<DeasAnnualDataEntity, Long> {

	List<DeasAnnualDataEntity> findByYearStartDateAndYearEndDateAndStatusAndUlb(LocalDate startDate,
			LocalDate endDate, TypeStatusEnum status, ULBEntity ulb);
}
