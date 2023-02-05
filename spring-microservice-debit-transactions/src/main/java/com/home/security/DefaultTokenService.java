package com.home.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultTokenService implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secret;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public DecodedJWT getDecodedToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT;
        } catch(JWTVerificationException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkToken(String token) {
        DecodedJWT decodedJWT = getDecodedToken(token);

        if(decodedJWT == null) {
            return false;
        }

        log.info(decodedJWT.getIssuer());
        if(!decodedJWT.getIssuer().equals("security-client")) {
            return false;
        }

        log.info("audience");
        log.info(applicationName);
        decodedJWT.getAudience().stream().forEach(log::info);
        if(!decodedJWT.getAudience().contains(applicationName)) {
            log.info(applicationName);
            return false;
        }

        return true;
    }

    @Override
    public String getRole(String token) {
        DecodedJWT decodedJWT = getDecodedToken(token);

        return decodedJWT.getClaim("role").asString();
    }
}
