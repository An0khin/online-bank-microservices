package com.home.service;

import com.home.model.Account;
import com.home.model.Passport;

public interface AccountService {
    void savePassport(Passport passport);

    Passport findPassportBySeriesAndNumber(int series, int number);

    void register(Account account); //Регистрация юзера

    Account getAccount(String username, String password); //Получение аккаунта по логину и паролю

}
