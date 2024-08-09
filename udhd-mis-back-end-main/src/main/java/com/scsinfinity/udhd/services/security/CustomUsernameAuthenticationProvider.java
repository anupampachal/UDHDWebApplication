package com.scsinfinity.udhd.services.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.base.UserEntity;

import lombok.AllArgsConstructor;

/**
 * class to support email id based authentication
 * @author adityadheeraj
 * @date 23 Jun 2021
 */
@Service
@AllArgsConstructor
public class CustomUsernameAuthenticationProvider   implements AuthenticationProvider {

	private final UsernameUserDetailsService usernameUserDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString().trim();
		String password = authentication.getCredentials().toString();

		UserEntity user = (UserEntity) usernameUserDetailsService.loadUserByUsername(username);
		if(!passwordEncoder.matches(password, user.getPassword()))
			throw new BadCredentialsException("Incorrect password");
		
		return new UsernamePasswordAuthenticationTokenWithUserInfo(username, password, user.getAuthorities(),user.getName());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationTokenWithUserInfo.class.isAssignableFrom(authentication);
	}

}
