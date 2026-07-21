package br.com.whobetter.scoringservice.exception;

import java.util.UUID;

public class ScoreNotFoundException extends RuntimeException {
    public ScoreNotFoundException(UUID id) {
        super("Score não encontrado com id: " + id);
    }
}
