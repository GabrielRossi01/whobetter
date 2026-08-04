package br.com.whobetter.userservice.dto;

import br.com.whobetter.userservice.domain.Group;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Resposta com os dados de um grupo")
public record GroupResponse(
        @Schema(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID id,

        @Schema(description = "Nome do grupo", example = "Amigos do Fut")
        String name,

        @Schema(description = "Código de convite do grupo", example = "ABC123XY")
        String inviteCode,

        @Schema(description = "UUID do owner do grupo", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID ownerId,

        @Schema(description = "Data e hora de criação do grupo em UTC", example = "2026-08-04T13:00:00Z")
        Instant createdAt
) {
    public static GroupResponse from(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getInviteCode(),
                group.getOwnerId(),
                group.getCreatedAt()
        );
    }
}
