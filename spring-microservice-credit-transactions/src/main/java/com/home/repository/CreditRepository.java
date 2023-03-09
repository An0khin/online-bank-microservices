package com.home.repository;

import com.home.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CreditRepository extends JpaRepository<CreditCard, Integer> {
    List<CreditCard> findByAccountId(String accountId);

}
