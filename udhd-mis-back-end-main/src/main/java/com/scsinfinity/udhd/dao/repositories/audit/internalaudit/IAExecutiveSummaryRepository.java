package com.scsinfinity.udhd.dao.repositories.audit.internalaudit;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.IAExecutiveSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAExecutiveSummaryRepository extends JpaRepository<IAExecutiveSummaryEntity, Long> {
}
