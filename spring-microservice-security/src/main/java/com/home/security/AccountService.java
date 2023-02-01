package com.home.security;

public interface AccountService {
    void register(String login, String clientSecret); //Регистрация юзера
    void checkCredentials(String login, String clientSecret); //Проверка аутентификационных данных
}
