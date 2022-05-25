package br.com.jwt.domain.exception;

public class EmailExistsException extends Exception{
    public EmailExistsException(String message){
        super(message);
    }
}
