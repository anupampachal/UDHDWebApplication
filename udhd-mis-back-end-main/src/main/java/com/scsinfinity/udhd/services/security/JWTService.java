package com.scsinfinity.udhd.services.security;

import java.util.Date;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class JWTService {

	private String tokenValidity = "720000000";

	private String SECRET_KEY = "4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*";

	byte[] secretKey;


	private DirectEncrypter encrypter;
	private JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
	private ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor;


	public JWTService() throws KeyLengthException {

		secretKey = SECRET_KEY.getBytes();
		encrypter = new DirectEncrypter(secretKey);
		jwtProcessor = new DefaultJWTProcessor<SimpleSecurityContext>();

		// The JWE key source
		JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<SimpleSecurityContext>(secretKey);

		// Configure a key selector to handle the decryption phase
		JWEKeySelector<SimpleSecurityContext> jweKeySelector = new JWEDecryptionKeySelector<SimpleSecurityContext>(
				JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);

		jwtProcessor.setJWEKeySelector(jweKeySelector);

	}

	public JWTClaimsSet extractClaimsSet(String token, String audience) throws BadCredentialsException {
		return parseToken(token, audience);
	}

	public String generateToken(UserRequestDTO userDetails, String audience) throws Exception {
		JWTClaimsSet claims = generateClaims(userDetails, audience);
		JWEObject jweObject = createJWEObject(claims);

		try {
			jweObject.encrypt(encrypter);
		} catch (JOSEException e) {
			log.error("error while encrypting");
			throw new Exception("ADMIN_KEY_LENGTH_EXCEPTION_JOSE_EXCEPTION");
		}
		String token = jweObject.serialize();

		return token;
	}

	/**
	 * Parses a token
	 */
	private JWTClaimsSet parseToken(String token, String audience) throws BadCredentialsException {

		try {

			JWTClaimsSet claims = jwtProcessor.process(token, null);
			ensureCredentials(audience != null && claims.getAudience().contains(audience), "INCORRECT_AUDIENCE");

			long expirationTime = claims.getExpirationTime().getTime();
			long currentTime = System.currentTimeMillis();

			log.debug("Parsing JWT. Expiration time = " + expirationTime + ". Current time = " + currentTime);

			ensureCredentials(expirationTime >= currentTime, "EXPIRED_TOKEN");

			return claims;

		} catch (BadJOSEException | JOSEException | java.text.ParseException e) {

			throw new BadCredentialsException(e.getMessage());
		}
	}

	private JWTClaimsSet generateClaims(UserRequestDTO userDetails, String audience) {
		return new JWTClaimsSet.Builder().audience(audience).claim("name", userDetails.getName())
				.claim("username", userDetails.getUsername()).claim("authority", userDetails.getAuthority())
				.subject(userDetails.getUsername()).issueTime(new Date(System.currentTimeMillis()))
				.expirationTime(new Date(System.currentTimeMillis() + Long.parseLong(tokenValidity))).build();
	}

	private JWEObject createJWEObject(JWTClaimsSet claims) {
		Payload payload = new Payload(claims.toJSONObject());
		return new JWEObject(header, payload);
	}

	/**
	 * Throws BadCredentialsException if not valid
	 * 
	 * @param valid
	 * @param messageKey
	 */
	private static void ensureCredentials(boolean valid, String messageKey) {

		if (!valid)
			throw new BadCredentialsException(messageKey);
	}

}
