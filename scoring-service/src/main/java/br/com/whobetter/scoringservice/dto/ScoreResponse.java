package br.com.whobetter.scoringservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta com os dados de uma pontuação")
public record ScoreResponse(
        @Schema(description = "UUID da pontuação", example = "550e8400-e29b-41d4-a716-446655440050")
        UUID id,

        @Schema(description = "UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
        UUID matchId,

        @Schema(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID groupId,

        @Schema(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,

        @Schema(description = "UUID da previsão associada à pontuação", example = "550e8400-e29b-41d4-a716-446655440030")
        UUID predictionId,

        @Schema(description = "Quantidade de pontos atribuída", example = "3")
        Integer points,

        @Schema(description = "Tipo de regra de pontuação aplicada", example = "EXACT_SCORE")
        String scoringType,

        @Schema(description = "Data e hora de criação da pontuação", example = "2026-08-04T13:20:00")
        LocalDateTime createdAt
) {}