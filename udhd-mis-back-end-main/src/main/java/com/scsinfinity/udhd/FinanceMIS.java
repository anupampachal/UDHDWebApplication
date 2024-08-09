package com.scsinfinity.udhd;

import lombok.AllArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
//@EnableCaching
@SpringBootApplication
public class FinanceMIS {


	public static void main(String[] args) {

		SpringApplication.run(FinanceMIS.class, args);


	}


}
