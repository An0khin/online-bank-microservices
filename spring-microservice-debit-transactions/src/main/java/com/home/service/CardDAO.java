package com.home.service;

import com.home.model.DebitCard;
import com.home.model.Card;
import com.home.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardDAO {
    @Autowired
    private DebitCardRepository debitCardRepository;
    //private final DebitTransactionRepository debitTransactionRepository;

    public CardDAO() {
//        this.debitTransactionRepository = debitTransactionRepository;
    }

    //Debit cards
    public void saveDebitCard(DebitCard card) {
        debitCardRepository.save(card);
    }

    public List<DebitCard> findAllDebitCardsByAccountId(Integer id) {
        return debitCardRepository.findByAccountId(id);
    }

    public DebitCard findDebitCardById(Integer id) {
        return debitCardRepository.findById(id).orElse(null);
    }

    public boolean transferMoneyFromTo(Card from, Card to, Double money) {
        if(from.transferTo(to, money)) {
            debitCardRepository.save((DebitCard) from);
            debitCardRepository.save((DebitCard) to);
            return true;
        }
        return false;
    }
}
