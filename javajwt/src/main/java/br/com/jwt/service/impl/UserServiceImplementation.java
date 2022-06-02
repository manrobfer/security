package br.com.jwt.service.impl;

import br.com.jwt.domain.User;
import br.com.jwt.domain.UserPrincipal;
import br.com.jwt.domain.exception.EmailExistsException;
import br.com.jwt.domain.exception.UserNotFoundException;
import br.com.jwt.domain.exception.UsernameExistsException;
import br.com.jwt.enumeration.Role;
import br.com.jwt.repositories.UserRepository;
import br.com.jwt.service.EmailService;
import br.com.jwt.service.LoginAttemptService;
import br.com.jwt.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static br.com.jwt.domain.constant.UserImplConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;
    private EmailService emailService;

   @Autowired
    public UserServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService,  EmailService emailService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByFirstName(username);
        if (user == null){
            log.error(USERNAME_NOT_FOUND + user);
            throw new UsernameNotFoundException(NOT_FOUND_BY_USERNAME +user);
        }else{
            try {
                validateLoginAttempt(user);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            log.info(USER_BY_NAME_FOUND + user);
            return userPrincipal;
        }

    }

    private void validateLoginAttempt(User user) throws ExecutionException {
        if (user.isNotLocked()) {
            if (loginAttemptService.hasExeededMaxAttempt(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    @Override
    public User register(String firstName, String lastName, String userName, String email) throws UserNotFoundException, UsernameExistsException, EmailExistsException, MessagingException {
       validateNewUsernameAndEmail(EMPTY,userName,email);
       User user = new User();
       user.setUserId(generateUserId());
       String password = generatePassword();
       String encodedPassword = encodePassword(password);
       user.setFirstName(firstName);
       user.setLastName(lastName);
       user.setUsername(userName);
       user.setEmail(email);
       user.setJoinDate(new Date());
       user.setPassword(encodedPassword);
       user.setActive(true);
       user.setNotLocked(true);
       user.setRoles(Role.USER_ROLE.name());
       user.setAuthorities(Role.USER_ROLE.getAuthorities());
       user.setProfileImageurl(getTemporaryProfileImageUrl());
       userRepository.save(user);
       log.info("New user password " + password);
       emailService.sendNewPasswordEmail(firstName,password,email);
       return user;
    }

    private String getTemporaryProfileImageUrl() {
       return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_PROFILE_PATH).toUriString();
    }

    private String encodePassword(String password) {
       return passwordEncoder.encode(password);
    }

    private String generateUserId(){
       return RandomStringUtils.randomNumeric(10);
    }

    private String generatePassword(){
       return RandomStringUtils.randomAlphanumeric(10);
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }



    @Override
    public User findUserByUsername(String name) {

       return userRepository.findUserByFirstName(name);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User addUser(String firstName, String lastName, String username, String email, String role, boolean isNonLocked, boolean isActive) {
        return null;
    }

    @Override
    public User updateUser(String currentUser, String newFirstName, String newLastname, String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) {
        return null;
    }

    @Override
    public void deleteUser(long id) {

    }

    @Override
    public void resetPassword(String email) {

    }

    @Override
    public User updateProfileImage(String userName, MultipartFile profileImage) {
        return null;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameExistsException, EmailExistsException {
        User userByUserName = findUserByUsername(newUsername);
        User userByEmail = findUserByEmail(newEmail);

        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(USER_NOT_FOUND + currentUsername);
            }
            if (userByUserName != null && !currentUser.getId().equals(userByUserName.getId())) {
                throw new UsernameExistsException(USER_ALTEADY_EXISTS);
            }
            if (userByEmail != null && !currentUser.getId().equals(userByEmail.getId())) {
                throw new EmailExistsException(USER_ALTEADY_EXISTS);
            }
            return currentUser;
        } else {
            if (userByUserName != null) {
                throw new UsernameExistsException(USER_ALTEADY_EXISTS);
            }
            if (userByEmail != null) {
                throw new EmailExistsException(USER_ALTEADY_EXISTS);
            }
        }
        return null;
    }


}
