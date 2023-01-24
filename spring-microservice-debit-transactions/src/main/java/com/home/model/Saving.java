package com.home.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "savings")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    private Double percent;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "account_id", nullable = false)
    private Integer account;

    public Saving() {
    }
    @Override
    public String toString() {
        return "Saving{" +
                "id=" + id +
                ", money=" + money +
                ", percent=" + percent +
                ", accountId=" + account +
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
        return account;
    }
}
