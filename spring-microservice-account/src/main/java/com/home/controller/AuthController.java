package com.home.controller;

import com.home.model.Account;
import com.home.model.Passport;
import com.home.service.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    public static final String URL = "http://localhost:8082/";
    public static final String OWNER_ROLE = "ROLE_OWNER";
    @Value("${owner.username}")
    public String OWNER_USERNAME;
    @Value("${owner.password}")
    public String OWNER_PASSWORD;

    @Autowired
    private AccountService accountService;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("newPassport", new Passport());
        model.addAttribute("newAccount", new Account());

        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("newAccount") Account account,
                           @ModelAttribute("newPassport") @Valid Passport passport,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "register";
        }

        account.setPassport(passport);
        accountService.register(account);

        passport.setAccount(account);
        accountService.savePassport(passport);

        return "redirect:" + URL;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String getToken(HttpServletResponse response,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password) {
        String role = OWNER_ROLE;

        if(!username.equals(OWNER_USERNAME) || !password.equals(OWNER_PASSWORD)) {
            Account account = accountService.getAccount(username, password);
            role = account.getRole();
        }

        String authorizationToken = restTemplate.getForObject(URL + "token/new_token?username={login}&role={role}",
                String.class,
                username,
                role);

        String refreshToken = restTemplate.getForObject(URL + "token/new_refresh_token?username={login}&role={role}",
                String.class,
                username,
                role);

        log.info(authorizationToken);
        log.info(refreshToken);

        Cookie authorizationCookie = new Cookie("Authorization", authorizationToken);
        authorizationCookie.setPath("/");
        Cookie refreshCookie = new Cookie("Refresh", refreshToken);
        refreshCookie.setPath("/");

        response.addCookie(authorizationCookie);
        response.addCookie(refreshCookie);

        return "redirect:" + URL;
    }
}
