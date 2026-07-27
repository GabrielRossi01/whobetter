package br.com.whobetter.rankingservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RankingEntryResponse(
        UUID id,
        UUID groupId,
        UUID userId,
        Integer totalPoints,
        Integer rankPosition,
        LocalDateTime updatedAt
) {}
