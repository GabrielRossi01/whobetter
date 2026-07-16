package br.com.whobetter.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record JoinGroupRequest(

        @NotBlank(message = "O código de convite é obrigatório")
        String inviteCode,

        @NotNull(message = "O ID do usuário é obrigatório")
        UUID userId
) {}
