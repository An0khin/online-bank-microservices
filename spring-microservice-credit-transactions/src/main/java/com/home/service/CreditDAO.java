package com.home.service;

import com.home.model.CreditCard;
import com.home.model.CreditLoan;
import com.home.model.CreditRequest;

import java.util.List;

public class CreditDAO {
    public List<CreditCard> findAllCreditCardsByAccountId(String accountId) {
        return null;
    }

    public CreditCard findCreditCardById(int id) {
        return null;
    }

    public void saveCreditLoan(CreditLoan creditLoan) {
    }

    public void saveCreditCard(CreditCard creditCard) {
    }

    public int findAllNotClosedCreditLoansByCreditCardId(Integer id) {
        return 0;
    }

    public void saveCreditRequest(CreditRequest creditRequest) {
    }

    public boolean takeMoneyById(int id, double money) {
        return false;
    }

    public boolean accrueMoneyById(int id, double money) {
        return false;
    }

    public List<CreditRequest> findAcceptedCreditRequestsByAccountId(String accountId) {
        return null;
    }

    public List<CreditRequest> findDeclinedCreditRequestsByAccountId(String accountId) {
        return null;
    }

    public List<CreditRequest> findNotViewedCreditRequestsByAccountId(String accountId) {
        return null;
    }
}
