package com.scsinfinity.udhd.dao.repositories.audit;

import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.AuditEntity;

import java.util.List;

@Repository
public interface IAuditRepository extends JpaRepository<AuditEntity, Long> {
    List<AuditEntity> findByUlbIn(List<ULBEntity>ulbs);
}
