package br.com.jwt.resource;

import br.com.jwt.domain.User;
import br.com.jwt.domain.UserPrincipal;
import br.com.jwt.domain.exception.EmailExistsException;
import br.com.jwt.domain.exception.UserNotFoundException;
import br.com.jwt.domain.exception.UsernameExistsException;
import br.com.jwt.service.UserService;
import br.com.jwt.utility.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

import static br.com.jwt.domain.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequestMapping(value = {"/","/user"})
public class UserResource {

	private UserService userService;
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	public UserResource(AuthenticationManager authenticationManager, UserService userService,  JwtTokenProvider jwtTokenProvider) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}
	@GetMapping("/home")
	public String showUser() {
		return "application works";
	}

	@PostMapping("/login")
	public ResponseEntity<User> login (@RequestBody User user){
		  authenticate(user.getUsername(),user.getPassword());
		  User loginUser = userService.findUserByUsername(user.getUsername());
		  UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		  HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		  return new ResponseEntity<>(loginUser, jwtHeader ,HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody  User user) throws UserNotFoundException, UsernameExistsException, EmailExistsException, MessagingException {
	       User newUser = userService.register(user.getFirstName(),user.getLastName(),user.getUsername(),user.getEmail());
		   return  new ResponseEntity<>(newUser,HttpStatus.OK);
	}

	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(JWT_TOKEN_HEADER,jwtTokenProvider.generateJwtToken(userPrincipal));
		return headers;
	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
	};

}
