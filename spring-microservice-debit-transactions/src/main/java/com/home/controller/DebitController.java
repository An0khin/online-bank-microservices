package com.home.controller;

import com.home.model.DebitCard;
import com.home.service.DebitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping
public class DebitController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DebitDAO debitDAO;

    static final String URL = "http://localhost:8082";


    @GetMapping("/debit/accrue")
    @ResponseBody
    public Boolean accrue(@RequestParam("id") int id,
                          @RequestParam("money") double money) {
        return debitDAO.accrueMoneyById(id, money);
    }

    @GetMapping("/debit/take")
    @ResponseBody
    public Boolean take(@RequestParam("id") int id,
                        @RequestParam("money") double money) {
       return debitDAO.takeMoneyById(id, money);
    }

    @GetMapping("/orderNewDC")
    public String createNewDebitCardPage(Model model) {
        model.addAttribute("flag", "");

        return "debitCards/createNew";
    }

    @PostMapping("/orderNewDC")
    public String createNewDebitCard(@RequestParam(value = "agree", required = false, defaultValue = "false") boolean agree,
                                     Model model) {
        if(agree) {
            //Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
            int accountId = 1; //Find current user's id

            debitDAO.saveDebitCard(new DebitCard(accountId));

            return "redirect:http://localhost:8082/";
        } else {
            model.addAttribute("flag", "You haven't read the agreement!");
            return "debitCards/createNew";
        }
    }

    @GetMapping("/debit/view")
    public String viewDebit(@RequestParam("id") int id,
                            Model model) {
        model.addAttribute("card", debitDAO.findDebitCardById(id));
        return "debitCards/view";
    }

    @GetMapping("/transfer/debitToDebit")
    public String transferDebitTo(Model model/*, HttpServletRequest request*/) {
        //Account account =  accountDAO.findAccountByLogin(request.getUserPrincipal().getName());

        int id = 1; //Finding current user's id

        model.addAttribute("debitCards", debitDAO.findAllDebitCardsByAccountId(1));

        return "debitCards/transfer";
    }

    @PostMapping("/transfer/debitToDebit")
    public String transferDebitToPost(@RequestParam("from") int from,
                                      @RequestParam("to") int to,
                                      @RequestParam("money") double money) {
        DebitCard debitFrom = debitDAO.findDebitCardById(from);
        DebitCard debitTo = debitDAO.findDebitCardById(to);

//        System.out.println(ids + " --- " + number);

//        if (debitDAO.transferMoneyFromTo(debitFrom, debitTo, number)) {
//            transactionDAO.saveDebitTransaction(new DebitTransaction(from, to, number.getNumber()));
//        }

        if(restTemplate.getForObject(URL + "/debit/take?id={id}&money={money}",
                Boolean.class,
                from, money))
            restTemplate.getForObject(URL + "/debit/accrue?id={id}&money={money}",
                    Boolean.class,
                    to, money);

        return "redirect:http://localhost:8082/";
    }

//LEARNING
    @Value("${eureka.instance.instance-id}")
    String instance;

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
        return debitDAO.findDebitCardById(id);
    }

    @GetMapping("/test")
    @ResponseBody
    public DebitCard anotherDebits(@RequestParam("id") int id) {
        return new RestTemplate().getForObject("http://localhost:8082/debit-client/debit?id={id}",
                DebitCard.class,
                id);
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
