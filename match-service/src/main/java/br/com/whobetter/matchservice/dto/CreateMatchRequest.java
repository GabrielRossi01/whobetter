package br.com.whobetter.matchservice.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMatchRequest(

   @NotNull
   UUID groupId,

   @NotBlank
   String title,

   @NotNull
   @Future
   LocalDateTime eventDate,

   @NotNull
   UUID createdBy
) {}
