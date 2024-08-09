package com.scsinfinity.udhd.configurations.dbinitializers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AppInitializer implements CommandLineRunner {
	private final AuthorityInitializer authInitializer;
	private final GeographyInitializer geographyInitializer;
	private final FolderInitialiazer folderInitializer;
	private final UserInitializer userInitializer;
	private final AccountCodeInitializer accountCodeInitializer;
	private final AuditActionsStepInitializer auditActionsStepInitializer;
	// private final JavaMailSender javaMailSender;

	@Override
	public void run(String... args) throws Exception {
		boolean initialize = false;
		if (initialize) {
			authInitializer.initializeAuthority();
			geographyInitializer.initializeGeography();
			folderInitializer.intializeStorage();
			userInitializer.initializeUsers(null);
			accountCodeInitializer.initializeAccount();
			auditActionsStepInitializer.initializer();
		}
	}

}
