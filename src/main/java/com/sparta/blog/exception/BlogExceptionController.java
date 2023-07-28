package com.sparta.blog.exception;

import com.sparta.blog.dto.ApiResponseDto;
import com.sparta.blog.exception.blog.LikeException;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.exception.blog.NoExistCommentException;
import com.sparta.blog.exception.blog.NoPermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlogExceptionController {

    // 블로그 게시글이 존재하지 않은 경우 발생하는 예외
    @ExceptionHandler(NoExistBlogException.class)
    public ResponseEntity<ApiResponseDto> noExistBlogException(NoExistBlogException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.NOT_FOUND
        );
    }

    // 게시글 수정/삭제 권한이 없는 경우 발생하는 예외
    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ApiResponseDto> noPermissionException(NoPermissionException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }

    // 댓글이 존재하지 않은 경우 발생하는 예외
    @ExceptionHandler(NoExistCommentException.class)
    public ResponseEntity<ApiResponseDto> noExistCommentException(NoExistCommentException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.NOT_FOUND
        );
    }

    // 좋아요와 관련된 예외
    @ExceptionHandler(LikeException.class)
    public ResponseEntity<ApiResponseDto> likeException(LikeException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }
}
