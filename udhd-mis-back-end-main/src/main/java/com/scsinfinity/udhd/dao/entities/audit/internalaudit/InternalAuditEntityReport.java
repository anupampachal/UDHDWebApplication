package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

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
@Table(name = "INTERNAL_AUDIT_REPORT")
public class InternalAuditEntityReport extends BaseIdEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -8079183991925133743L;

	@OneToOne
	private InternalAuditEntity internalAudit;

	@OneToOne
	private FileEntity fileEntity;
}
