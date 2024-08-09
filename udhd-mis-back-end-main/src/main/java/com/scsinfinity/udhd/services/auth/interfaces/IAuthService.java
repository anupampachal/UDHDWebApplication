package com.scsinfinity.udhd.services.auth.interfaces;

import javax.validation.Valid;

import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginEmailRequestDTO;
import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginMobileOTPDTO;
import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginResponseDTO;

public interface IAuthService {
	LoginResponseDTO loginByUsername(LoginEmailRequestDTO loginEmailRequestDTO);
	LoginResponseDTO loginByMobile(@Valid LoginMobileOTPDTO loginMobileOTPDTO);
	Boolean requestLoginByMobile(String mobileNumber);
}
