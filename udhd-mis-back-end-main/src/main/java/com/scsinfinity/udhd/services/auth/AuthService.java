package com.scsinfinity.udhd.services.auth;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.exceptions.BadRequestAlertException;
import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginEmailRequestDTO;
import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginMobileOTPDTO;
import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginResponseDTO;
import com.scsinfinity.udhd.services.auth.interfaces.IAuthService;
import com.scsinfinity.udhd.services.security.AudienceConstants;
import com.scsinfinity.udhd.services.security.JWTService;
import com.scsinfinity.udhd.services.security.UserRequestDTO;
import com.scsinfinity.udhd.services.security.UsernamePasswordAuthenticationTokenWithUserInfo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	private final CaptchaService captchaService;

	@Override
	public LoginResponseDTO loginByUsername(LoginEmailRequestDTO loginEmailRequestDTO) {

		log.debug("AuthService-> loginByUsername: ", loginEmailRequestDTO.getEmail());
		@SuppressWarnings("unused")
		Exception exception = null;
		Authentication authentication = null;
		try {

		/*	boolean captchaVerified = captchaService.verify(loginEmailRequestDTO.getRecaptcha());
			if (!captchaVerified) {
				throw new javax.naming.AuthenticationException("Captcha verification failed");
			}*/

			UsernamePasswordAuthenticationTokenWithUserInfo authenticationToken = new UsernamePasswordAuthenticationTokenWithUserInfo(
					loginEmailRequestDTO.getEmail(), loginEmailRequestDTO.getPassword());
			authentication = this.authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (DisabledException disabledException) {
			exception = disabledException;
			throw new BadRequestAlertException("User account is disabled", "AuthService", "DISABLEDUSERACCOUNT");
		} catch (LockedException lockedException) {
			exception = lockedException;
			throw new BadRequestAlertException("User account is locked", "AuthService", "LOCKEDUSERACCOUNT");
		} catch (BadCredentialsException badCredentialsException) {
			exception = badCredentialsException;
			throw new BadRequestAlertException("Incorrect credentials", "AuthService", "BADUSERLOGINCREDENTIALS");
		} catch (AuthenticationException authenticationException) {
			exception = authenticationException;
			if (authenticationException.getCause() instanceof BadRequestAlertException) {
				BadRequestAlertException badRequestAlertException = (BadRequestAlertException) authenticationException
						.getCause();
				if (badRequestAlertException.getErrorKey().equals("ACCOUNTEXPIRED"))
					throw new BadRequestAlertException(
							"Authentication expired, please connect with system administrator", "AuthService",
							"ACCOUNTEXPIRED");
				if (badRequestAlertException.getErrorKey().equals("ACCOUNTLOCKED"))
					throw new BadRequestAlertException(
							"Authentication locked, please connect with system administrator", "AuthService",
							"ACCOUNTLOCKED");
				if (badRequestAlertException.getErrorKey().equals("CREDENTIALEXPIRED"))
					throw new BadRequestAlertException("Credential expired, please connect with system administrator",
							"AuthService", "CREDENTIALEXPIRED");
				if (badRequestAlertException.getErrorKey().equals("DISABLEDUSERACCOUNT"))
					throw new BadRequestAlertException("User account is disabled", "AuthService",
							"DISABLEDUSERACCOUNT");

			}
			throw new BadRequestAlertException("Authentication failed, please connect with system administrator",
					"AuthService", "USERAUTHENTICATIONFAILED");
		} /*catch (javax.naming.AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		@SuppressWarnings("unchecked")
		Optional<SimpleGrantedAuthority> authorityO = (Optional<SimpleGrantedAuthority>) authentication.getAuthorities()
				.stream().findFirst();

		String authority = authorityO.get().getAuthority();
		return new LoginResponseDTO(authentication.getPrincipal().toString(), authority,
				getJWT(UserRequestDTO.builder().authority(authority).username(authentication.getPrincipal().toString())
						.name(authentication.getName()).build()),
				authentication.getName());
	}

	private String getJWT(UserRequestDTO userDetails) {
		UserRequestDTO userDTO = UserRequestDTO.builder().authority(userDetails.getAuthority())
				.username(userDetails.getUsername()).name(userDetails.getName()).build();
		String jwt = null;
		try {
			jwt = jwtService.generateToken(userDTO, AudienceConstants.AUTH_AUDIENCE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jwt;
	}

	@Override
	public LoginResponseDTO loginByMobile(@Valid LoginMobileOTPDTO loginMobileOTPDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean requestLoginByMobile(String mobileNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
