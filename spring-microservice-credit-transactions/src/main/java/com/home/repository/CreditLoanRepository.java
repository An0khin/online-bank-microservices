package com.home.repository;

import com.home.model.CreditLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditLoanRepository extends JpaRepository<CreditLoan, Integer> {
    long countByClosedFalseAndCreditCard_Id(Integer id);
}