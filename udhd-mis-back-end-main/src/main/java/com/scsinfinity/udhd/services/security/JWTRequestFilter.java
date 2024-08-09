package com.scsinfinity.udhd.services.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jwt.JWTClaimsSet;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {
	private final JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, BadCredentialsException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwtString = null;
		JWTClaimsSet claims = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwtString = authorizationHeader.substring(7);
			try {
				claims = jwtService.extractClaimsSet(jwtString, AudienceConstants.AUTH_AUDIENCE);

				// add method call to check in the cache if this user has to be prohibited from
				// logging in.
				// @Todo

				username = claims.getSubject();

			} catch (BadCredentialsException e) {
				log.error("Invalid Token");
				throw new BadCredentialsException("INVALID_TOKEN");
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			Set<GrantedAuthority> authoritiesSet = new HashSet<>();
			authoritiesSet.add(new SimpleGrantedAuthority(claims.getClaim("authority").toString()));

			UsernamePasswordAuthenticationTokenWithUserInfo usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationTokenWithUserInfo(
					username, null, authoritiesSet, claims.getClaim("name").toString());

			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		filterChain.doFilter(request, response);
	}

}
