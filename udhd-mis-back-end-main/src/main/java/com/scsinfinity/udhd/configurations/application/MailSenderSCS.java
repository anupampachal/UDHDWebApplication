package com.scsinfinity.udhd.configurations.application;

import lombok.AllArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailSenderSCS {

    private final Mailer mailer;

    @Async
    public void sendMail(){
        Email email = EmailBuilder.startingBlank()
                .from("Admin", "admin@scsinfinity.com")
                .to("Aditya", "aditya.menu@gmail.com")
                .to("Other Email", "uwannabadi@gmail.com")
                .withSubject("Test Email")
                .withPlainText("Hello test plain test")
                .withHTMLText("<p>Test <strong>our mail sender</strong>!!!</p>")
                .buildEmail();

        mailer.sendMail(email);
    }
}
