package br.com.whobetter.matchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Payload para criação de uma nova partida")
public record CreateMatchRequest(
        @Schema(description = "UUID do grupo ao qual a partida pertence", example = "550e8400-e29b-41d4-a716-446655440010")
        @NotNull
        UUID groupId,

        @Schema(description = "Título da partida", example = "Final do campeonato do grupo")
        @NotBlank
        String title,

        @Schema(description = "Data e hora futura em que a partida ocorrerá", example = "2026-08-20T19:30:00")
        @NotNull
        @Future
        LocalDateTime eventDate,

        @Schema(description = "UUID do usuário que criou a partida", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull
        UUID createdBy
) {}
