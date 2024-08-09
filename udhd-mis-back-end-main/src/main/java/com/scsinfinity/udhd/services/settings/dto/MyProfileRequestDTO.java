package com.scsinfinity.udhd.services.settings.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyProfileRequestDTO {

	@NotBlank
	private String name;
	
	@NotBlank
	private String mobileNo;

	
	private String address;

	
	private String fileId;

}
