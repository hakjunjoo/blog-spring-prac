package com.sparta.blog.exception;

import com.sparta.blog.dto.ApiResponseDto;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.exception.blog.NoExistCommentException;
import com.sparta.blog.exception.blog.NoPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlogExceptionController {
    @ExceptionHandler(NoExistBlogException.class)
    public ResponseEntity<ApiResponseDto> noExistBlogException(NoExistBlogException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ApiResponseDto> noPermissionException(NoPermissionException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoExistCommentException.class)
    public ResponseEntity<ApiResponseDto> noExistCommentException(NoExistCommentException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.NOT_FOUND
        );
    }
}
