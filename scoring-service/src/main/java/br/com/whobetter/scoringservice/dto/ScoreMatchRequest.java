package br.com.whobetter.scoringservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Payload para cálculo das pontuações de uma partida")
public record ScoreMatchRequest(
        @Schema(description = "UUID da partida cujas previsões serão pontuadas", example = "550e8400-e29b-41d4-a716-446655440020")
        @NotNull
        UUID matchId
) {}