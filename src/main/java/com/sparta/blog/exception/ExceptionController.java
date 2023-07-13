package com.sparta.blog.exception;

import com.sparta.blog.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ControllerAdvice : 전역으로 예외를 처리할 수 있음
 */
@ControllerAdvice
public class ExceptionController {

    //@valid  유효성체크에 통과하지 못하면  MethodArgumentNotValidException 이 발생한다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> methodValidException() {
        return ResponseEntity.badRequest().body(new ApiResponseDto("잘못된 형식입니다.", HttpStatus.BAD_REQUEST.value()));
    }

    /**
     * @ExceptionHandler : @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능
     *  - Controller, Restcontroller에만 적용이 가능하다 (@Service 같은 빈에서는 안된다.)
     *  - @ExceptionHandler에 등록한 Controller에만 적용된다. ( 다른 Controller에서 같은 exception이 발생하더라도 예외 처리를 할 수 없음
     *  - 리턴 타입은 자유롭게 가능
     *  - 메서드의 파라미터도 자유롭게 가능
     */
}
