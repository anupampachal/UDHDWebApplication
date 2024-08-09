package com.scsinfinity.udhd.services.base;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class RandomGeneratorService {
	private static final int DEF_COUNT = 9;
	private static final int OTP_STRENGTH_COUNT = 6;
	private static final int STRING_NUMBERIC_COUNT=30;
	private static final int DEF_COUNT_FOR_ID=8;

	/**
	 * Generate OTP
	 */
	public String generateOTP() {
		return RandomStringUtils.randomNumeric(OTP_STRENGTH_COUNT);
	}

	/**
	 * Generate a password.
	 *
	 * @return the generated password
	 */
	public String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
	}
	
	public String generateRandomNumberForFilename() {
		return RandomStringUtils.randomNumeric(STRING_NUMBERIC_COUNT);
	}
	public String generateRandomAlphaNumericForID() {
		return RandomStringUtils.randomAlphanumeric(DEF_COUNT_FOR_ID);
	}
	
	public String generateRandomAlphaNumeric() {
		return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
	}

	/**
	 * Generate an activation key.
	 *
	 * @return the generated activation key
	 */
	public String generateActivationKey() {
		return RandomStringUtils.randomNumeric(DEF_COUNT);
	}

	/**
	 * Generate a reset key.
	 *
	 * @return the generated reset key
	 */
	public String generateResetKey() {
		return RandomStringUtils.randomNumeric(DEF_COUNT);
	}

	/**
	 * Generate a unique series to validate a persistent token, used in the
	 * authentication remember-me mechanism.
	 *
	 * @return the generated series data
	 */
	public String generateSeriesData() {
		return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
	}

	/**
	 * Generate a persistent token, used in the authentication remember-me
	 * mechanism.
	 *
	 * @return the generated token data
	 */
	public String generateTokenData() {
		return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
	}
}
