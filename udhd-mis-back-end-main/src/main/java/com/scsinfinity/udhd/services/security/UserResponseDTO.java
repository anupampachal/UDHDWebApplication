package com.scsinfinity.udhd.services.security;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDTO {

	
	@Email
	@Size(min = 1, max = 50)
	private String username;
	
	@Size(max = 50, min = 2)
	private String name;

	private String authority;

}
