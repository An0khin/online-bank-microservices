package com.home.security.dao;

import lombok.Value;

@Value
public class Account {
    String login;
    String password;
    String role;
}
