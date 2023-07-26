package com.sparta.blog.exception;

public class PasswordContainsUsernameException extends RuntimeException{
    public PasswordContainsUsernameException(String message) {
        super(message);
    }
}
