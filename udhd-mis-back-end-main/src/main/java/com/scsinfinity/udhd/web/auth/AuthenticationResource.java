package com.scsinfinity.udhd.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.scsinfinity.udhd.configurations.application.MailSenderSCS;
import org.simplejavamail.api.mailer.Mailer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginEmailRequestDTO;
import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginMobileOTPDTO;
import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginResponseDTO;
import com.scsinfinity.udhd.services.auth.interfaces.IAuthService;
import com.scsinfinity.udhd.services.common.ResponseObject;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("**")
public class AuthenticationResource {

	private final IAuthService authService;
	private final Mailer mailer;
	private final MailSenderSCS mailSenderSCS;

	@PostMapping
	public ResponseEntity<LoginResponseDTO> loginUsingEmail(
			@Valid @RequestBody LoginEmailRequestDTO loginEmailRequestDTO, HttpServletRequest request, HttpServletResponse response) {
		log.debug("LoginController->loginUsingEmail :", loginEmailRequestDTO);
		LoginResponseDTO loginResponse=authService.loginByUsername(loginEmailRequestDTO);
		response.setHeader("xsrf-token", loginResponse.getJwt());

		//mailSenderSCS.sendMail();
		return ResponseEntity.ok(authService.loginByUsername(loginEmailRequestDTO));
	}

	/**
	 * Left for future implementation--- not complete
	 * @param mobileNo
	 * @return
	 */
	@GetMapping("/mobile/{mobileNo}")
	public ResponseEntity<ResponseObject> requestLoginUsingMobile(@PathVariable("mobileNo") String mobileNo) {
		log.debug("LoginController -> requestLoginUsingMobile : ", mobileNo);
		Boolean response = authService.requestLoginByMobile(mobileNo);
		return ResponseEntity.ok(ResponseObject.builder().success(response).build());
	}

	/**
	 * Left for future implementation--- not complete
	 * @param loginMobileOTPDTO
	 * @return
	 */
	@PostMapping("/mobile")
	public ResponseEntity<LoginResponseDTO> loginUsingMobile(@Valid @RequestBody LoginMobileOTPDTO loginMobileOTPDTO) {
		log.debug("LoginController -> loginUsingMobile : ", loginMobileOTPDTO);
		return ResponseEntity.ok(authService.loginByMobile(loginMobileOTPDTO));
	}
	
	

}
