package com.easy.easyeatsserver.controller;

import com.easy.easyeatsserver.exception.UserAlreadyExistException;
import com.easy.easyeatsserver.exception.UserHasNoAuthorityException;
import com.easy.easyeatsserver.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice // make Spring use it when there’s ANY exceptions in the Controller code.
public class CustomExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class) // to match each exception to the corresponding handler function.
    public final ResponseEntity<String> handleUserAlreadyExistExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotExistException.class)
    public final ResponseEntity<String> handleUserNotExistExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserHasNoAuthorityException.class)
    public final ResponseEntity<String> handleUserHasNoAuthorityExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
