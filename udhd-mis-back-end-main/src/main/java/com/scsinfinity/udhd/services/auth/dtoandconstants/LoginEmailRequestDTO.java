package com.scsinfinity.udhd.services.auth.dtoandconstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginEmailRequestDTO {
	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(min = 8)
	private String password;
	
	/*@NotBlank
	private String  recaptcha;*/


}
