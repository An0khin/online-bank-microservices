package com.home.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.function.Function;

public interface TokenService {
    DecodedJWT getDecodedToken(String token) throws JWTVerificationException;
    boolean checkToken(String token);

    String getRole(String token);

}
