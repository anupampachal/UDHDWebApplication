package com.scsinfinity.udhd.services.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.base.UserEntity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomMobileAuthenticationProvider implements AuthenticationProvider {

	private final MobileUserDetailsService mobileUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String otpInfo = authentication.getDetails().toString();
		UserEntity user = (UserEntity) mobileUserDetailsService.loadUserByUsername(otpInfo.split("#")[0]);
		return new UserContactnoOtpAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserContactnoOtpAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
