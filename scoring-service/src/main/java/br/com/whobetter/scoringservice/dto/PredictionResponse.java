package br.com.whobetter.scoringservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record PredictionResponse(
        UUID id,
        UUID matchId,
        UUID groupId,
        UUID userId,
        Integer predictedHomeScore,
        Integer predictedAwayScore,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
