package br.com.whobetter.matchservice.dto;

import br.com.whobetter.matchservice.domain.MatchStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MatchResponse(
    UUID id,
    UUID groupId,
    String title,
    LocalDateTime eventDate,
    MatchStatus status,
    Integer homeScore,
    Integer awayScore,
    UUID createdBy
) {}
