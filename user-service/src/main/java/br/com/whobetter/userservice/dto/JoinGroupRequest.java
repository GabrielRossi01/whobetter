package br.com.whobetter.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record JoinGroupRequest(

        @Schema(description = "Código de convite do grupo", example = "ABC123XY")
        @NotBlank(message = "O código de convite é obrigatório")
        String inviteCode,

        @Schema(description = "UUID do usuário que deseja entrar no grupo", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID userId
) {}
