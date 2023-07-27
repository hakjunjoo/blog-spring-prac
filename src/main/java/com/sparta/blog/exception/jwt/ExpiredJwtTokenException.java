package com.sparta.blog.exception.jwt;

public class ExpiredJwtTokenException extends RuntimeException{
    public ExpiredJwtTokenException(String message) {
        super(message);
    }
}
