package br.com.whobetter.predictionservice.exception;

import java.util.UUID;

public class PredictionNotFoundException extends RuntimeException {
    public PredictionNotFoundException(UUID id) {
        super("Predição não encontrada com id: " + id);
    }
}
