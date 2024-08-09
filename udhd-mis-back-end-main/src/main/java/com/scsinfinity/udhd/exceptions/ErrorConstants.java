package com.scsinfinity.udhd.exceptions;

import java.net.URI;

public class ErrorConstants {

	public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
	public static final String ERR_VALIDATION = "error.validation";
	public static final String PROBLEM_BASE_URL = "http://www.scsinfinity.com/problem";
	public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
	public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
	public static final URI BAD_CREDENTIALS_EXCEPTION= URI.create(PROBLEM_BASE_URL+"/bad-credentials");
	public static final URI STORAGE_EXCEPTION_TYPE = URI.create(PROBLEM_BASE_URL + "/storage-exception");
	public static final URI OTHER_EXCEPTION_TYPE = URI.create(PROBLEM_BASE_URL + "/other-exception");
	public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
	public static final URI ENTITY_NOT_FOUND = URI.create(PROBLEM_BASE_URL + "/entity-not-found");
	public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
	public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
	public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
	public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");
	public static final URI NAME_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/name-already-used");
	public static final URI INSTITUTE_PROFILE_DOES_NOT_EXIST = URI
			.create(PROBLEM_BASE_URL + "/institue-profile-does-not-exist");
	public static final URI EMAIL_SETUP_INCORRECT = URI.create(PROBLEM_BASE_URL + "/email_setup_incorrect");
	public static final URI SMS_SETUP_INCORRECT = URI.create(PROBLEM_BASE_URL + "/sms_setup_incorrect");
	public static final URI PAYMENT_GATEWAY_SETUP_INCORRECT = URI.create(PROBLEM_BASE_URL + "/pg_setup_incorrect");

}
