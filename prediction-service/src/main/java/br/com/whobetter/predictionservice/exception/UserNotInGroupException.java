package br.com.whobetter.predictionservice.exception;

import java.util.UUID;

public class UserNotInGroupException extends RuntimeException {
    public UserNotInGroupException(UUID userId, UUID groupId) {
        super("User " + userId + " não é um membro do grupo " + groupId);
    }
}
