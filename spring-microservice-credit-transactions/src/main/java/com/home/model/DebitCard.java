package com.home.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

public class DebitCard {
    private Integer id;

    private Double money;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

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

    public Double getMoney() {
        return money;
    }

    public Date getDate() {
        return date;
    }

    public int getAccountId() {
        return accountId;
    }
}
