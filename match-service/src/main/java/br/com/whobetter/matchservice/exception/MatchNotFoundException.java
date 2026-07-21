package br.com.whobetter.matchservice.exception;

import java.util.UUID;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(UUID id) {
        super("Partida não encontrada com id: " + id);
    }
}
