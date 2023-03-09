package com.home.repository;

import com.home.model.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRequestRepository extends JpaRepository<CreditRequest, Integer> {
    List<CreditRequest> findByViewedFalseAndBorrower(String borrower);
    List<CreditRequest> findByViewedTrueAndAcceptedFalseAndBorrower(String borrower);
    List<CreditRequest> findByAcceptedTrueAndBorrower(String borrower);
}