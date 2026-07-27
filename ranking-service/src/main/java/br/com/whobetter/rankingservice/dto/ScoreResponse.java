package br.com.whobetter.rankingservice.dto;

import java.util.UUID;

public record ScoreResponse(
        UUID id,
        UUID matchId,
        UUID groupId,
        UUID userId,
        UUID predictionId,
        Integer points,
        String scoringType
) {}
