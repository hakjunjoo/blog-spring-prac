package com.sparta.blog.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LoginResponseDto {
    private String message;
    private int status;

    public LoginResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}

