package com.home.repository;

import com.home.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    Optional<AccountEntity> findByLogin(String login);
}
