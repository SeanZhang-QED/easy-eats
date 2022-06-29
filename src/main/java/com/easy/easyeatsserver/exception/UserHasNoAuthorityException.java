package com.easy.easyeatsserver.exception;

public class UserHasNoAuthorityException extends RuntimeException{
    public UserHasNoAuthorityException(String msg) {
        super(msg);
    }
}
