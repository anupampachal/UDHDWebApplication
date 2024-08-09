package com.scsinfinity.udhd.services.audit.cag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditComplianceEntity;
import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditComplianceDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CAGComplianceDTO extends AuditComplianceDTO {


	@JsonIgnore
	public AuditComplianceEntity getEntity(AuditCriteriaEntity criteria) {
		return AuditComplianceEntity.builder().comment(getComment()).status(getStatus()).criteria(criteria).file(getFile()).build();
	}
}
