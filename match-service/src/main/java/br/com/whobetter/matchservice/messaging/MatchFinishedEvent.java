package br.com.whobetter.matchservice.messaging;

import java.util.UUID;

public record MatchFinishedEvent(
        UUID matchId,
        UUID groupId,
        Integer homeScore,
        Integer awayScore,
        String status
) {}
