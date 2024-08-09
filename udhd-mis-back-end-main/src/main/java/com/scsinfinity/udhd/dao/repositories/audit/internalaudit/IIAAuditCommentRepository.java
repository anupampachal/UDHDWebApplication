package com.scsinfinity.udhd.dao.repositories.audit.internalaudit;

import com.scsinfinity.udhd.dao.entities.audit.AuditCommentEntity;
import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAAuditCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IIAAuditCommentRepository extends JpaRepository<IAAuditCommentEntity, Long> {
    Page<AuditCommentEntity> findAllByCommentIgnoreCaseContaining(Pageable pageable,
                                                                                String name);

}
