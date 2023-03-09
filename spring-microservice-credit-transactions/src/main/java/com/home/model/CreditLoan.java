package com.home.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "creditLoans")
public class CreditLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(columnDefinition = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date wasClosed;

    @ManyToOne
    @JoinColumn(name = "creditCard", nullable = false)
    private CreditCard creditCard;

    @Column
    private Double money;

    @Column
    private Boolean closed;

    public CreditLoan() {
    }

    public CreditLoan(CreditCard creditCard, Double money) {
        this.creditCard = creditCard;
        this.money = money;
        this.closed = false;
        this.date = Date.valueOf(LocalDate.now());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getWasClosed() {
        return wasClosed;
    }

    public void setWasClosed(Date wasClosed) {
        this.wasClosed = wasClosed;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
