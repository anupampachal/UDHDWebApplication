package com.scsinfinity.udhd.dao.repositories.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.AuditCommentEntity;

@Repository
public interface IAuditCommentRepository extends JpaRepository<AuditCommentEntity, Long> {
	Page<AuditCommentEntity> findAllByCriteria_IdAndCommentIgnoreCaseContaining(Pageable pageable, Long id,
			String name);

	Page<AuditCommentEntity> findAllByCriteria_Id(Pageable pageable, Long id);
}