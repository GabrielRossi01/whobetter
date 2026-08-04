package br.com.whobetter.matchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Payload para definicação do resultado de uma partida")
public record SetMatchResultRequest(

        @Schema(description = "Quantidade de gols do mandante", example = "2")
        @NotNull
        @Min(0)
        Integer homeScore,

        @Schema(description = "Quantidade de gols do visitante", example = "1")
        @NotNull
        @Min(0)
        Integer awayScore
) {}
