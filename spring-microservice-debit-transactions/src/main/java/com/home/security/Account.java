package com.home.security;

import lombok.Value;

@Value
public class Account {
    String login;
    String password;
    String role;
}
