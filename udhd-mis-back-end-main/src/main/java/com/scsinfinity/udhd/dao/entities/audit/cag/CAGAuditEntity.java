package com.scsinfinity.udhd.dao.entities.audit.cag;

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
import com.scsinfinity.udhd.services.audit.cag.dto.CAGAuditDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CAG_AUDIT")
public class CAGAuditEntity extends BaseIdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518630153931583993L;

	@OneToMany(mappedBy = "cagAudit")
	private List<AuditCriteriaEntity> auditCriterias;

	@OneToOne
	private AuditEntity auditEntity;

	@JsonIgnore
	public CAGAuditDTO getDTO() {
		return CAGAuditDTO.builder().cagAuditId(id).auditStatus(auditEntity.getAuditStatus())
				.endDate(auditEntity.getEndDate()).startDate(auditEntity.getStartDate()).id(id)
				.isAssigned(auditEntity.getCurrentStepOwner()!=null?true:false)
				.description(auditEntity.getDescription()).ulbName(auditEntity.getUlb().getDTO().getName())
				.ulbId(auditEntity.getUlb().getDTO().getId()).title(auditEntity.getTitle())
				.ulb(auditEntity.getUlb().getDTO()).build();
	}

}
