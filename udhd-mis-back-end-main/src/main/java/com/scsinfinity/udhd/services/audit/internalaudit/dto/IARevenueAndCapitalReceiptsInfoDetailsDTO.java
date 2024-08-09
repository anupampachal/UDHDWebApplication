package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IARevenueAndCapitalReceiptsInfoDetailsDTO {
	@NotNull
	private Long iaId;

	private Long id;
	@NotBlank
	private String fy1;
	@NotBlank
	private String fy2;
	@NotBlank
	private String fy3;
	@NotBlank
	private String fy4;
	@NotBlank
	private String fy5;
	@NotBlank
	private String fy6;
}
