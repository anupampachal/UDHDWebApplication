package com.scsinfinity.udhd.web.acitvities;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scsinfinity.udhd.services.activities.dto.SendEmailDTO;
import com.scsinfinity.udhd.services.activities.interfaces.ISendEmailNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activities/send-notification")
public class SendNotificationResource {

	private final ISendEmailNotificationService notificationService;

	@PutMapping
	public ResponseEntity<Boolean> sendEmail(@RequestBody SendEmailDTO emailDTO) {
		return ResponseEntity.ok(notificationService.sendEmail(emailDTO));
	}
}
