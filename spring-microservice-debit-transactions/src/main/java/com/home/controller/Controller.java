package com.home.controller;

import com.home.model.DebitCard;
import com.home.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/debit-client")
public class Controller {
    @Value("${eureka.instance.instance-id}")
    String instance;

    @Autowired
    DebitCardRepository debitCardRepository;

    @GetMapping("/test")
    public String test() {
        return "Hello on " + instance;
    }

    @GetMapping("/debit")
    public DebitCard debits(@RequestParam("id") int id) {
        return debitCardRepository.findById(id).orElse(null);
    }
}
