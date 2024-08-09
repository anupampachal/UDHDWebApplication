package com.scsinfinity.udhd.dao.repositories.audit.internalaudit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.agir.AGIRAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.internalaudit.InternalAuditEntity;

@Repository
public interface IInternalAuditRepository extends JpaRepository<InternalAuditEntity, Long> {

	Optional<InternalAuditEntity> findByAuditEntity_Id(
			 Long id);

	Page<InternalAuditEntity> findByAuditEntity_UlbIn(Pageable pageable, List<ULBEntity>ulbs);
	List<InternalAuditEntity> findByAuditEntity_StartDateGreaterThanEqualAndAuditEntity_EndDateLessThanEqual(
			LocalDate startDate, LocalDate endDateEq);

	Page<InternalAuditEntity> findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContainingAndAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(
			Pageable pageable, String ulbName, String title, AuditStatusEnum statusEnum, UserProfileEntity upe);

	Page<InternalAuditEntity> findAllByAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(
			Pageable pageable,AuditStatusEnum statusEnum, UserProfileEntity upe);
}
