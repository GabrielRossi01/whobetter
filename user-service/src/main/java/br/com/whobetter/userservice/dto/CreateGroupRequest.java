package br.com.whobetter.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateGroupRequest(

        @NotBlank(message = "O nome do grupo é obrigatório")
        @Size(max = 120, message = "O nome do grupo deve ter no mínimo 120 caracteres")
        String name,

        @NotNull(message = "O ID do usuário criador (owner) é obrigatório")
        UUID ownerId
) {}
