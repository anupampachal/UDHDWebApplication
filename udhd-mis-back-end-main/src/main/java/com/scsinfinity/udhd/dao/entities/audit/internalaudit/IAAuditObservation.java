package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.scsinfinity.udhd.dao.entities.base.BaseIdEntity;
import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public  class IAAuditObservation extends BaseIdEntity {

	@NotBlank
	private String objective;

	@NotBlank
	private String criteria;

	@NotBlank
	private String condition;

	@NotBlank
	private String consequences;

	@NotBlank
	private String cause;

	@NotBlank
	private String correctiveAction;

	@OneToOne
	private FileEntity file;
}
