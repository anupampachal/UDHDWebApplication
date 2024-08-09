package com.scsinfinity.udhd.dao.repositories.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.AuditActionStepEnumLineItemEntity;

@Repository
public interface IAuditActionStepEnumLineItemRepository extends JpaRepository<AuditActionStepEnumLineItemEntity, Long> {

}
