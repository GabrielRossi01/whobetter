package br.com.whobetter.predictionservice.exception;

import java.util.UUID;

public class DuplicatePredictionException extends RuntimeException {
    public DuplicatePredictionException(UUID matchId, UUID userId) {
        super("Predição já existente para partida " + matchId + " and user " + userId);
    }
}
