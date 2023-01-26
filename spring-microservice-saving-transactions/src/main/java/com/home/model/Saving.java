package com.home.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "templates/savings")
public class Saving extends Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "savings_id_seq")
    private Integer id;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    private Double percent;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    public Saving() {
    }

    public Saving(Integer accountId) {
        this.money = 0.;
        this.percent = 3.;
        this.date = Date.valueOf(LocalDate.now());
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Saving{" +
                "id=" + id +
                ", money=" + money +
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
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
