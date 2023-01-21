package com.home.controller;

import com.home.model.DebitCard;
import com.home.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/debit-client")
public class DebitController {
    @Value("${eureka.instance.instance-id}")
    String instance;

    @Autowired
    DebitCardRepository debitCardRepository;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello on " + instance;
    }

    @GetMapping("/debit")
    public String debits(@RequestParam("id") int id,
                         Model model) {
        model.addAttribute("card", debitCardRepository.findById(id).orElse(null));
        return "debitCards/view";
    }
}
