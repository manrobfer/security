package br.com.jwt.filter;

import br.com.jwt.domain.constant.SecurityConstant;
import br.com.jwt.utility.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    JwtTokenProvider jwtTokenProvider;

    JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider){

        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
              if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)){
                  response.setStatus(HttpStatus.OK.value());
              } else {
                  String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                  if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)){
                     filterChain.doFilter(request, response);
                     return;
                  }
                  String token = authorizationHeader.substring(SecurityConstant.TOKEN_PREFIX.length());//deve ser PREFIX
                  String username = jwtTokenProvider.getSubject(token);
                  if(jwtTokenProvider.isTokenValid(username,token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                      List<GrantedAuthority> authority = jwtTokenProvider.getAuthorities(token);
                      Authentication authentication = jwtTokenProvider.getAuthentication(username, authority, request);
                      SecurityContextHolder.getContext().setAuthentication(authentication);
                  }else {
                      SecurityContextHolder.clearContext();
                      }
                  }
              filterChain.doFilter(request,response);

    }
}
