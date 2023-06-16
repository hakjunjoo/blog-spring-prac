package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class SuccessDto {
    private boolean success;

    public SuccessDto(boolean success) {
        this.success = success;
    }
}
