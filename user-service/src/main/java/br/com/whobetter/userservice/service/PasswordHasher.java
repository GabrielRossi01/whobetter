package br.com.whobetter.userservice.service;

public interface PasswordHasher {
    String hash(String rawPassword);
}
