package com.scsinfinity.udhd.services.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class UsernamePasswordAuthenticationTokenWithUserInfo extends UsernamePasswordAuthenticationToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9164357238832457242L;
	private String name;
	
	public UsernamePasswordAuthenticationTokenWithUserInfo(Object principal, Object credentials) {
		super(principal, credentials);
	}
	public UsernamePasswordAuthenticationTokenWithUserInfo(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		// TODO Auto-generated constructor stub
	}

	public UsernamePasswordAuthenticationTokenWithUserInfo(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities,String name) {
		super(principal, credentials, authorities);
		this.name=name;
	}
}
