package com.home.service;

import com.home.model.CreditCard;
import com.home.model.CreditLoan;
import com.home.model.CreditRequest;
import com.home.repository.CreditLoanRepository;
import com.home.repository.CreditRepository;
import com.home.repository.CreditRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreditDAO {
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private CreditRequestRepository creditRequestRepository;
    @Autowired
    private CreditLoanRepository creditLoanRepository;

    public void saveCreditLoan(CreditLoan creditLoan) {
        creditLoanRepository.save(creditLoan);
    }

    public void saveCreditCard(CreditCard creditCard) {
        creditRepository.save(creditCard);
    }

    public void saveCreditRequest(CreditRequest creditRequest) {
        creditRequestRepository.save(creditRequest);
    }

    public List<CreditCard> findAllCreditCardsByAccountId(String accountId) {
        return creditRepository.findByAccountId(accountId);
    }

    public CreditCard findCreditCardById(int id) {
        return creditRepository.findById(id).get();
    }

    public int findAllNotClosedCreditLoansByCreditCardId(Integer id) {
        return (int) creditLoanRepository.countByClosedFalseAndCreditCard_Id(id);
    }

    public List<CreditRequest> findAcceptedCreditRequestsByAccountId(String accountId) {
        return creditRequestRepository.findByAcceptedTrueAndBorrower(accountId);
    }

    public List<CreditRequest> findDeclinedCreditRequestsByAccountId(String accountId) {
        return creditRequestRepository.findByViewedTrueAndAcceptedFalseAndBorrower(accountId);
    }

    public List<CreditRequest> findNotViewedCreditRequestsByAccountId(String accountId) {
        return creditRequestRepository.findByViewedFalseAndBorrower(accountId);
    }

    public Boolean accrueMoneyById(int id, double money) {
        CreditCard creditCard = findCreditCardById(id);
        if(creditCard.accrueMoney(money)) {
            saveCreditCard(creditCard);
            return true;
        }

        return false;
    }

    public Boolean takeMoneyById(int id, double money) {
        CreditCard creditCard = findCreditCardById(id);
        if(creditCard.takeMoney(money)) {
            saveCreditCard(creditCard);
            return true;
        }

        return false;
    }
}
