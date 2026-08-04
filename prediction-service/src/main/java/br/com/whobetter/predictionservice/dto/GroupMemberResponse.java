package br.com.whobetter.predictionservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Resposta indicando se um usuário é membro de um grupo")
public record GroupMemberResponse(
        @Schema(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID groupId,

        @Schema(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,

        @Schema(description = "Indica se o usuário pertence ao grupo", example = "true")
        boolean member
) {}