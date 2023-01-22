package com.home.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "debitCards")
public class DebitCard extends Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double money;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "account_id", nullable = false)
    private int accountId;

    public DebitCard() {
    }

    public DebitCard(int accountId) {
        this.money = 0.;
        this.date = Date.valueOf(LocalDate.now());
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "DebitCard{" +
                "id=" + id +
                ", money=" + money +
                ", accountId=" + accountId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean accrueMoney(Double money) {
        this.money += money;
        return true;
    }

    @Override
    public boolean takeMoney(Double takingMoney) {
        if (this.money >= takingMoney) {
            this.money -= takingMoney;
            return true;
        }
        return false;
    }
}
