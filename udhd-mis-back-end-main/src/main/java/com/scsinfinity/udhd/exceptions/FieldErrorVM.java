package com.scsinfinity.udhd.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldErrorVM implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String objectName;

	private final String field;

	private final String message;

	private final String defaultMessage;

}
