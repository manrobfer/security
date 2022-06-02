package br.com.jwt.service;

import br.com.jwt.domain.User;
import br.com.jwt.domain.exception.EmailExistsException;
import br.com.jwt.domain.exception.UserNotFoundException;
import br.com.jwt.domain.exception.UsernameExistsException;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistsException, EmailExistsException, MessagingException;
    List<User> getUser();
    User findUserByUsername(String name);
    User findUserByEmail(String email);
    User addUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive);
    User updateUser(String currentUser, String newFirstName,String newLastname, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage );
    void deleteUser(long id);
    void resetPassword(String email);
    User updateProfileImage(String userName, MultipartFile profileImage);
}
