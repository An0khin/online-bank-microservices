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
        accountRepository.save(new AccountEntity(account.getLogin(), password_hash, role_hash, account.getPassport()));
    }

    @Override
    public Account getAccount(String username, String password) {
        Optional<AccountEntity> optionalAccount = accountRepository.findByLogin(username);
        if(optionalAccount.isEmpty()) {
            throw new LoginException(String.format("Account with %s login doesn't exists", username));
        }

        AccountEntity accountEntity = optionalAccount.get();

        if(!BCrypt.checkpw(password, accountEntity.getPassword())) {
            throw new LoginException("Password is incorrect");
        }

        return new Account(
                accountEntity.getLogin(),
                accountEntity.getPassword(),
                accountEntity.getRole(),
                accountEntity.getPassport());
    }
}
