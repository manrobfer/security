package br.com.jwt.domain.exception;

import br.com.jwt.domain.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final static String ACCOUNT_LOCKED = "Your account has been lockec. Please contact your administrator";
    private final static String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a %s request";
    private final static String INTERNAL_SERVER_ERROR_MSG= "An error occurred while processing the request";
    private final static String INCORRECT_CREDENTIALS = " Username / password incorrect. Please try again";
    private final static String ACCOUNT_DISABLED = " Your account has been disabled. If this is an error, please contact the administrator ";
    private final static String ERROR_PROCESSING_FILE = "Error occurred while processing a file";
    private final static String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    private final static String ERROR_PATH = "/error";
    private final static String NOT_FOUND = "Resource not found";

//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<HttpResponse> accountDisabledException() {
//        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<HttpResponse> badCredentialsException() {
//        return createHttpResponse(BAD_REQUEST, INCORRECT_CREDENTIALS);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<HttpResponse> accessDeniedException() {
//        return createHttpResponse(FORBIDDEN, NOT_ENOUGH_PERMISSION);
//    }
//
//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<HttpResponse> lockedException() {
//        return createHttpResponse(UNAUTHORIZED, ACCOUNT_LOCKED);
//    }
//
//    @ExceptionHandler(TokenExpiredException.class)
//    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
//        return createHttpResponse(UNAUTHORIZED, exception.getMessage());
//    }
//
//    @ExceptionHandler(EmailExistException.class)
//    public ResponseEntity<HttpResponse> emailExistException(EmailExistException exception) {
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }
//
//    @ExceptionHandler(UsernameExistException.class)
//    public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception) {
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }
//
//    @ExceptionHandler(EmailNotFoundException.class)
//    public ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exception) {
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
        return createHttpResponse(BAD_REQUEST, "There is no mapping for this URL");
    }

//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
//        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
//        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
//    }
//
//    @ExceptionHandler(NotAnImageFileException.class)
//    public ResponseEntity<HttpResponse> notAnImageFileException(NotAnImageFileException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(BAD_REQUEST, exception.getMessage());
//    }
//
//    @ExceptionHandler(NoResultException.class)
//    public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(NOT_FOUND, exception.getMessage());
//    }
//
//    @ExceptionHandler(IOException.class)
//    public ResponseEntity<HttpResponse> iOException(IOException exception) {
//        LOGGER.error(exception.getMessage());
//        return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
//    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(HttpStatus.NOT_FOUND, "There is no mapping for this URL");
    }

  //  @Override
  //  public String getErrorPath() {
  //      return ERROR_PATH;
 //   }


}
