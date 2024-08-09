package com.scsinfinity.udhd.services.audit.cag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditCommentEntity;
import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditCommentDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CAGCommentDTO extends AuditCommentDTO {

	@JsonIgnore
	public AuditCommentEntity getEntity(AuditCriteriaEntity criteria) {
		return AuditCommentEntity.builder().comment(getComment()).criteria(criteria).id(getId()).build();
	}
}
