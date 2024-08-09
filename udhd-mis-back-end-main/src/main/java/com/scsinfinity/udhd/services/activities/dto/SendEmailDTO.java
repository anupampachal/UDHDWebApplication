package com.scsinfinity.udhd.services.activities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class SendEmailDTO {
	@Email
	@NotBlank
	private String emailIdsCSV;
	@NotBlank
	private String subject;
	@NotBlank
	private String body;
}
