package com.home.controller;

import com.home.model.DebitCard;
import com.home.model.primitive.Number;
import com.home.model.primitive.Text;
import com.home.repository.DebitCardRepository;
import com.home.service.CardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/debit-client")
public class DebitController {
    @Value("${eureka.instance.instance-id}")
    String instance;

    @Autowired
    DebitCardRepository debitCardRepository;

    @Autowired
    CardDAO cardDAO;

//    @GetMapping("/test")
//    @ResponseBody
//    public String test() {
//        return "Hello on " + instance;
//    }

    @GetMapping("/debit")
    @ResponseBody
    public DebitCard debits(@RequestParam("id") int id,
                         Model model) {
//        model.addAttribute("card", debitCardRepository.findById(id).orElse(null));
//        return "debitCards/view";
        return debitCardRepository.findById(id).orElse(null);
    }

    @GetMapping("/test")
    @ResponseBody
    public DebitCard anotherDebits(@RequestParam("id") int id) {
        return new RestTemplate().getForObject("http://localhost:8082/debit-client/debit?id={id}",
                DebitCard.class,
                id);
    }

//    @GetMapping("/transfer/debitToDebit")
//    public String transferDebitTo(Model model, HttpServletRequest request) {
//        Account account =  accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//        cardDAO.findAllDebitCardsByAccountId(account.getId()).forEach(System.out::println);
//        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));
//
//        model.addAttribute("ids", new Text());
//        model.addAttribute("money", new Number());
//
//        System.out.println("It's time");
//        model.asMap().forEach((str, obj) -> System.out.println(str + " --- " + obj));
//
//        return "debitCards/transfer";
//    }

    @PostMapping("/transfer/debitToDebit")
    public String transferDebitToPost(@RequestParam("from") int from,
                                      @RequestParam("to") int to,
                                      @RequestParam("money") double number) {

//        String[] strings = ids.getText().split(",");

        DebitCard debitFrom = cardDAO.findDebitCardById(from);
        DebitCard debitTo = cardDAO.findDebitCardById(to);

//        System.out.println(ids + " --- " + number);

        if (cardDAO.transferMoneyFromTo(debitFrom, debitTo, number)) {
            //transactionDAO.saveDebitTransaction(new DebitTransaction(from, to, number.getNumber()));
        }

        return "redirect:http://localhost:8082/";
    }

//    @GetMapping("/transfer/debitToSaving")
//    public String transferDebitToSavingsPage(Model model, HttpServletRequest request) {
//        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//
//        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));
//        model.addAttribute("savings", cardDAO.findAllSavingsByAccountId(account.getId()));
//
//        model.addAttribute("ids", new Text());
//        model.addAttribute("money", new Number());
//
//        return "debitCards/transferToSaving";
//    }

//    @PostMapping("/transfer/debitToSaving")
//    public String transferDebitToSavings(@RequestParam("from") int from,
//                                         @RequestParam("to") int to,
//                                         @RequestParam("money") double number) {
////        String[] strings = ids.getText().split(",");
//
//        DebitCard debitFrom = cardDAO.findDebitCardById(from);
////        Saving to = cardDAO.findSavingById();
//
//        cardDAO.transferMoneyFromTo(from, to, money.getNumber().doubleValue());
//
//        return "redirect:/";
//    }
}
