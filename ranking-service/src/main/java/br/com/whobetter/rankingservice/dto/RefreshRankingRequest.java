package br.com.whobetter.rankingservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Payload para solicitar o recálculo do ranking de um grupo")
public record RefreshRankingRequest(
        @Schema(description = "UUID do grupo cujo ranking deve ser recalculado", example = "550e8400-e29b-41d4-a716-446655440010")
        @NotNull
        UUID groupId
) {}