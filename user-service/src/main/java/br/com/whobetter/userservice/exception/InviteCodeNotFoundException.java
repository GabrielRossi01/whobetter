package br.com.whobetter.userservice.exception;

public class InviteCodeNotFoundException extends RuntimeException {
    public InviteCodeNotFoundException(String inviteCode) {
        super("Nenhum grupo encontrado para o código de convite '%s'".formatted(inviteCode));
    }
}
