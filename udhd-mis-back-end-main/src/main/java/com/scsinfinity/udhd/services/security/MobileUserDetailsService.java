package com.scsinfinity.udhd.services.security;

import javax.security.auth.login.AccountExpiredException;
import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.CredentialExpiredException;

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
@AllArgsConstructor
public class MobileUserDetailsService implements UserDetailsService {

	private final IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {
		log.debug("load username for mobileNo->", mobileNo);
		UserEntity user = null;
		try {
			user = userRepository.findByMobileNo(mobileNo).orElseThrow(() -> new UsernameNotFoundException(mobileNo));

			if (!user.isEnabled())
				throw new DisabledException(mobileNo);
			else if (!user.isAccountNonExpired())
				throw new AccountExpiredException(mobileNo);
			else if (!user.isAccountNonLocked())
				throw new AccountLockedException(mobileNo);
			else if (!user.isCredentialsNonExpired())
				throw new CredentialExpiredException(mobileNo);
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
