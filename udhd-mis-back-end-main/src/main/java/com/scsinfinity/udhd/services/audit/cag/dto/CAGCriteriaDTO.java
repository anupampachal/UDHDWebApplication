package com.scsinfinity.udhd.services.audit.cag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;

import com.scsinfinity.udhd.dao.entities.audit.cag.CAGAuditEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditCriteriaDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CAGCriteriaDTO extends AuditCriteriaDTO {

	private Long cagAuditId;

	@JsonIgnore
	public CAGCriteriaDTO getDTO(AuditCriteriaDTO criteriaDTO, CAGAuditEntity agAudit) {
		return CAGCriteriaDTO.builder().id(criteriaDTO.getId()).cagAuditId(agAudit.getId())
				.amount(criteriaDTO.getAmount()).associatedRisk(criteriaDTO.getAssociatedRisk())
				.auditPara(criteriaDTO.getAuditPara()).description(criteriaDTO.getDescription())
				.status(criteriaDTO.getStatus()).title(criteriaDTO.getTitle()).build();
	}

	@JsonIgnore
	public AuditCriteriaEntity getAuditCriteriaEntity(CAGCriteriaDTO criteriaDTO, CAGAuditEntity agAudit) {
		return AuditCriteriaEntity
				.builder()
				.id(criteriaDTO.getId())
				.cagAudit(agAudit)
				.amount(criteriaDTO.getAmount())
				.associatedRisk(criteriaDTO.getAssociatedRisk())
				.auditPara(criteriaDTO.getAuditPara())
				.description(criteriaDTO.getDescription())
				.status(criteriaDTO.getStatus())
				.title(criteriaDTO.getTitle())
				.build();
	}

	@JsonIgnore
	public AuditCriteriaDTO getAuditCriteriaDTO(CAGCriteriaDTO criteriaDTO, CAGAuditEntity cagAudit){
		return AuditCriteriaDTO.builder()
				.id(criteriaDTO.getId())
				.amount(criteriaDTO.getAmount())
				.associatedRisk(criteriaDTO.getAssociatedRisk())
				.auditPara(criteriaDTO.getAuditPara())
				.description(criteriaDTO.getDescription())
				.status(criteriaDTO.getStatus())
				.title(criteriaDTO.getTitle())
				.build();
	}
}
