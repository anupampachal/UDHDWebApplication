package com.scsinfinity.udhd.dao.repositories.deas;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.deas.TrialBalanceUploadReportSummaryEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;

@Repository
public interface ITrialBalanceUploadReportSummaryRepository
		extends JpaRepository<TrialBalanceUploadReportSummaryEntity, Long> {
	Page<TrialBalanceUploadReportSummaryEntity> findAllByUlbIdIn(List<Long> ulbs, Pageable page);

	Page<TrialBalanceUploadReportSummaryEntity> findAllByUlbId(Long UlbId, Pageable page);

	Optional<TrialBalanceUploadReportSummaryEntity> findByResponseFileId(String fileId);

	Optional<TrialBalanceUploadReportSummaryEntity> findByTrialBalanceFile_File_FileId(String fileId);
	
}
