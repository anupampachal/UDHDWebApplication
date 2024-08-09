package com.scsinfinity.udhd.dao.entities.audit.internalaudit;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.scsinfinity.udhd.services.audit.internalaudit.dto.IAPartCAuditObservationEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IAPartCAuditObservationDTO {

	@NotNull
	private Long iaId;

	@NotBlank
	private String comment;

	@NotNull
	private IAPartCAuditObservationEnum partCEnum;
}
