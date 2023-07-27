package com.sparta.blog.exception.jwt;

import com.sparta.blog.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionController {

    // 유효하지 않은 JWT 서명에 대한 예외 처리
    @ExceptionHandler(InvalidJwtSignatureException.class)
    public ResponseEntity<ApiResponseDto> invalidJwtSignatureException(InvalidJwtSignatureException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }

    // 만료된 JWT token에 대한 예외 처리
    @ExceptionHandler(ExpiredJwtTokenException.class)
    public ResponseEntity<ApiResponseDto> expiredJwtToken(ExpiredJwtTokenException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }

    // 지원되지 않는 JWT 토큰에 대한 예외 처리
    @ExceptionHandler(UnsupportedJwtTokenException.class)
    public ResponseEntity<ApiResponseDto> unsupportedJwtToken(UnsupportedJwtTokenException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }

    // 잘못된 JWT 토큰에 대한 예외 처리
    @ExceptionHandler(WrongJwtToken.class)
    public ResponseEntity<ApiResponseDto> wrongJwtToken(WrongJwtToken e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.BAD_REQUEST
        );
    }
}
