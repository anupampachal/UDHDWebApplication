package com.scsinfinity.udhd.exceptions;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestAlertException extends AbstractThrowableProblem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7636901985740066099L;

	private final String defaultMessage;

	private final String entityName;

	private final String errorKey;

	public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
		this(URI.create("http://www.scsinfinity.com/problem" + "/problem-with-message"), defaultMessage, entityName,
				errorKey);
	}

	public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
		super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(entityName, errorKey));
		this.defaultMessage=defaultMessage;
		this.entityName = entityName;
		this.errorKey = errorKey;
	}

	public String getDefaultMessage(){return defaultMessage;}
	public String getEntityName() {
		return entityName;
	}

	public String getErrorKey() {
		return errorKey;
	}

	private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("message", "error." + errorKey);
		parameters.put("params", entityName);
		return parameters;
	}

}
