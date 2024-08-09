package com.scsinfinity.udhd.services.security;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialExpiredException;
import javax.transaction.Transactional;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.dao.entities.base.UserEntity;
import com.scsinfinity.udhd.dao.repositories.base.IUserRepository;
import com.scsinfinity.udhd.exceptions.BadRequestAlertException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UsernameUserDetailsService implements UserDetailsService {

	private final IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("load username for user->", username);
		UserEntity user = null;
		try {
			user = userRepository.findByUsernameIgnoreCase(username)
					.orElseThrow(() -> new UsernameNotFoundException(username));

			if (!user.isEnabled())
				throw new DisabledException(username);
			else if (!user.isAccountNonExpired())
				throw new AccountExpiredException(username);
			else if (!user.isAccountNonLocked())
				throw new AccountLockedException(username);
			else if (!user.isCredentialsNonExpired())
				throw new CredentialExpiredException(username);
		} catch (DisabledException disabledException) {
			throw new BadRequestAlertException("User account is disabled", "AuthService", "DISABLEDUSERACCOUNT");
		} catch (AccountExpiredException e) {
			throw new BadRequestAlertException("Account Expired", "AuthService", "ACCOUNTEXPIRED");
		} catch (AccountLockedException e) {
			throw new BadRequestAlertException("Account Locked", "AuthService", "ACCOUNTLOCKED");
		} catch (CredentialExpiredException e) {
			throw new BadRequestAlertException("CredentialsExpired", "AuthService", "CREDENTIALEXPIRED");
		}

		return user;

	}

}
