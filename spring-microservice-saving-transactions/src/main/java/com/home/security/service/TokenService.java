package com.home.security.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.home.security.SecretType;

public interface TokenService {
    DecodedJWT getDecodedToken(String token, SecretType type) throws JWTVerificationException;
    boolean checkToken(String token, SecretType type);
    String getRole(String token, SecretType type);
    String getName(String token, SecretType type);
}
