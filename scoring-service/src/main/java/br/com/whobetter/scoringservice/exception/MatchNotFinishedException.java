package br.com.whobetter.scoringservice.exception;

import java.util.UUID;

public class MatchNotFinishedException extends RuntimeException {
    public MatchNotFinishedException(UUID matchId, String status) {
        super("Partida " + matchId + " não terminou. Status atual: " + status);
    }
}
