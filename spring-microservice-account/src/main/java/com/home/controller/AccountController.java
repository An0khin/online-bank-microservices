package com.home.controller;

import com.home.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/account")
public class AccountController {
    public static String URL = "http://localhost:8082/";

    @Autowired
    public AccountService accountService;
    @Autowired
    public RestTemplate restTemplate;

    @GetMapping
    public String firstPage() {
        return "first";
    }
}
