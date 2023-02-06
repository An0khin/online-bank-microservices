package com.home.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.home.model.TokenService;
import com.home.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class DefaultTokenService implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secretKey;
    private final AccountRepository accountRepository;

    public DefaultTokenService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public String generateToken(String login, String role) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(10, ChronoUnit.MINUTES);

        return JWT.create()
                .withIssuer("security-client") //Кто выдает токен
                .withAudience("debit-client", "saving-client", "credit-client") //Для кого предназначается
                .withSubject(login) //Поле по которому формируется токен
                .withClaim("role", role)
                .withIssuedAt(now) //Время формирования
                .withExpiresAt(exp) //Длительность жизни
                .sign(algorithm); //Алгоритм формирования
    }
}
