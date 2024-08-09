package com.scsinfinity.udhd.services.settings.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ULBsInfoForOtherTypesOfUsers {
	@NotNull
	private List<ULBDTO> ulbs;
}
