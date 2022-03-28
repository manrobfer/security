package br.com.javajwt.domain;

import static java.util.Arrays.stream;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
		
	private static final long serialVersionUID = 1L;
	
	private User user;

	public UserPrincipal(User user) {		
		this.user = user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		  return stream(this.user.getAuthorities())
			        .map(SimpleGrantedAuthority::new)
			        .collect(Collectors.toList());
	}

	public String getPassword() {
		
		return this.user.getPassword();
	}

	public String getUsername() {		
		return this.getUsername();
	}

	public boolean isAccountNonExpired() {
		
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.user.isNotLocked();
	}

	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
