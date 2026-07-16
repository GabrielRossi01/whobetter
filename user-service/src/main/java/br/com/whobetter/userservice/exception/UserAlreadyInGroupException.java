package br.com.whobetter.userservice.exception;

import java.util.UUID;

public class UserAlreadyInGroupException extends RuntimeException {
    public UserAlreadyInGroupException(UUID userId, UUID groupId) {
        super("O usuário '%s' já é membro do grupo '%s'".formatted(userId, groupId));
    }
}
