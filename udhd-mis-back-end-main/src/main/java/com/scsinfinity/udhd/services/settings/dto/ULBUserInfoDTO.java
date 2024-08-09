package com.scsinfinity.udhd.services.settings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ULBUserInfoDTO extends UserInfoDTO {

	private Long ulbId;

}
