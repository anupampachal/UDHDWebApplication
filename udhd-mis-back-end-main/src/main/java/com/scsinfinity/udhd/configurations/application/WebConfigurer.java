package com.scsinfinity.udhd.configurations.application;

import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfigurer implements ServletContextInitializer {

	@Autowired
	private Jackson2ObjectMapperBuilder mapperBuilder;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "PATCH"));
		config.setAllowCredentials(true);
		// config.addAllowedOrigin("*");
		config.setAllowedOrigins(
				Arrays.asList("http://localhost:4200", "https://udhd.scsinfinity.com", "http://udhd.scsinfinity.com"));
		config.addAllowedHeader("*");
		if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
			log.debug("Registering CORS filter");
			source.registerCorsConfiguration("/**", config);
			source.registerCorsConfiguration("/management/**", config);
		}
		return new CorsFilter(source);
	}

	/*
	 * @Bean
	 * 
	 * @Primary public ObjectMapper objectMapper() {
	 * 
	 * //SimpleDateFormat formatter = new
	 * SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
	 * SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); final
	 * ObjectMapper mapper = new ObjectMapper(); mapper.setDateFormat(formatter);
	 * mapper.registerModule(new JavaTimeModule());
	 * //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); return
	 * mapper; }
	 */
	/*
	 * @Bean public SimpleModule customModule() { ObjectMapper mapper = new
	 * ObjectMapper(); SimpleModule module = new SimpleModule();
	 * module.addSerializer(java.time.LocalDate.class, new
	 * CustomLocalDateSerializer());
	 * module.addDeserializer(java.time.LocalDate.class, new
	 * CustomLocalDateDeserializer()); mapper.registerModule(module); return module;
	 * }
	 * 
	 * 
	 * @Bean
	 * 
	 * @Primary public ObjectMapper objectMapper() {
	 * 
	 * SimpleModule module = new SimpleModule();
	 * module.addSerializer(java.time.LocalDate.class, new
	 * CustomLocalDateSerializer()); //
	 * module.addDeserializer(java.time.LocalDate.class, new
	 * CustomLocalDateDeserializer()); return new
	 * ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).
	 * registerModule(module); }
	 * 
	 * @Bean public ObjectMapper objectMapper() { SimpleModule module = new
	 * SimpleModule(); ObjectMapper o = new ObjectMapper(); o.registerModule(new
	 * JavaTimeModule()); o.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	 * return o; }
	 * 
	 * 
	 * @Bean public ObjectMapper getObjectMapperWithDateTimeModule() { ObjectMapper
	 * mapper = mapperBuilder.build().registerModule(new ParameterNamesModule())
	 * .registerModule(new Jdk8Module()).registerModule(new JavaTimeModule()); //
	 * new module, NOT JSR310Module return mapper; }
	 */
	@Bean
	public RedisSerializer<Object> redisSerializer() {

		ObjectMapper objectMapper = mapperBuilder.build();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
		// dateTime , JSR310 LocalDateTimeSerializer
		objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
		// , LocalDateTIme LocalDate , Jackson-data-JSR310
		objectMapper.registerModule(new JavaTimeModule());
		// ,
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL,
				JsonTypeInfo.As.PROPERTY);
		GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
		return new GenericJackson2JsonRedisSerializer(objectMapper);

	}
}
