package com.sparta.blog.exception.jwt;

public class UnsupportedJwtTokenException extends RuntimeException{
    public UnsupportedJwtTokenException(String message) {
        super(message);
    }
}
