package com.scsinfinity.udhd.services.deas.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.scsinfinity.udhd.dao.entities.deas.TypeStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class DEASDTO {

	private Long id;

	private String fileId;

	private MultipartFile file;
	@NotNull
	private LocalDate startDate;
	@NotNull
	private LocalDate endDate;

	@NotNull
	private Long ulbId;

	@NotEmpty
	private String ulbName;

	@NotNull
	// @Enumerated(EnumType.STRING)
	private TypeStatusEnum status;

	@NotNull
	private Boolean overWritten;
}
