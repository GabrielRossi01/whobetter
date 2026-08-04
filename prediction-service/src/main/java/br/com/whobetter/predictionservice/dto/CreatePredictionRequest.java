package br.com.whobetter.predictionservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Payload para criação de uma nova previsão")
public record CreatePredictionRequest(

        @Schema(description = "UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
        @NotNull
        UUID matchId,

        @Schema(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
        @NotNull
        UUID groupId,

        @Schema(description = "UUID do usuário que está realizando a previsão", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull
        UUID userId,

        @Schema(description = "Placar previsto para o mandante", example = "2")
        @NotNull
        @Min(0)
        Integer predictedHomeScore,

        @Schema(description = "Placar previsto para o visitante", example = "1")
        @NotNull
        @Min(0)
        Integer predictedAwayScore
) {}