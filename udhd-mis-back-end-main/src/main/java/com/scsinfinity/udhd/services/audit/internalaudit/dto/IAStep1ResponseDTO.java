package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scsinfinity.udhd.dao.entities.audit.AuditStatusEnum;
import com.scsinfinity.udhd.services.settings.dto.ULBDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class IAStep1ResponseDTO {
	
	@NotNull
	private Long id;

	@NotBlank
	@Size(min = 10, max = 255)
	private String title;

	@Size(min = 5, max = 2000)
	private String description;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate startDate;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate endDate;

	@NotNull
	private ULBDTO ulb;
	
	private String ulbName;

	@NotNull
	private AuditStatusEnum auditStatus;

	@NotNull
	private Boolean isAssigned;

	@NotNull
	private Long auditReportId;

}
