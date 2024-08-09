package com.scsinfinity.udhd.dao.repositories.deas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.BalanceSheetEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

@Repository
public interface IBalanceSheetRepository extends JpaRepository<BalanceSheetEntity, Long> {
	Optional<BalanceSheetEntity> findByTrialBalance_Id(Long trialBalanceId);

	// Optional<BalanceSheetEntity> findDateToBeforeTopByOrderByDateToDesc(LocalDate
	// dateTo);

	// Optional<BalanceSheetEntity>
	// findTopByOrderByDateToDescAndDateToBefore(LocalDate dateTo);

	@Query(value = "select TOP 1 *  from DEAS_BALANCE_SHEET h where h.date_till<=?1 and h.ulb_id=?2 ORDER BY date_till DESC ", nativeQuery = true)
	Optional<BalanceSheetEntity> findByGivenCriteria(LocalDate dateTill, Long ulbId);

	/*
	 * @Query(value="select deas_balance_sheet.* from\n" +
	 * " deas_balance_sheet , (select id, total_assets,  total_liabilities, ulb_id , max(date_till)  as transaction_date from deas_balance_sheet where active=true and date_till<=?1) max_date  where deas_balance_sheet.id= max_date.id and deas_balance_sheet.date_till= max_date.transaction_date"
	 * , nativeQuery=true) List<BalanceSheetEntity>
	 * findLatestBalanceSheetForAllULBsTillDate(LocalDate dateTill);
	 */
	List<BalanceSheetEntity> findByActiveAndDateTillLessThanEqualAndUlbIn(Boolean active, LocalDate dateTill,
			List<ULBEntity> ulb);
}
