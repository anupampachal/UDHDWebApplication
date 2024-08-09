package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IAAuditObservationPartALineItemDTO {

	@NotNull
	private Long iaId;

	private Long id;

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

	private String fileId;

	@NotNull
	private IAPartAAuditObsTypeEnum partAType;
}
