package com.example.contactsapplication.exception;

import com.example.contactsapplication.exception.custom.DuplicateEmailException;
import com.example.contactsapplication.exception.custom.InvalidLoginRequestException;
import com.example.contactsapplication.exception.custom.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<String> accessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: ", ex);
        return new ResponseEntity<>("Access denied.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<String> authenticationException(AuthenticationException ex) {
        log.info("Authentication error!", ex);
        return new ResponseEntity<>("Authentication error.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<String> duplicateEmailException(DuplicateEmailException ex) {
        log.error("Duplicate e-mail exception: ", ex);
        return new ResponseEntity<>("An error occurred during saving e-mail.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidLoginRequestException.class)
    protected ResponseEntity<String> invalidLoginRequestException(InvalidLoginRequestException ex) {
        log.error("An error occurred during login.", ex);
        return new ResponseEntity<>("An error occurred during login.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<String> badCredentialsException(BadCredentialsException ex) {
        log.info("Bad credentials.", ex);
        return new ResponseEntity<>("Bad credentials.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    protected ResponseEntity<String> invalidPasswordException(InvalidPasswordException ex) {
        log.info("Invalid password.", ex);
        return new ResponseEntity<>("Invalid password entered.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> generalErrorHandler(Throwable t) {
        log.error("General error: ", t);
        return new ResponseEntity<>("Unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
