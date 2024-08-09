package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IAAuditObjectsWithMonetaryIrregularitiesDTO {

	
	//@NotNull
	private Long iaId;
	
	private Long id;
	@NotBlank
	private String description;

	private String fileId;

	@NotNull
	private PartByTypeAuditObsEnum type;
}
