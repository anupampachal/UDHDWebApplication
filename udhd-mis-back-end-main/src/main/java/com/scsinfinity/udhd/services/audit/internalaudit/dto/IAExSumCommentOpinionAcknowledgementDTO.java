package com.scsinfinity.udhd.services.audit.internalaudit.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class IAExSumCommentOpinionAcknowledgementDTO {

	@Size(min = 2, max = 10000)
	private String text;

	@NotNull
	private IAExSumCommentOpinionAcknowledgementEnum type;

	@NotNull
	private Long iaId;
}
