package com.coaching.erp.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
	@Value("${app.security.jwt.secret}")
	private String secret;
	@Value("${app.security.jwt.issuer}")
	private String issuer;
	@Value("${app.security.jwt.expiry-minutes}")
	private long expiryMinutes;

	private Key getSigningKey() {
		byte[] keyBytes;
		try {
			keyBytes = Decoders.BASE64.decode(secret);
		} catch (IllegalArgumentException ex) {
			keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		}
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String username, List<String> roles) {
		Instant now = Instant.now();
		Instant expiry = now.plusSeconds(expiryMinutes * 60);
		return Jwts.builder()
			.setIssuer(issuer)
			.setSubject(username)
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(expiry))
			.addClaims(Map.of("roles", roles))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims parseToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getSigningKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}