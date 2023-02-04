package com.home.model;

import lombok.Value;

import java.util.List;

@Value
public class Account {
    String login;
    String password;
    String role;
}
