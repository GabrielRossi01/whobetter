package br.com.whobetter.scoringservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScoreResponse(
        UUID id,
        UUID matchId,
        UUID groupId,
        UUID userId,
        UUID predictionId,
        Integer points,
        String scoringType,
        LocalDateTime createdAt
) {}
