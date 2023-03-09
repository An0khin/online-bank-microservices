package com.home.controller;

import com.home.model.CreditCard;
import com.home.model.CreditLoan;
import com.home.model.CreditRequest;
import com.home.service.CreditDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CreditDAO creditDAO;

    static final String URL = "http://localhost:8082";


    @GetMapping("/accrue")
    @ResponseBody
    public Boolean accrue(@RequestParam("id") int id,
                          @RequestParam("money") double money) {
        return creditDAO.accrueMoneyById(id, money);
    }

    @GetMapping("/take")
    @ResponseBody
    public Boolean take(@RequestParam("id") int id,
                        @RequestParam("money") double money) {
        return creditDAO.takeMoneyById(id, money);
    }

    @GetMapping("/get")
    @ResponseBody
    public CreditCard getById(@RequestParam("id") int id) {
        return creditDAO.findCreditCardById(id);
    }

    @GetMapping("/get_all")
    @ResponseBody
    public List<CreditCard> getAllByAccountId(@RequestParam("accountId") String accountId) {
        return creditDAO.findAllCreditCardsByAccountId(accountId);
    }

    @GetMapping("/order_new")
    public String createNewCreditCardPage(Model model) {
        model.addAttribute("flag", "");

        return "creditCards/createNew";
    }

    @PostMapping("/order_new")
    public String createNewCreditCard(@RequestParam(value = "agree", required = false, defaultValue = "false") boolean agree,
                                      Model model) {
        if(agree) {
            String accountId = SecurityContextHolder.getContext().getAuthentication().getName(); //Find current user's id from another service

            creditDAO.saveCreditCard(new CreditCard(accountId));

            return "redirect:" + URL;
        } else {
            model.addAttribute("flag", "You haven't read the agreement!");
            return "creditCards/createNew";
        }
    }

    @GetMapping("/view")
    public String viewCredit(@RequestParam("id") int id,
                             Model model) {
        model.addAttribute("card", creditDAO.findCreditCardById(id));
        return "creditCards/view";
    }

    @GetMapping("/transfer/to_debit")
    public String transferCreditToDebit(Model model) {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("creditCards", creditDAO.findAllCreditCardsByAccountId(accountId));

        return "creditCards/transfer";
    }

    @PostMapping("/transfer/to_debit")
    public String transferCreditToDebitPost(@RequestParam("from") int from,
                                            @RequestParam("to") int to,
                                            @RequestParam("money") double money) {

        CreditCard creditFrom = creditDAO.findCreditCardById(from);

        if(creditFrom.takeMoney(money)) {
            restTemplate.getForObject(URL + "/debit/accrue?id={id}&money={money}",
                    Boolean.class,
                    to, money);
            creditDAO.saveCreditCard(creditFrom);
        }

        return "redirect:" + URL;
    }

    @GetMapping("/newCreditRequest")
    public String newCreditRequestPage(Model model) {

        model.addAttribute("creditRequest", new CreditRequest());

        return "creditCards/newCreditRequest";
    }

    @PostMapping("/newCreditRequest")
    public String newCreditRequest(@ModelAttribute("creditRequest") CreditRequest creditRequest) {
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        creditRequest.setBorrower(accountId);

        creditDAO.saveCreditRequest(creditRequest);

        return "redirect:/";
    }

    @GetMapping("/takeCredit")
    public String takeCreditPage(Model model) {

        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("creditCards", creditDAO.findAllCreditCardsByAccountId(accountId));
//        model.addAttribute("id", new Text());
//        model.addAttribute("money", new Number());

        return "creditCards/takeCredit";
    }

    @PostMapping("/takeCredit")
    public String takeCredit(@RequestParam("id") int id,
                             @RequestParam("money") double money,
                             Model model) {

        CreditCard creditCard = creditDAO.findCreditCardById(id);

        if(creditDAO.findAllNotClosedCreditLoansByCreditCardId(creditCard.getId()) != 0) {
            return "creditCards/alreadyHasCredit";
        }

        if(!creditCard.takeCreditMoney(money)) {
//            result.addError(new FieldError("money", "number", "Must be less or equals " + creditCard.getMoneyLimit()));
            model.addAttribute("flag", "Must be less or equals " + creditCard.getMoneyLimit());
            return "creditCards/takeCredit";
        }

        creditDAO.saveCreditLoan(new CreditLoan(creditCard, money));
        creditDAO.saveCreditCard(creditCard);

        return "redirect:/";
    }
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
}
