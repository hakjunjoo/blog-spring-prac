package com.sparta.blog.exception.jwt;

public class InvalidJwtSignatureException extends RuntimeException{
    public InvalidJwtSignatureException(String message) {
        super(message);
    }
}
