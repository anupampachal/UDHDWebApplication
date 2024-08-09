package com.scsinfinity.udhd.dao.entities.audit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "AUDIT_ACTIONS_STEP_ENUM_LINE_ITEM_ENTITY")
public class AuditActionStepEnumLineItemEntity extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = -6371918723712965452L;

	@Enumerated(EnumType.STRING)
	private AuditActionTypeEnum actionTypeEnum;
}
