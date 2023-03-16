package com.home.service;

public interface TokenService {
    String generateToken(String login, String role);

    String generateRefreshToken(String login, String role);
}
