package com.sparta.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {
    private String message;
    private int status;

    public SignupResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
