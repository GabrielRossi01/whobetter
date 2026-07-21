package br.com.whobetter.predictionservice.dto;

import java.util.UUID;

public record MatchResponse(
        UUID id,
        UUID groupId,
        String title,
        String status,
        Integer homeScore,
        Integer awayScore
) {}
