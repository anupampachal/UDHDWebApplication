package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class IATallyInfoDTO {

	@NotNull
	private Long iaId;

	private Long id;

	private Long statusOfDeasId;

	@NotBlank
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private String periodFrom;

	@NotNull
	//@JsonSerialize(using = LocalDateSerializer.class)
	//@JsonDeserialize(using = LocalDateDeserializer.class)
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private String periodTo;

	@NotBlank
	private String tallySerialNo;

	@NotBlank
	private String tallyId;
}
