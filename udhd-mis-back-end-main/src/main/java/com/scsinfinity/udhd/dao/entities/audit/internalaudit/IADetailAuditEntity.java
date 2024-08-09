package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IA_DETAIL_AUDIT_INFO")
public class IADetailAuditEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3176347816260066223L;
	
	@OneToOne
	private InternalAuditEntity internalAudit;

	@OneToMany
	private List<IAStatusOfAuditObservationEntity> auditStatuses;

	@OneToMany
	private List<IADetailAuditParaInfoEntity> auditParaInfos;
}
