package com.home.controller;

import com.home.model.Account;
import com.home.model.AccountService;
import com.home.model.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@Controller
@Slf4j
public class AuthController {
    public static String URL = "http://localhost:8082/";
    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role,
                           @RequestParam("passport") int passport) {
        Account account = new Account(username, password, role, passport);
        accountService.register(account);
        return "redirect:" + URL;
//        return ResponseEntity.ok("Registered");
    }

    @GetMapping("/token")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/token")
    public void getToken(HttpServletResponse response,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password) {
//        Account account = new Account(username, password, "ROLE_" + role, 0);
        Account account = accountService.getAccount(username, password);

        response.addCookie(new Cookie("Authorization", "Bearer_" + tokenService.generateToken(account.getLogin(), account.getRole())));
        response.addCookie(new Cookie("Refresh", tokenService.generateRefreshToken(account.getLogin(), account.getRole())));
        try {
            response.sendRedirect(URL);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/new_token")
    @ResponseBody
    public String getNewToken(@RequestParam("username") String username,
                              @RequestParam("role") String role) {
        return "Bearer_" + tokenService.generateToken(username, role);
    }

    @GetMapping("/new_refresh_token")
    @ResponseBody
    public String getNewRefreshToken(@RequestParam("username") String username,
                                     @RequestParam("role") String role) {
        return tokenService.generateRefreshToken(username, role);
    }
}
