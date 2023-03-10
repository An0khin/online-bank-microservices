package com.home.service;

import com.home.dao.AccountEntity;
import com.home.model.Account;
import com.home.model.AccountService;
import com.home.model.exception.LoginException;
import com.home.model.exception.RegistrationException;
import com.home.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAccountService implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void register(Account account) {
        if(accountRepository.findByLogin(account.getLogin()).isPresent()) {
            throw new RegistrationException(String.format("Account with %s login already exists", account.getLogin()));
        }

        String password_hash = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        String role_hash = BCrypt.hashpw(account.getRole(), BCrypt.gensalt());
        accountRepository.save(new AccountEntity(account.getLogin(), password_hash, role_hash));
    }

    @Override
    public void checkCredentials(Account account) {
        Optional<AccountEntity> optionalAccount = accountRepository.findByLogin(account.getLogin());
        if(optionalAccount.isEmpty()) {
            throw new LoginException(String.format("Account with %s login doesn't exists", account.getLogin()));
        }

        AccountEntity accountEntity = optionalAccount.get();

        if(!BCrypt.checkpw(account.getPassword(), accountEntity.getPassword()) ||
                !BCrypt.checkpw(account.getRole(), accountEntity.getRole())) {
            throw new LoginException("Password is incorrect");
        }
    }
}
