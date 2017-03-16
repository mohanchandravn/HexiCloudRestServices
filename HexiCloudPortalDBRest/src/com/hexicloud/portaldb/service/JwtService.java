package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.auth.SecretKeyProvider;
import com.hexicloud.portaldb.bean.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;

import java.net.URISyntaxException;

import java.time.LocalDateTime;
import static java.time.ZoneOffset.UTC;

import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    private static final Logger logger = Logger.getLogger(JwtService.class);
    private static final String ISSUER = "in.hexiCloud.jwt";
    public static final String USERNAME = "username";
    private final SecretKeyProvider secretKeyProvider;
    private final LoginService loginService;

    @SuppressWarnings("unused")
    public JwtService() {
        this(null, null);
    }

    @Autowired
    public JwtService(SecretKeyProvider secretKeyProvider, LoginService loginService) {
        this.secretKeyProvider = secretKeyProvider;
        this.loginService = loginService;
    }

    public String tokenFor(User user) throws IOException, URISyntaxException {
        logger.info("Creating token for user");
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now(UTC).plusHours(2).toInstant(UTC));
        return Jwts.builder()
                .setSubject("jwt-hexiCloud")
                .setExpiration(expiration)
                .setIssuer(ISSUER)
                .claim(USERNAME, user.getUserId())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public User verify(String token) throws IOException, URISyntaxException {
        logger.info("Verifying token for user");
        byte[] secretKey = secretKeyProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return loginService.getMimimalUser(claims.getBody().get(USERNAME).toString());
    }

}
