package com.home.controller;

import com.home.model.Saving;
import com.home.service.SavingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/saving")
public class SavingController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    SavingDAO savingDAO;

    static final String URL = "http://localhost:8082";

    @GetMapping("/accrue")
    @ResponseBody
    public Boolean accrue(@RequestParam("id") int id,
                          @RequestParam("money") double money) {
        return savingDAO.accrueMoneyById(id, money);
    }

    @GetMapping("/take")
    @ResponseBody
    public Boolean take(@RequestParam("id") int id,
                        @RequestParam("money") double money) {
        return savingDAO.takeMoneyById(id, money);
    }

    @GetMapping("/get")
    @ResponseBody
    public Saving getById(@RequestParam("id") int id) {
        return savingDAO.findSavingById(id);
    }

    @GetMapping("/get_all")
    @ResponseBody
    public List<Saving> getAllByAccountId(@RequestParam("accountId") int accountId) {
        return savingDAO.findAllSavingsByAccountId(accountId);
    }

//    @GetMapping("/orderNewS")
//    public String createNewDebitCardPage(Model model) {
//        model.addAttribute("flag", "");
//
//        return "debitCards/createNew";
//    }

//    @PostMapping("/orderNewS")
//    public String createNewDebitCard(@RequestParam(value = "agree", required = false, defaultValue = "false") boolean agree,
//                                     Model model) {
//        if(agree) {
//            //Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//            int accountId = 1; //Find current user's id from another service
//
//            debitDAO.saveDebitCard(new DebitCard(accountId));
//
//            return "redirect:http://localhost:8082/";
//        } else {
//            model.addAttribute("flag", "You haven't read the agreement!");
//            return "debitCards/createNew";
//        }
//    }

    @GetMapping("/view")
    public String viewDebit(@RequestParam("id") int id,
                            Model model) {
        model.addAttribute("card", savingDAO.findSavingById(id));
        return "savings/view";
    }
}