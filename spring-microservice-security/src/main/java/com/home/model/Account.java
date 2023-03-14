package com.home.model;

import lombok.Value;

@Value
public class Account {
    String login;
    String password;
    String role;
    int passport;
}
