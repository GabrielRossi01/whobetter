package br.com.whobetter.userservice.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userId) {
        super("Usuário com ID '%s' não encontrado".formatted(userId));
    }
}
