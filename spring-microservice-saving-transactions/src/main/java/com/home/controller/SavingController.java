package com.home.controller;

import com.home.model.DebitCard;
import com.home.model.Saving;
import com.home.service.SavingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public List<Saving> getAllByAccountId(@RequestParam("accountId") String accountId) {
        return savingDAO.findAllSavingsByAccountId(accountId);
    }

    @GetMapping("/order_new_s")
    public String createNewDebitCardPage(Model model) {
        model.addAttribute("flag", "");

        return "savings/createNew";
    }

    @PostMapping("/order_new_s")
    public String createNewSaving(@RequestParam(value = "agree", required = false, defaultValue = "false") boolean agree,
                                     Model model) {
        if(agree) {
            String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

            savingDAO.saveSaving(new Saving(accountId));

            return "redirect:" + URL;
        } else {
            model.addAttribute("flag", "You haven't read the agreement!");
            return "savings/createNew";
        }
    }

    @GetMapping("/view")
    public String viewDebit(@RequestParam("id") int id,
                            Model model) {
        model.addAttribute("card", savingDAO.findSavingById(id));
        return "savings/view";
    }

    @GetMapping("/transfer/to_debit")
    public String transferSavingToDebitPage(Model model) {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<DebitCard[]> debitEntity = restTemplate.getForEntity(URL + "/debit/getAll?accountId={accountId}",
                DebitCard[].class,
                accountId);
        DebitCard[] debitCards = debitEntity.getBody();


        model.addAttribute("debitCards", debitCards);
        model.addAttribute("savings", savingDAO.findAllSavingsByAccountId(accountId));

        return "savings/transfer";
    }

    @PostMapping("/transfer/to_debit")
    public String transferDebitToSavings(@RequestParam("from") int from,
                                         @RequestParam("to") int to,
                                         @RequestParam("money") double money) {
        Saving savingFrom = savingDAO.findSavingById(from);

        if (savingFrom.takeMoney(money)) {
            restTemplate.getForObject(URL + "/debit/accrue?id={id}&money={money}",
                    Boolean.class,
                    to, money);
            savingDAO.saveSaving(savingFrom);
        }

        return "redirect:" + URL;
    }

//
}