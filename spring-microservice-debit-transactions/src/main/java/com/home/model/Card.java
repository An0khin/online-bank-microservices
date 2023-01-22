package com.home.model;

public abstract class Card {
    public boolean transferTo(Card other, Double otherMoney) {
        if(takeMoney(otherMoney)) {
            other.accrueMoney(otherMoney);
            return true;
        }
        return false;
    }

    public abstract boolean accrueMoney(Double money);

    public abstract boolean takeMoney(Double takingMoney);
}
