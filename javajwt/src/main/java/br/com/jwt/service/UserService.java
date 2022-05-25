package br.com.jwt.service;

import br.com.jwt.domain.User;
import br.com.jwt.domain.exception.EmailExistsException;
import br.com.jwt.domain.exception.UserNotFoundException;
import br.com.jwt.domain.exception.UsernameExistsException;

import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistsException, EmailExistsException;
    List<User> getUser();
    User findUserByUsername(String name);
    User findUserByEmail(String email);
}
