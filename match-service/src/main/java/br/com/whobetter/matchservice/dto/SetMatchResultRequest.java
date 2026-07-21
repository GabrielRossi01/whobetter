package br.com.whobetter.matchservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SetMatchResultRequest(

   @NotNull
   @Min(0)
   Integer homeScore,

   @NotNull
   @Min(0)
   Integer awayScore
) {}
