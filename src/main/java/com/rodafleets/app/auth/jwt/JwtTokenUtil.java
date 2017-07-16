package com.rodafleets.app.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rodafleets.app.model.Driver;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	static final String CLAIM_KEY_UNIQUE = "unique_value";
	static final String CLAIM_KEY_VALUE = "key";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";

	private static final String AUDIENCE_UNKNOWN = "unknown";
	private static final String AUDIENCE_WEB = "web";
	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";

	@Value("${api.auth.secretkey}")
	private String secret = "mySecret";

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = getClaimsFromToken(token);
			audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	public String getKeyFromToken(String token) {
		String key;
		try {
			final Claims claims = getClaimsFromToken(token);
			key = (String) claims.get(CLAIM_KEY_VALUE);
		} catch (Exception e) {
			System.out.println("excpetion in getKeyFromToken = " + e.getMessage());
			key = null;
		}
		return key;
	}

	public String getDriverFromToken(String token) {
		String username;
		String key;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = (String) claims.get(CLAIM_KEY_UNIQUE);
			key = (String) claims.get(CLAIM_KEY_VALUE);
		} catch (Exception e) {
			System.out.println("excpetion in getDriverFromToken = " + e.getMessage());
			username = null;
		}
		return username;
	}

	public String generateDriverToken(Driver driver) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_UNIQUE, driver.getPhoneNumber());
		claims.put(CLAIM_KEY_VALUE, "driver");
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	String generateToken(Map<String, Object> claims) {
		System.out.println("in generateToken = " + secret);
		return Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public Boolean validateDriverToken(String token, Driver driverInfo) {
		System.out.println("in validate token");

		Driver driver = (Driver) driverInfo;
		final String username = getDriverFromToken(token);
		return (username.equals(driver.getPhoneNumber()));
	}
}