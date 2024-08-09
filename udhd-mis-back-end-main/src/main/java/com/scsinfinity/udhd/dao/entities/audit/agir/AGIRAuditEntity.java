package com.scsinfinity.udhd.dao.entities.audit.agir;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scsinfinity.udhd.dao.entities.audit.AuditCriteriaEntity;
import com.scsinfinity.udhd.dao.entities.audit.AuditEntity;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.services.audit.agir.dto.AGIRAuditDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AGIR_AUDIT")
public class AGIRAuditEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -3988098110271214005L;

	@OneToMany(mappedBy = "agirAudit")
	private List<AuditCriteriaEntity> auditCriterias;

	@OneToOne
	private AuditEntity auditEntity;

	@JsonIgnore
	public AGIRAuditDTO getDTO() {
		return AGIRAuditDTO.builder().agirAuditId(id).auditStatus(auditEntity.getAuditStatus())
				.endDate(auditEntity.getEndDate()).startDate(auditEntity.getStartDate()).id(id)
				.description(auditEntity.getDescription())
				.stepEnum(auditEntity.getStepEnum())
				.isAssigned(auditEntity.getCurrentStepOwner()!=null?true:false)
				.ulbName(auditEntity.getUlb().getDTO().getName()).ulbId(auditEntity.getUlb().getDTO().getId())
				.title(auditEntity.getTitle()).ulb(auditEntity.getUlb().getDTO()).build();
	}

}
