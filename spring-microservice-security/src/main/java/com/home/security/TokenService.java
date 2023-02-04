package com.home.security;

public interface TokenService {
    String generateToken(String login, String role);
}
