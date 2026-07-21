package br.com.whobetter.scoringservice.exception;

import java.util.UUID;

public class ScoreAlreadyCalculatedException extends RuntimeException {
    public ScoreAlreadyCalculatedException(UUID matchId) {
        super("Score já calculado para a partida: " + matchId);
    }
}
