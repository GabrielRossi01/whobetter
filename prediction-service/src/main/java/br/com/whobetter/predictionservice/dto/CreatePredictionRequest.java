package br.com.whobetter.predictionservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePredictionRequest(

        @NotNull
        UUID matchId,

        @NotNull
        UUID groupId,

        @NotNull
        UUID userId,

        @NotNull
        @Min(0)
        Integer predictedHomeScore,

        @NotNull
        @Min(0)
        Integer predictedAwayScore
) {}
