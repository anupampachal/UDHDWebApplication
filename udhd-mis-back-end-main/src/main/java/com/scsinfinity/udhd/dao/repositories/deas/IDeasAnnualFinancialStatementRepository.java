package com.scsinfinity.udhd.dao.repositories.deas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.AnnualFinanceStatementEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

@Repository
public interface IDeasAnnualFinancialStatementRepository extends JpaRepository<AnnualFinanceStatementEntity, Long> {
	
	@Query(value = "select * from DEAS_ANNUAL_FINANCE_STATEMENT h where h.YEAR_END_DATE>=?1 and h.YEAR_START_DATE<=?2 and h.ulb_id=?3 and status=?4", nativeQuery = true)
	List<AnnualFinanceStatementEntity> findByGivenCriteria(LocalDate startDate, LocalDate endDateEq, Long ulbId,
			int status);
	
	Page<AnnualFinanceStatementEntity> findAllByUlb_Id(Long UlbId, Pageable page);

	Page<AnnualFinanceStatementEntity> findAllByUlbIn(List<ULBEntity> ulbs, Pageable page);

	Optional<AnnualFinanceStatementEntity> findByFile_FileId(String fileId);
}
