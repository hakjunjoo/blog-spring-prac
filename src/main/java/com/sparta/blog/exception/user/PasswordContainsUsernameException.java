package com.sparta.blog.exception.user;

public class PasswordContainsUsernameException extends RuntimeException{
    public PasswordContainsUsernameException(String message) {
        super(message);
    }
}
