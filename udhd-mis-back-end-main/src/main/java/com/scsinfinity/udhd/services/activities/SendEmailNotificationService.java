package com.scsinfinity.udhd.services.activities;

import javax.transaction.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.scsinfinity.udhd.services.activities.dto.SendEmailDTO;
import com.scsinfinity.udhd.services.activities.interfaces.ISendEmailNotificationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SendEmailNotificationService implements ISendEmailNotificationService {

	private final JavaMailSender javaMailSender;

	@Override
	@Async
	public Boolean sendEmail(SendEmailDTO dto) {
		log.debug("sending email");
		sendEmail(dto.getEmailIdsCSV(), dto.getSubject(), dto.getBody());
		return true;
	}

	void sendEmail(String csv, String subject, String textBody) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(csv);
		msg.setFrom("admin@techvarsity.org");
		msg.setSubject(subject);
		msg.setText(textBody);
		javaMailSender.send(msg);

	}

}
