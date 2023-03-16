package com.home.service;

import com.home.model.Account;
import com.home.model.Passport;
import com.home.model.exception.LoginException;
import com.home.model.exception.RegistrationException;
import com.home.repository.AccountRepository;
import com.home.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAccountService implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PassportRepository passportRepository;

    @Override
    public void savePassport(Passport passport) {
        passportRepository.save(passport);
    }

    @Override
    public Passport findPassportBySeriesAndNumber(int series, int number) {
        return passportRepository.findBySeriesAndNumber(series, number).orElse(null);
    }

    @Override
    public void register(Account account) {
        if(accountRepository.findByLogin(account.getLogin()).isPresent()) {
            throw new RegistrationException(String.format("Account with %s login already exists", account.getLogin()));
        }

        String password_hash = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
//        String role_hash = BCrypt.hashpw(account.getRole(), BCrypt.gensalt());

        accountRepository.save(
                new Account(account.getLogin(),
                        password_hash,
                        account.getRole(),
                        account.getPassport())
        );
    }

    @Override
    public Account getAccount(String username, String password) {
        Optional<Account> optionalAccount = accountRepository.findByLogin(username);
        if(optionalAccount.isEmpty()) {
            throw new LoginException(String.format("Account with %s login doesn't exists", username));
        }

        Account account = optionalAccount.get();

        if(!BCrypt.checkpw(password, account.getPassword())) {
            throw new LoginException("Password is incorrect");
        }

        return new Account(
                account.getLogin(),
                account.getPassword(),
                account.getRole(),
                account.getPassport());
    }
}
