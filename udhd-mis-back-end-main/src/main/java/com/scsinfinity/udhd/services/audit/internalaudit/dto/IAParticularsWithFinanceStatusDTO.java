package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IAParticularsWithFinanceStatusDTO {

	@NotNull
	private Long iaId;

	//@NotNull
	private Long id;

	//@NotNull
	private Long statusOfDeasId;

	@NotBlank
	private String particulars;

	@NotBlank
	private String value;
}
