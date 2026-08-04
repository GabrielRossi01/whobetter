package br.com.whobetter.predictionservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload para atualização de uma previsão existente")
public record UpdatePredictionRequest(

        @Schema(description = "Placar previsto para o mandante", example = "2")
        @NotNull
        @Min(0)
        Integer predictedHomeScore,

        @Schema(description = "Placar previsto para o visitante", example = "1")
        @NotNull
        @Min(0)
        Integer predictedAwayScore
) {}