package com.home.controller;

import com.home.service.CreditDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CreditDAO creditDAO;

    static final String URL = "http://localhost:8082";


//    @GetMapping("/newCreditRequest")
//    public String newCreditRequestPage(Model model) {
//
//        model.addAttribute("creditRequest", new CreditRequest());
//
//        return "creditCards/newCreditRequest";
//    }
//
//    @PostMapping("/newCreditRequest")
//    public String newCreditRequest(@ModelAttribute("creditRequest") CreditRequest creditRequest,
//                                   HttpServletRequest request) {
//        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//        creditRequest.setBorrower(account);
//
//        cardDAO.saveCreditRequest(creditRequest);
//
//        return "redirect:/";
//    }
//
//    @GetMapping("/takeCredit")
//    public String takeCreditPage(Model model,
//                                 HttpServletRequest request) {
//
//        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//
//        model.addAttribute("creditCards", account.getCreditCards());
//        model.addAttribute("id", new Text());
//        model.addAttribute("money", new Number());
//
//        return "creditCards/takeCredit";
//    }
//
//    @PostMapping("/takeCredit")
//    public String takeCredit(@ModelAttribute("id") Text id,
//                             @ModelAttribute("money") Number money,
//                             BindingResult result) {
//
//        CreditCard creditCard = cardDAO.findCreditCardById(Integer.valueOf(id.getText()));
//
//        if(transactionDAO.findAllNotClosedCreditLoansByCreditCardId(creditCard.getId()) != 0) {
//            return "creditCards/alreadyHasCredit";
//        }
//
//        if(!creditCard.takeCreditMoney(money.getNumber())) {
//            result.addError(new FieldError("money", "number", "Must be less or equals " + creditCard.getMoneyLimit()));
//            return "creditCards/takeCredit";
//        }
//
//        transactionDAO.saveCreditLoan(new CreditLoan(creditCard, money.getNumber()));
//        cardDAO.saveCreditCard(creditCard);
//
//        return "redirect:/";
//    }
//
//
//    //Read the data
//    @GetMapping("/allCreditCards")
//    public String allCreditCardsPage(Model model,
//                                     HttpServletRequest request) {
//        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//
//        model.addAttribute("creditCards", cardDAO.findAllCreditCardsByAccountId(account.getId()));
//
//        return "creditCards/allCreditCards";
//    }
//
//    @GetMapping("/creditCard")
//    public String viewCreditCard(@RequestParam("id") Integer id,
//                                 Model model) {
//        model.addAttribute("card", cardDAO.findCreditCardById(id));
//
//        return "creditCards/view";
//    }
//
//    @GetMapping("/allCreditRequests")
//    public String allCreditRequests(Model model,
//                                    HttpServletRequest request) {
//
//        Integer id = accountDAO.findAccountByLogin(request.getUserPrincipal().getName()).getId();
//
//        model.addAttribute("accepted", cardDAO.findAcceptedCreditRequestsByAccountId(id));
//        model.addAttribute("declined", cardDAO.findDeclinedCreditRequestsByAccountId(id));
//        model.addAttribute("notViewed", cardDAO.findNotViewedCreditRequestsByAccountId(id));
//
//        return "creditCards/allCreditRequests";
//    }
//
//    @GetMapping("/transfer/creditToDebit")
//    public String transferCreditToDebitPage(Model model, HttpServletRequest request) {
//        Account account = accountDAO.findAccountByLogin(request.getUserPrincipal().getName());
//
//        model.addAttribute("debitCards", cardDAO.findAllDebitCardsByAccountId(account.getId()));
//        model.addAttribute("credits", cardDAO.findAllCreditCardsByAccountId(account.getId()));
//
//        model.addAttribute("ids", new Text());
//        model.addAttribute("money", new Number());
//
//        return "creditCards/transferToDebit";
//    }
//
//    @PostMapping("/transfer/creditToDebit")
//    public String transferCreditToDebit(@ModelAttribute("ids") Text ids, @ModelAttribute("money") Number money) {
//        String[] strings = ids.getText().split(",");
//
//        DebitCard to = cardDAO.findDebitCardById(Integer.valueOf(strings[1]));
//        CreditCard from = cardDAO.findCreditCardById(Integer.valueOf(strings[0]));
//
//        cardDAO.transferMoneyFromTo(from, to, money.getNumber().doubleValue());
//
//        return "redirect:/";
//    }
}
