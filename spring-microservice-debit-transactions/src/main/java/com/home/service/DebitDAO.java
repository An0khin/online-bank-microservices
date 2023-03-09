package com.home.service;

import com.home.model.DebitCard;
import com.home.repository.DebitCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebitDAO {
    @Autowired
    private DebitCardRepository debitCardRepository;

    public DebitDAO() {
    }

    //Debit cards
    public void saveDebitCard(DebitCard card) {
        debitCardRepository.save(card);
    }

    public List<DebitCard> findAllDebitCardsByAccountId(String id) {
        return debitCardRepository.findByAccountId(id);
    }

    public DebitCard findDebitCardById(Integer id) {
        return debitCardRepository.findById(id).orElse(null);
    }

    public Boolean accrueMoneyById(int id, double money) {
        DebitCard debitCard = findDebitCardById(id);
        if(debitCard.accrueMoney(money)) {
            saveDebitCard(debitCard);
            return true;
        }

        return false;
    }

    public Boolean takeMoneyById(int id, double money) {
        DebitCard debitCard = findDebitCardById(id);
        if(debitCard.takeMoney(money)) {
            saveDebitCard(debitCard);
            return true;
        }

        return false;
    }

//    public boolean transferMoneyFromTo(Card from, Card to, Double money) {
//        if(from.transferTo(to, money)) {
//            debitCardRepository.save((DebitCard) from);
//            debitCardRepository.save((DebitCard) to);
//            return true;
//        }
//        return false;
//    }
}
