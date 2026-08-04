package br.com.whobetter.predictionservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta com os dados de uma previsão")
public record PredictionResponse(
        @Schema(description = "UUID da previsão", example = "550e8400-e29b-41d4-a716-446655440030")
        UUID id,

        @Schema(description = "UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
        UUID matchId,

        @Schema(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID groupId,

        @Schema(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,

        @Schema(description = "Placar previsto para o mandante", example = "2")
        Integer predictedHomeScore,

        @Schema(description = "Placar previsto para o visitante", example = "1")
        Integer predictedAwayScore,

        @Schema(description = "Data e hora de criação da previsão", example = "2026-08-04T13:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Data e hora da última atualização da previsão", example = "2026-08-04T13:15:00")
        LocalDateTime updatedAt
) {}