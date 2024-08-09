package com.scsinfinity.udhd.services.settings.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyProfileResponseDTO {

	@NotBlank
	private String name;
	@NotBlank
	private String username;

	@NotBlank
	private String mobileNo;

	

	private String address;

	@NotBlank
	private String userGroupId;

	@NotBlank
	private String userGroupName;

	@NotBlank
	private String userId;
	@NotBlank
	private String authority;

	private String fileId;

}
