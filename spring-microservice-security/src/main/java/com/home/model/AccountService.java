package com.home.model;

public interface AccountService {
    void register(Account account); //Регистрация юзера

    void checkCredentials(Account account); //Проверка аутентификационных данных
}
