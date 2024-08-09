package com.scsinfinity.udhd.dao.entities.audit;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "AUDIT_ACTIONS_STEP_ENUM_ENTITY")
public class AuditActionsStepEnumEntity extends BaseIdEntity implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -6182682007861103433L;

	@Enumerated(EnumType.STRING)
	@Column(unique=true)
	private AuditStepEnum auditStepEnum;

	@ManyToMany
	private List<AuditActionStepEnumLineItemEntity> auditActionStepEnumLineItems;

}
