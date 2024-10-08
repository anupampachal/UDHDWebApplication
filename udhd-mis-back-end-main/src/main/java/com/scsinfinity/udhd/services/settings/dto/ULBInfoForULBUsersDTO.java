package com.scsinfinity.udhd.services.settings.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ULBInfoForULBUsersDTO {
	@NotNull
	private ULBDTO ulb;
}
