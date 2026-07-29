package br.com.whobetter.scoringservice.messaging;

import java.util.UUID;

public record MatchFinishedEvent(
        UUID matchId,
        UUID groupId,
        Integer homeScore,
        Integer awayScore,
        String status
) {}
