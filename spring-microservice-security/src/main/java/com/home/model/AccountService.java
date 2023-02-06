package com.home.model;

import com.home.model.Account;

public interface AccountService {
    void register(Account account); //Регистрация юзера
    void checkCredentials(Account account); //Проверка аутентификационных данных
}
