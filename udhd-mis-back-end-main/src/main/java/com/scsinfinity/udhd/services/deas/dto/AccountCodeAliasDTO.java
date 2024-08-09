package com.scsinfinity.udhd.services.deas.dto;

import com.scsinfinity.udhd.dao.entities.deas.CodeTypeMajorMinorDetailEnum;
import com.scsinfinity.udhd.dao.entities.deas.TrialUploadStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class AccountCodeAliasDTO {
	private String alias;
	private String code;
	private String error;
	private TrialUploadStatusEnum status;
	private CodeTypeMajorMinorDetailEnum type;
}
