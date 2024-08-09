package com.scsinfinity.udhd.web.settings;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/settings/user-profile")
public class UserProfileInformationResource {

	@GetMapping
	public ResponseEntity<Object> getMyProfile() {
		log.debug("Get my profile information");
		return ResponseEntity.ok("");
	}
}
