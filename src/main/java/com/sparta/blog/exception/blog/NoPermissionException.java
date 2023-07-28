package com.sparta.blog.exception.blog;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message) {
        super(message);
    }
}
