package com.home.security;

import com.home.exception.LoginException;
import com.home.exception.RegistrationException;
import com.home.model.AccountEntity;
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
    public void register(String login, String clientSecret) {
        if(accountRepository.findByLogin(login).isPresent()) {
            throw new RegistrationException(String.format("Account with %s login already exists", login));
        }

        String hash = BCrypt.hashpw(clientSecret, BCrypt.gensalt());
        accountRepository.save(new AccountEntity(login, hash));
    }

    @Override
    public void checkCredentials(String login, String clientSecret) {
        Optional<AccountEntity> optionalAccount = accountRepository.findByLogin(login);
        if(optionalAccount.isEmpty()) {
            throw new LoginException(String.format("Account with %s login doesn't exists", login));
        }

        AccountEntity accountEntity = optionalAccount.get();

        if(!BCrypt.checkpw(clientSecret, accountEntity.getHash())) {
            throw new LoginException("Secret is incorrect");
        }
    }
}
