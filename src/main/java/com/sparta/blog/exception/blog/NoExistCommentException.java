package com.sparta.blog.exception.blog;

public class NoExistCommentException extends RuntimeException{
    public NoExistCommentException(String message) {
        super(message);
    }
}
