package com.scsinfinity.udhd.dao.repositories.deas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceFileInfoEntity;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

@Repository
public interface ITrialBalanceFileInfoRepository extends JpaRepository<TrialBalanceFileInfoEntity, Long> {

	List<TrialBalanceFileInfoEntity> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<TrialBalanceFileInfoEntity> findByUlb_IdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatus(
			Long ulbId, LocalDate startDate, LocalDate endDateEq, TypeStatusEnum status);

	List<TrialBalanceFileInfoEntity> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatus(
			LocalDate startDate, LocalDate endDateEq, TypeStatusEnum status);

	List<TrialBalanceFileInfoEntity> findByStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatusAndUlbIn(
			LocalDate startDate, LocalDate endDateEq, TypeStatusEnum status, List<ULBEntity> ulbs);

	List<TrialBalanceFileInfoEntity> findByUlb_IdAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndStatus(
			Long ulbId, LocalDate startDate, LocalDate endDateEq, TypeStatusEnum status);

}
