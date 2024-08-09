package com.scsinfinity.udhd.exceptions;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

	private LocalDateTime timestamp;
	private String message;
	private HttpStatus status;
	private Integer statusCode;
	private URI uri;
	private Map<String, Object> withKeyAndValues;
}
