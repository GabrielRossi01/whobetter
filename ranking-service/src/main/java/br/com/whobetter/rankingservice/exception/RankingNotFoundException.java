package br.com.whobetter.rankingservice.exception;

import java.util.UUID;

public class RankingNotFoundException extends RuntimeException {
    public RankingNotFoundException(UUID groupId) {
        super("Ranking não encontrado para o grupo: " + groupId);
    }
}
