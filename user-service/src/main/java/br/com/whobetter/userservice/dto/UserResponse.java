package br.com.whobetter.userservice.dto;

import br.com.whobetter.userservice.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Resposta com os dados de um usuário")
public record UserResponse(
        @Schema(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Nome completo do usuário", example = "Gabriel Rossi")
        String name,

        @Schema(description = "E-mail do usuário", example = "gabriel@whobetter.com")
        String email,

        @Schema(description = "Data e hora de criação do usuário em UTC", example = "2026-08-04T13:00:00Z")
        Instant createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt());
    }
}
