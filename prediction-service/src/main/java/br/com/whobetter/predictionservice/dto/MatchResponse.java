package br.com.whobetter.predictionservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta resumida de uma partida usada pelo prediction-service")
public record MatchResponse(
        @Schema(description = "UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
        UUID id,

        @Schema(description = "UUID do grupo da partida", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID groupId,

        @Schema(description = "Título da partida", example = "Final do campeonato do grupo")
        String title,

        @Schema(description = "Status da partida", example = "OPEN")
        String status,

        @Schema(description = "Placar do mandante", example = "2", nullable = true)
        Integer homeScore,

        @Schema(description = "Placar do visitante", example = "1", nullable = true)
        Integer awayScore
) {}