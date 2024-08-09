package com.scsinfinity.udhd.services.audit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class AuditCommentDTO {

	@NotBlank
	@Size(min = 3, max = 2000)
	private String comment;

	private Long id;

	@NotNull
	private Long auditCriteriaId;
}
