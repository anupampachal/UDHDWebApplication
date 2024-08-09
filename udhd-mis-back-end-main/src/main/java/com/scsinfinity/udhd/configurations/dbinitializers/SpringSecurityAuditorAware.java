package com.scsinfinity.udhd.configurations.dbinitializers;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		String username = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		}
		return username != null ? Optional.of(username) : Optional.of("SYSTEM");

	}
}
