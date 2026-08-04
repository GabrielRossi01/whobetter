package br.com.whobetter.matchservice.dto;

import br.com.whobetter.matchservice.domain.MatchStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Resposta com os dados de uma partida")
public record MatchResponse(
        @Schema(description = "UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
        UUID id,

        @Schema(description = "UUID do grupo ao qual a partida pertence", example = "550e8400-e29b-41d4-a716-446655440010")
        UUID groupId,

        @Schema(description = "Título da partida", example = "Final do campeonato do grupo")
        String title,

        @Schema(description = "Data e hora agendada para a partida", example = "2026-08-20T19:30:00")
        LocalDateTime eventDate,

        @Schema(description = "Status atual da partida", example = "OPEN")
        MatchStatus status,

        @Schema(description = "Quantidade de gols do mandante", example = "2", nullable = true)
        Integer homeScore,

        @Schema(description = "Quantidade de gols do visitante", example = "1", nullable = true)
        Integer awayScore,

        @Schema(description = "UUID do usuário que criou a partida", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID createdBy
) {}
