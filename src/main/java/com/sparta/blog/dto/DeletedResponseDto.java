package com.sparta.blog.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DeletedResponseDto {
    private String msg;
	private int status;

	public DeletedResponseDto(String msg, HttpStatus httpStatus) {
		this.msg = msg;
		this.status = httpStatus.value();
	}
}
