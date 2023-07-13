package com.sparta.blog.controller;

import com.sparta.blog.dto.*;
import com.sparta.blog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입
    // @valid 유효성체크에 통과하지 못하면  MethodArgumentNotValidException 이 발생한다. -> ControllerAdvice, ExceptionHandler 로 전역에서 예외 관리
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("중복된 username 입니다", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResponseDto("회원가입에 성공했습니다.", HttpStatus.CREATED.value()));
    }

    // 로그인 처리
}

