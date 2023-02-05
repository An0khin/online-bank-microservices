package com.home.controller;

import com.home.model.Account;
import com.home.security.AccountService;
import com.home.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/auth")
@Controller
@Slf4j
public class AuthController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public @ResponseBody ResponseEntity<String> register(@RequestParam("username") String username,
                                                         @RequestParam("password") String password,
                                                         @RequestParam("role") String role) {
        Account account = new Account(username, password, role);
        accountService.register(account);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/token")
    public void getToken(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role) {
        Account account = new Account(username, password, "ROLE_" + role);
        accountService.checkCredentials(account);

//        return ResponseEntity.ok(tokenService.generateToken(account.getLogin(), account.getRole()));
        log.info(request.getRequestURI());

        response.addCookie(new Cookie("Authorization", "Bearer_" + tokenService.generateToken(account.getLogin(), account.getRole())));
        try {
            response.sendRedirect("http://localhost:8082/debit/view?id=1");
        } catch(Exception e) {
            e.printStackTrace();
        }


    }

    @GetMapping("/token")
    public String loginForm() {
        return "login";
    }
}
