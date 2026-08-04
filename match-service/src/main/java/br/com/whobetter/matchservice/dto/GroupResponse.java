package br.com.whobetter.matchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Representação resumida de um grupo no contexto do match-service")
public record GroupResponse(
        @Schema(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID id,

        @Schema(description = "Nome do grupo", example = "Amigos do Futebol")
        String name,

        @Schema(description = "UUID do owner do grupo", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID ownerId
) {}
