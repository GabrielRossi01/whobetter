package br.com.whobetter.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @Schema(description = "Nome completo do usuário", example = "Gabriel Rossi")
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
        String name,

        @Schema(description = "E-mail único do usuário", example = "gabriel@whobetter.com")
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-email informado é inválido")
        String email,

        @Schema(description = "Senha de acesso do usuário", example = "Senha@123")
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password
) {}
