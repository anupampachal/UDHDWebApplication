package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IANonComplianceOfTDSNVATDTO {
	private Long iaId;

	private Long id;

	private String name;//only to be used for custom
	@NotBlank
	private String description;

	@NotNull
	private BigDecimal amountInvolved;

	private String fileId;

	@NotNull
	private IANonComplianceOfTDSNVATEnum type;
}
