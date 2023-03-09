package com.home.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class Saving {
    private Integer id;

    private Double money;

    private Double percent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Integer accountId;

    public Saving() {
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

    public Double getMoney() {
        return money;
    }

    public Double getPercent() {
        return percent;
    }

    public Date getDate() {
        return date;
    }

    public Integer getAccountId() {
        return accountId;
    }
}
