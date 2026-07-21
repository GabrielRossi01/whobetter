package br.com.whobetter.scoringservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ScoreMatchRequest(
        @NotNull
        UUID matchId
) {}
