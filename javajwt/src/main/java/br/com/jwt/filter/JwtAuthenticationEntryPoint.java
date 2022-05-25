package br.com.jwt.filter;

import br.com.jwt.domain.constant.SecurityConstant;
import br.com.jwt.domain.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.*;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        HttpResponse httpResponse = new HttpResponse(FORBIDDEN.value(), FORBIDDEN, FORBIDDEN.getReasonPhrase().toUpperCase(), SecurityConstant.FORBIDDEN_MESSAGE);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();

    }

}
