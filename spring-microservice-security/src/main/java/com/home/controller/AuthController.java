package com.home.controller;

import com.home.model.Account;
import com.home.security.AccountService;
import com.home.security.TokenResponse;
import com.home.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody Account account) {
        accountService.register(account);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public TokenResponse getToken(@RequestBody Account account) {
        accountService.checkCredentials(account);
        return new TokenResponse(tokenService.generateToken(account.getLogin(), account.getRole()));
    }
}
