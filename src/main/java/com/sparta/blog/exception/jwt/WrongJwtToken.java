package com.sparta.blog.exception.jwt;

public class WrongJwtToken extends RuntimeException{
    public WrongJwtToken(String message) {
        super(message);
    }
}
