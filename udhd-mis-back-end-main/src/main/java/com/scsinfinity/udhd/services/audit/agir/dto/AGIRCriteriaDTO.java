package com.scsinfinity.udhd.services.audit.agir.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;
import com.scsinfinity.udhd.dao.entities.audit.agir.AGIRAuditEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditCriteriaDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class AGIRCriteriaDTO extends AuditCriteriaDTO {

	private Long agirAuditId;

	@JsonIgnore
	public AGIRCriteriaDTO getDTO(AuditCriteriaDTO criteriaDTO, AGIRAuditEntity agAudit) {
		return AGIRCriteriaDTO.builder().id(criteriaDTO.getId()).agirAuditId(agAudit.getId())
				.amount(criteriaDTO.getAmount()).associatedRisk(criteriaDTO.getAssociatedRisk())
				.auditPara(criteriaDTO.getAuditPara()).description(criteriaDTO.getDescription())
				.status(criteriaDTO.getStatus()).title(criteriaDTO.getTitle()).build();
	}
	@JsonIgnore
	public AuditCriteriaDTO getAuditCriteriaDTO(AGIRCriteriaDTO criteriaDTO, AGIRAuditEntity agAudit) {
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

	@JsonIgnore
	public AuditCriteriaEntity getAuditCriteriaEntity(AGIRCriteriaDTO criteriaDTO, AGIRAuditEntity agAudit) {
		return AuditCriteriaEntity.builder()
				.id(criteriaDTO.getId())
				.agirAudit(agAudit)
				.amount(criteriaDTO.getAmount())
				.associatedRisk(criteriaDTO.getAssociatedRisk())
				.auditPara(criteriaDTO.getAuditPara())
				.description(criteriaDTO.getDescription())
				.status(criteriaDTO.getStatus())
				.title(criteriaDTO.getTitle())
				.build();
	}
}
