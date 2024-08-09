package com.scsinfinity.udhd.dao.entities.audit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditCommentDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "AUDIT_COMMENT")
public class AuditCommentEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2379708470665930994L;

	@NotBlank
	@Size(min = 3, max = 2000)
	private String comment;

	@ManyToOne
	private AuditCriteriaEntity criteria;

	@Builder
	public AuditCommentEntity(Long id, @NotBlank @Size(min = 3, max = 2000) String comment,
			AuditCriteriaEntity criteria) {
		super(id);
		this.comment = comment;
		this.criteria = criteria;
	}

	@JsonIgnore
	public AuditCommentDTO getDTO() {
		return AuditCommentDTO.builder().auditCriteriaId(criteria.getId()).comment(comment).id(getId()).build();
	}
}
