package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class LikeResponseDto {
    private String message;
    private int status;

    public LikeResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
