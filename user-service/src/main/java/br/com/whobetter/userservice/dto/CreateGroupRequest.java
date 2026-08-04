package br.com.whobetter.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateGroupRequest(

        @Schema(description = "Nome do grupo", example = "Amigos do Fut")
        @NotBlank(message = "O nome do grupo é obrigatório")
        @Size(max = 120, message = "O nome do grupo deve ter no máximo 120 caracteres")
        String name,

        @Schema(description = "UUID do usuário criador do grupo", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "O ID do usuário criador (owner) é obrigatório")
        UUID ownerId
) {}
