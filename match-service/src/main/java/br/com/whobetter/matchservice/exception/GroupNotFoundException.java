package br.com.whobetter.matchservice.exception;

import java.util.UUID;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(UUID groupId) {
        super("Grupo não encontrado com id: " + groupId);
    }
}
