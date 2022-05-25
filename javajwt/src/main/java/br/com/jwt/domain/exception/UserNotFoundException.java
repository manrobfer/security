package br.com.jwt.domain.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message){
        super(message);
    }
}
