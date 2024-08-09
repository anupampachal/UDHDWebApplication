package com.scsinfinity.udhd.services.audit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scsinfinity.udhd.dao.entities.storage.FileEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class AuditComplianceDTO {

	private Long id;
	@NotBlank
	@Size(min = 3, max = 2000)
	private String comment;

	private FileEntity file;
	@NotNull
	private Boolean status;

	@NotNull
	private Long auditCriteriaId;
}
