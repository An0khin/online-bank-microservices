package com.home.model;

public interface AccountService {
    void register(Account account); //Регистрация юзера

    Account getAccount(String username, String password); //Получение аккаунта по логину и паролю
}
