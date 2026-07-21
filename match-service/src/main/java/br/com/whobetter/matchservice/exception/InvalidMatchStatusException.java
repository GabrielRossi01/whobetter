package br.com.whobetter.matchservice.exception;

import br.com.whobetter.matchservice.domain.MatchStatus;

import java.util.UUID;

public class InvalidMatchStatusException extends RuntimeException {
    public InvalidMatchStatusException(UUID matchId, MatchStatus current, MatchStatus target) {
        super("Não é possível fazer a transição da partida " + matchId + " from " + current + " to " + target);
    }
}
