package com.scsinfinity.udhd.dao.entities.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.agir.AGIRAuditEntity;
import com.scsinfinity.udhd.dao.entities.audit.cag.CAGAuditEntity;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.audit.dto.AuditCriteriaDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "AGIR_AUDIT_CRITERIA")
public class AuditCriteriaEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 5854776796426918591L;

	@NotBlank
	@Size(min = 3, max = 512)
	private String title;

	@NotBlank
	@Size(min = 3, max = 2000)
	private String description;

	@NotBlank
	@Size(min = 3, max = 255)
	private String auditPara;

	@NotNull
	private Boolean status;

	@NotBlank
	@Size(min = 3, max = 255)
	private String associatedRisk;

	@NotNull
	private BigDecimal amount;

	@ManyToOne
	private AGIRAuditEntity agirAudit;

	@ManyToOne
	private CAGAuditEntity cagAudit;

	@OneToMany(mappedBy = "criteria")
	private List<AuditComplianceEntity> compliances;

	@OneToMany(mappedBy = "criteria")
	private List<AuditCommentEntity> comment;

	@JsonIgnore
	public AuditCriteriaDTO getDTO(String type) {
		return AuditCriteriaDTO.builder().amount(amount).associatedRisk(associatedRisk).auditPara(auditPara)
				.typeAuditId(getTypeId(type)).description(description).id(id).status(status).title(title).build();
	}

	private Long getTypeId(String type) {
		switch (type) {
		case "AGIR": {
			return agirAudit.getId();
		}
		case "CAG": {
			return cagAudit.getId();
		}
		case "FINANCE": {

		}
		}
		return null;
	}

}
