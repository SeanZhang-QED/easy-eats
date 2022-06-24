package com.easy.easyeatsserver.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String errorMsg){
        super(errorMsg);
    }
}
