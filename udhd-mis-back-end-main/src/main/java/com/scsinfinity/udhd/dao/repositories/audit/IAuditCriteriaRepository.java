package com.scsinfinity.udhd.dao.repositories.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;

@Repository
public interface IAuditCriteriaRepository extends JpaRepository<AuditCriteriaEntity, Long> {
	Page<AuditCriteriaEntity> findAllByAgirAudit_IdAndTitleIgnoreCaseContaining(Pageable pageable, Long id,String title);
	Page<AuditCriteriaEntity> findAllByAgirAudit_Id(Pageable pageable, Long id);

	Page<AuditCriteriaEntity> findAllByCagAudit_IdAndTitleIgnoreCaseContaining(Pageable pageable, Long id, String title);
	Page<AuditCriteriaEntity> findAllByCagAudit_Id(Pageable pageable, Long id);
}
