package br.com.jwt.listener;

import br.com.jwt.domain.User;
import br.com.jwt.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class AuthenticationSuccessListener{

  LoginAttemptService loginAttemptService;

  @Autowired
  public AuthenticationSuccessListener(LoginAttemptService loginAttemptService) {
      this.loginAttemptService = loginAttemptService;
  }

  @EventListener
  public void onAuthenticationSuccess(AuthenticationSuccessEvent event){
  Object principal = event.getAuthentication().getPrincipal();
  if(principal instanceof User){
     User user = (User) event.getAuthentication().getPrincipal();
    loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());

      }
  }
}


