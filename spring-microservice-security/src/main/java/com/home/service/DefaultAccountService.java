package com.home.service;

import com.home.exception.LoginException;
import com.home.exception.RegistrationException;
import com.home.model.Account;
import com.home.model.AccountEntity;
import com.home.repository.AccountRepository;
import com.home.security.AccountService;
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

        String hash = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        String role_hash = BCrypt.hashpw(account.getRole(), BCrypt.gensalt());
        accountRepository.save(new AccountEntity(account.getLogin(), hash, role_hash));
    }

    @Override
    public void checkCredentials(Account account) {
        Optional<AccountEntity> optionalAccount = accountRepository.findByLogin(account.getLogin());
        if(optionalAccount.isEmpty()) {
            throw new LoginException(String.format("Account with %s login doesn't exists", account.getLogin()));
        }

        AccountEntity accountEntity = optionalAccount.get();

        if(!BCrypt.checkpw(account.getPassword(), accountEntity.getHash()) ||
            !BCrypt.checkpw(account.getRole() , accountEntity.getRole())) {
            throw new LoginException("Hash is incorrect");
        }
    }
}
