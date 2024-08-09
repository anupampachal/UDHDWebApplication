package com.scsinfinity.udhd.services.auth.interfaces;

import javax.servlet.http.HttpServletRequest;

import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginResponseDTO;

public interface IUserAccountInformationService {

	LoginResponseDTO getUserAccountInfo(HttpServletRequest request);
}
