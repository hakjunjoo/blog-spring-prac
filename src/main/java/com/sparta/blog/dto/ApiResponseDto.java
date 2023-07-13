package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {
    private String message;
    private int status;

    public ApiResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
