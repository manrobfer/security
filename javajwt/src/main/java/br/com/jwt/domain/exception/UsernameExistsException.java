package br.com.jwt.domain.exception;

public class UsernameExistsException extends Exception {

    public UsernameExistsException(String message){
        super(message);
    }
}
