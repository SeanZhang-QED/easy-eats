package com.easy.easyeatsserver.controller;

import com.easy.easyeatsserver.exception.UserAlreadyExistException;
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

}
