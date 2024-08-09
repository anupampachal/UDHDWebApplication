package com.scsinfinity.udhd.services.auth;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginResponseDTO;
import com.scsinfinity.udhd.services.auth.interfaces.IUserAccountInformationService;
import com.scsinfinity.udhd.services.security.UsernamePasswordAuthenticationTokenWithUserInfo;

@Service
public class UserAccountInformationService implements IUserAccountInformationService {

	@Override
	public LoginResponseDTO getUserAccountInfo(HttpServletRequest request) {
		UsernamePasswordAuthenticationTokenWithUserInfo usernamePaswd = (UsernamePasswordAuthenticationTokenWithUserInfo) SecurityContextHolder
				.getContext().getAuthentication();
		Optional<GrantedAuthority> authorityO = (Optional<GrantedAuthority>) usernamePaswd.getAuthorities().stream()
				.findFirst();

		String authority = authorityO.get().getAuthority();
		return LoginResponseDTO.builder().authority(authority).username(usernamePaswd.getPrincipal().toString())
				.name(usernamePaswd.getName()).jwt(getJwtFromHeader(request)).build();
	}

	String getJwtFromHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		return authorizationHeader.substring(7);
	}
}
