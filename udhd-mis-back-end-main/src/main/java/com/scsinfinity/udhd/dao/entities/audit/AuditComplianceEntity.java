package com.scsinfinity.udhd.dao.entities.audit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditComplianceDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "AUDIT_COMPLIANCE")
public class AuditComplianceEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 5355493263891438208L;

	@NotBlank
	@Size(min = 3, max = 2000)
	private String comment;

	@OneToOne
	private FileEntity file;

	@NotNull
	private Boolean status;

	@ManyToOne
	private AuditCriteriaEntity criteria;

	@Builder
	public AuditComplianceEntity(Long id, @NotNull Boolean status, @NotBlank @Size(min = 3, max = 2000) String comment,
			FileEntity file, AuditCriteriaEntity criteria) {
		super(id);
		this.comment = comment;
		this.status = status;
		this.file = file;
		this.criteria = criteria;
	}

	@JsonIgnore
	public AuditComplianceDTO getDTO() {
		return AuditComplianceDTO.builder().comment(comment).id(id).auditCriteriaId(criteria.getId()).file(file)
				.status(status).build();
	}

}
