package com.home.repository;

import com.home.model.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    List<Saving> findByAccountId(Integer accountId);

}
