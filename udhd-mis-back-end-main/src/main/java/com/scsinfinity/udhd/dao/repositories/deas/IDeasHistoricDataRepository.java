package com.scsinfinity.udhd.dao.repositories.deas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.DEASHistoricalDataEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

@Repository
public interface IDeasHistoricDataRepository extends JpaRepository<DEASHistoricalDataEntity, Long> {

	/*
	 * List<DEASHistoricalDataEntity> //
	 * findByStartDateAfterOrStartDateAndEndDateBeforeOrEndDateAndUlbAndStatusIn
	 * //findByEndDateBeforeOrStartDateAndStartDateAfterOrEndDateAndUlbAndStatus
	 * findByEndDateBeforeOrStartDateAndStartDateAfterOrEndDateAndUlbAndStatus
	 * (LocalDate startDate,LocalDate startDateEQ, LocalDate endDate,LocalDate
	 * endDateEq,ULBEntity ulb, TypeStatusEnum status);
	 */
	@Query(value = "select * from DEAS_HISTORICAL_DATA_ENTITY h where h.END_DATE>=?1 and h.START_DATE<=?2 and h.ulb_id=?3 and status=?4", nativeQuery = true)
	List<DEASHistoricalDataEntity> findByGivenCriteria(LocalDate startDate, LocalDate endDateEq, Long ulbId,
			int status);

	Page<DEASHistoricalDataEntity> findAllByUlb_Id(Long UlbId, Pageable page);

	Page<DEASHistoricalDataEntity> findAllByUlbIn(List<ULBEntity> ulbs, Pageable page);
	
	Optional<DEASHistoricalDataEntity> findByFile_FileId(String fileId);
}
