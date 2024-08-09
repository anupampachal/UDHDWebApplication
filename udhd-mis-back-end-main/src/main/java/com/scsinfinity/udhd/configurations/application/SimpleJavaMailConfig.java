package com.scsinfinity.udhd.configurations.application;

import lombok.AllArgsConstructor;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


@Configuration
@EnableAsync
@AllArgsConstructor
public class SimpleJavaMailConfig {

    @Bean
    public Mailer getMailer(){
        return MailerBuilder
                .withDebugLogging(true)
                .withSMTPServerHost("smtp.zoho.com")
                .withSMTPServerPort(587)
                .withSMTPServerUsername("admin@scsinfinity.com")
                .withSMTPServerPassword("Soni@and2")
                .withConnectionPoolCoreSize(2)
                .withConnectionPoolClaimTimeoutMillis(60000)
                .withConnectionPoolExpireAfterMillis(1800000)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer();
    }


}
