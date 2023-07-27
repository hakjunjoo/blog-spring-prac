package com.sparta.blog.exception.blog;

public class NoExistBlogException extends RuntimeException{
    public NoExistBlogException(String message) {
        super(message);
    }
}
