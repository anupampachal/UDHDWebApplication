package com.scsinfinity.udhd.services.dashboard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CAGPACAuditDashboardDTO {

	@NotNull
	private Long currentData;

	@NotBlank
	private String currentPeriod;

	@NotNull
	private Long previousData;

	@NotBlank
	private String previousPeriod;
}
