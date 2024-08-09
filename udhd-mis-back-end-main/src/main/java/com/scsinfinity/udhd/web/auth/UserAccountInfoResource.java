package com.scsinfinity.udhd.web.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.auth.dtoandconstants.LoginResponseDTO;
import com.scsinfinity.udhd.services.auth.interfaces.IUserAccountInformationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/account-info")
public class UserAccountInfoResource {

	private final IUserAccountInformationService userAccountInformationService;

	@GetMapping
	public ResponseEntity<LoginResponseDTO> getUserLoginInfo(HttpServletRequest request) {
		log.info("getting user info");
		return ResponseEntity.ok(userAccountInformationService.getUserAccountInfo(request));
	}
}
