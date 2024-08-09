package com.scsinfinity.udhd.services.auth.dtoandconstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDTO {
	private String username;
	private String authority;
	private String jwt;
	private String name;
}
