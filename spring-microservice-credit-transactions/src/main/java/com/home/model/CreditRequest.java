package com.home.model;

import jakarta.persistence.*;

@Entity
@Table(name = "creditRequests")
public class CreditRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "borrower_id")
    private String borrower;

    @Column
    private Double desiredLimit;

    @Column
    private Double percent;

    @Column(name = "creditor_id")
    private String creditor;

    @Column
    private boolean accepted;

    @Column
    private boolean viewed;

    public CreditRequest() {
    }

    @Override
    public String toString() {
        return "CreditRequest{" +
                "id=" + id +
                ", borrower=" + borrower +
                ", desiredLimit=" + desiredLimit +
                ", percent=" + percent +
                ", creditor=" + creditor +
                ", accepted=" + accepted +
                ", viewed=" + viewed +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public Double getDesiredLimit() {
        return desiredLimit;
    }

    public void setDesiredLimit(Double desiredLimit) {
        this.desiredLimit = desiredLimit;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}