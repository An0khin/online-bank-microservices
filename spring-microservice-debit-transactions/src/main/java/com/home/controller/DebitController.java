package com.home.controller;

import com.home.model.DebitCard;
import com.home.model.Saving;
import com.home.service.DebitDAO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/debit")
@Slf4j
public class DebitController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DebitDAO debitDAO;

    static final String URL = "http://localhost:8082";


    @GetMapping("/accrue")
    @ResponseBody
    public Boolean accrue(@RequestParam("id") int id,
                          @RequestParam("money") double money) {
        return debitDAO.accrueMoneyById(id, money);
    }

    @GetMapping("/take")
    @ResponseBody
    public Boolean take(@RequestParam("id") int id,
                        @RequestParam("money") double money) {
        return debitDAO.takeMoneyById(id, money);
    }

    @GetMapping("/get")
    @ResponseBody
    public DebitCard getById(@RequestParam("id") int id) {
        return debitDAO.findDebitCardById(id);
    }

    @GetMapping("/get_all")
    @ResponseBody
    public List<DebitCard> getAllByAccountId(@RequestParam("accountId") String accountId) {
        return debitDAO.findAllDebitCardsByAccountId(accountId);
    }

    @GetMapping("/order_new_dc")
    public String createNewDebitCardPage(Model model) {
        model.addAttribute("flag", "");

        return "debitCards/createNew";
    }

    @PostMapping("/order_new_dc")
    public String createNewDebitCard(@RequestParam(value = "agree", required = false, defaultValue = "false") boolean agree,
                                     Model model) {
        if (agree) {
            String accountId = SecurityContextHolder.getContext().getAuthentication().getName(); //Find current user's id from another service

            debitDAO.saveDebitCard(new DebitCard(accountId));

            return "redirect:" + URL;
        } else {
            model.addAttribute("flag", "You haven't read the agreement!");
            return "debitCards/createNew";
        }
    }

    @GetMapping("/view")
    public String viewDebit(@RequestParam("id") int id,
                            Model model) {
        model.addAttribute("card", debitDAO.findDebitCardById(id));
        return "debitCards/view";
    }

    @GetMapping("/transfer/to_debit")
    public String transferDebitTo(Model model) {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("debitCards", debitDAO.findAllDebitCardsByAccountId(accountId));

        return "debitCards/transfer";
    }

    @PostMapping("/transfer/to_debit")
    public String transferDebitToPost(@RequestParam("from") int from,
                                      @RequestParam("to") int to,
                                      @RequestParam("money") double money) {
        DebitCard debitFrom = debitDAO.findDebitCardById(from);
        DebitCard debitTo = debitDAO.findDebitCardById(to);

        if (debitFrom.takeMoney(money)) {
            debitTo.accrueMoney(money);
            debitDAO.saveDebitCard(debitFrom);
            debitDAO.saveDebitCard(debitTo);
        }

        return "redirect:" + URL;
    }

    @GetMapping("/transfer/to_saving")
    public String transferDebitToSavingsPage(Model model) {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        ResponseEntity<Saving[]> savingEntity = restTemplate.getForEntity(URL + "/saving/getAll?accountId={accountId}",
                Saving[].class,
                accountId);
        Saving[] savings = savingEntity.getBody();


        model.addAttribute("debitCards", debitDAO.findAllDebitCardsByAccountId(accountId));
        model.addAttribute("savings", savings);

        return "debitCards/transferToSaving";
    }

    @PostMapping("/transfer/to_saving")
    public String transferDebitToSavings(@RequestParam("from") int from,
                                         @RequestParam("to") int to,
                                         @RequestParam("money") double money) {
        DebitCard debitFrom = debitDAO.findDebitCardById(from);

        if (debitFrom.takeMoney(money)) {
            restTemplate.getForObject(URL + "/saving/accrue?id={id}&money={money}",
                    Boolean.class,
                    to, money);
            debitDAO.saveDebitCard(debitFrom);
        }

        return "redirect:/";
    }

//    @GetMapping("/transfer/debitToCredit")
//    public String transferDebitToCreditPage(Model model) {
//        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//
//        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));
//        model.addAttribute("credits", cardDAO.findAllCreditCardsByAccountId(account.getId()));
//
//        model.addAttribute("ids", new Text());
//        model.addAttribute("money", new Number());
//
//        return "debitCards/transferToCredit";
//    }
//
//    @PostMapping("/transfer/debitToCredit")
//    public String transferDebitToCredit(@ModelAttribute("ids") Text ids, @ModelAttribute("money") Number money) {
//        String[] strings = ids.getText().split(",");
//
//        DebitCard from = cardDAO.findDebitCardById(Integer.valueOf(strings[0]));
//        CreditCard to = cardDAO.findCreditCardById(Integer.valueOf(strings[1]));
//
//        cardDAO.transferMoneyFromTo(from, to, money.getNumber().doubleValue());
//
//        if(to.getReturnMoney() == 0) {
//            transactionDAO.setClosedToCreditLoanByCreditCard(to);
//        }
//
//        return "redirect:/";
//    }

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
