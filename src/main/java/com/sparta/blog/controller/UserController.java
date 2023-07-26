package com.sparta.blog.controller;

import com.sparta.blog.dto.*;
import com.sparta.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원 가입
    // @valid 유효성체크에 통과하지 못하면  MethodArgumentNotValidException 이 발생한다. -> ControllerAdvice, ExceptionHandler 로 전역에서 예외 관리
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        ApiResponseDto apiResponseDto = new ApiResponseDto("회원가입에 성공했습니다.", HttpStatus.CREATED.value());

        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.CREATED
        );
    }

    // 로그인 처리
    @PostMapping("/user/login/success")
    public ResponseEntity<ApiResponseDto> login(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = (String) request.getAttribute("message");
        int status = response.getStatus();
        ApiResponseDto apiResponseDto = new ApiResponseDto(errorMessage, status);

        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.OK
        );
    }

    @PostMapping("/user/login/fail")
    public ResponseEntity<ApiResponseDto> loginFail(HttpServletRequest request, HttpServletResponse response) {
        String errorMessage = (String) request.getAttribute("message");
        int status = response.getStatus();
        ApiResponseDto apiResponseDto = new ApiResponseDto(errorMessage, status);

        // 상태 코드와 에러 메시지를 ResponseEntity에 담아서 반환
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.UNAUTHORIZED
        );
    }
}

