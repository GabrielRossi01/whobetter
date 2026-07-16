package br.com.whobetter.userservice.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("O e-mail '%s' já está cadastrado".formatted(email));
    }
}
