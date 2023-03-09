package com.home.model;

public interface TokenService {
    String generateToken(String login, String role);

    String generateRefreshToken(String login, String role);
}
