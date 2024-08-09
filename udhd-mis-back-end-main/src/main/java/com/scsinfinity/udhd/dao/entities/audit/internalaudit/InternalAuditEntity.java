package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.dao.entities.audit.AuditEntity;
import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "INTERNAL_AUDIT")
public class InternalAuditEntity extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6402032637303740000L;

	@NotNull
	@OneToOne
	private AuditEntity auditEntity;

	@OneToOne(mappedBy = "internalAudit")
	private IAExecutiveSummaryEntity executiveSummary;

	@OneToOne(mappedBy = "internalAudit")
	private IAAdministrationEntity administration;

	@OneToOne(mappedBy = "internalAudit")
	private IADetailAuditEntity details;

	@OneToOne(mappedBy = "internalAudit")
	private IAFinanceEntity finance;

	@OneToOne(mappedBy = "internalAudit")
	private IAAuditObservationEntity auditObservation;

	@OneToMany
	private List<IAAuditCommentEntity> iaAuditCommentEntityList;
	
}
