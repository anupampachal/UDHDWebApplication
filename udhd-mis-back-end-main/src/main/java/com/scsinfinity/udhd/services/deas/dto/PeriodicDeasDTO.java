package com.scsinfinity.udhd.services.deas.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.configurations.application.date.DateQuartersEnum;
import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class PeriodicDeasDTO {

	private Long id;

	private String fileId;

	private MultipartFile file;

	@NotNull
	private String inputDate;
	@NotNull
	private String startDate;
	@NotNull
	private String endDate;
	//@NotNull
	private DateQuartersEnum quarter;
	@NotNull
	private Long ulbId;

	@NotEmpty
	private String ulbName;

	@NotNull
	private TypeStatusEnum status;

	@NotNull
	private Boolean overWritten;
}
