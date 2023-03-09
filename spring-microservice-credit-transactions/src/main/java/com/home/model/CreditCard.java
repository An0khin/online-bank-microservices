package com.home.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "creditCards")
public class CreditCard extends Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double moneyLimit;

    @Column(nullable = false)
    private Double returnMoney;

    @Column(nullable = false)
    private Double currentMoney;

    @Column(nullable = false)
    private Double percent;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    public CreditCard() {
    }

    public CreditCard(String accountId) {
        this.accountId = accountId;
        this.date = Date.valueOf(LocalDate.now());
        this.returnMoney = 0.;
        this.currentMoney = 0.;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", moneyLimit=" + moneyLimit +
                ", currentMoney=" + currentMoney +
                ", percent=" + percent +
                ", accountId=" + accountId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(Double moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Double getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(Double currentMoney) {
        this.currentMoney = currentMoney;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccount() {
        return accountId;
    }

    public void setAccount(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean accrueMoney(Double money) {
        this.currentMoney += money;

        double min = Math.min(this.currentMoney, this.returnMoney);

        this.currentMoney -= min;
        this.returnMoney -= min;
        return true;
    }

    @Override
    public boolean takeMoney(Double takingMoney) {
        if(this.currentMoney >= takingMoney) {
            this.currentMoney -= takingMoney;

            return true;
        }

        return false;
    }

    public boolean takeCreditMoney(Double money) {
        if(this.moneyLimit >= money) {
            this.currentMoney += money;
            this.returnMoney += money + (money * percent / 100);

            return true;
        }

        return false;
    }
}
