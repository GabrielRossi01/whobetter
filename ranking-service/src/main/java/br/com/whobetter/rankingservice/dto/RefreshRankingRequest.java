package br.com.whobetter.rankingservice.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RefreshRankingRequest(
        @NotNull
        UUID groupId
) {}
