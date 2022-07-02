package com.easy.easyeatsserver.exception;

public class PostNotExistException extends RuntimeException{
    public PostNotExistException(String message) {
        super(message);
    }
}
