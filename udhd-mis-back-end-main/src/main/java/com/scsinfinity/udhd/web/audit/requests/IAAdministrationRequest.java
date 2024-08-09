package com.scsinfinity.udhd.web.audit.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IAAdministrationRequest {
	@NotNull
	private Long internalAuditId;
	@NotBlank
	@Size(min = 3, max = 255)
	private String nameOfChairmanMayor;
	@NotBlank
	@Size(min = 3, max = 255)
	private String periodOfServiceChairman;
	@NotBlank
	@Size(min = 3, max = 255)
	private String nameOfCMO;
	@NotBlank
	@Size(min = 3, max = 255)
	private String periodOfServiceCMO;
}
