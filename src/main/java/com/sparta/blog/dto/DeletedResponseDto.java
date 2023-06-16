package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class DeletedResponseDto {
    private boolean success;

    public DeletedResponseDto(boolean success) {
        this.success = success;
    }
}
