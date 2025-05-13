package com.bank.backend.service;

public interface IJWTService {
    String generateToken(String accountNumber);
    /**
     * Return the subject
     * */
    String validateToken(String token);
}
