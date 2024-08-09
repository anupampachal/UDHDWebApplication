package com.scsinfinity.udhd.dao.repositories.audit.cag;

import java.time.LocalDate;
import java.util.List;

import com.scsinfinity.udhd.dao.entities.audit.AuditEntity;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.dao.entities.audit.agir.AGIRAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.UserProfileEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scsinfinity.udhd.dao.entities.audit.cag.CAGAuditEntity;

@Repository
public interface ICAGAuditRepository extends JpaRepository<CAGAuditEntity, Long> {
	Page<CAGAuditEntity> findByAuditEntity_UlbIn(Pageable pageable,List<ULBEntity>ulbs);
	Page<CAGAuditEntity> findByAuditEntity_UlbInOrAuditEntityIn(
			Pageable pageable, List<ULBEntity> ulbs, List<AuditEntity>audits);
	Page<CAGAuditEntity> findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContaining(
			Pageable pageable, String ulbName, String title);

	List<CAGAuditEntity> findByAuditEntity_StartDateGreaterThanEqualAndAuditEntity_EndDateLessThanEqual(
			LocalDate startDate, LocalDate endDateEq);




	Page<CAGAuditEntity> findAllByAuditEntity_Ulb_NameIgnoreCaseContainingOrAuditEntity_TitleIgnoreCaseContainingAndAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(
			Pageable pageable, String ulbName, String title, AuditStatusEnum statusEnum, UserProfileEntity upe);

	Page<CAGAuditEntity> findAllByAuditEntity_AuditStatusAndAuditEntity_CurrentStepOwner(
			Pageable pageable,AuditStatusEnum statusEnum, UserProfileEntity upe);


}
