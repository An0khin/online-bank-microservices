package com.home.controller;

import com.home.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/new_token")
    public String getNewToken(@RequestParam("username") String username,
                              @RequestParam("role") String role) {
        return "Bearer_" + tokenService.generateToken(username, role);
    }

    @GetMapping("/new_refresh_token")
    public String getNewRefreshToken(@RequestParam("username") String username,
                                     @RequestParam("role") String role) {
        return tokenService.generateRefreshToken(username, role);
    }
}
