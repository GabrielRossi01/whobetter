package br.com.whobetter.predictionservice.exception;

import java.util.UUID;

public class MatchClosedException extends RuntimeException {
    public MatchClosedException(UUID matchId, String status) {
        super("Partida " + matchId + " não está aberta para predições. Status atual: " + status);
    }
}
