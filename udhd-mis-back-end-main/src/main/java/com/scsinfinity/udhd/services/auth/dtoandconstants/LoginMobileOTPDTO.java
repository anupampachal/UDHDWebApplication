package com.scsinfinity.udhd.services.auth.dtoandconstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class LoginMobileOTPDTO {
	@NotBlank
	@Size(min = 6)
	private String otp;

	@NotBlank
	@Size(min = 10, max = 10)
	private String mobileNumber;

}
