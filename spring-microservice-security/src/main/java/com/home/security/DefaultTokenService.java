package com.home.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class DefaultTokenService implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Override
    public String generateToken(String login) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(5, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("security-client") //Кто выдает токен
                .withAudience("debit-client", "saving-client", "credit-client") //Для кого предназначается
                .withSubject(login) //Поле по которому формируется токен
                .withIssuedAt(now) //Время формирования
                .withExpiresAt(exp) //Длительность жизни
                .sign(algorithm); //Алгоритм формирования
    }
}
