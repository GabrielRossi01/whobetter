package br.com.whobetter.rankingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta com os dados de uma entrada de ranking")
public record RankingEntryResponse(
        @Schema(description = "UUID do registro de ranking", example = "550e8400-e29b-41d4-a716-446655440040")
        UUID id,

        @Schema(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID groupId,

        @Schema(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,

        @Schema(description = "Pontuação total acumulada pelo usuário no grupo", example = "15")
        Integer totalPoints,

        @Schema(description = "Posição atual do usuário no ranking do grupo", example = "1")
        Integer rankPosition,

        @Schema(description = "Data e hora da última atualização do ranking", example = "2026-08-04T13:30:00")
        LocalDateTime updatedAt
) {}