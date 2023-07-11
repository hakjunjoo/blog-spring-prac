package com.sparta.blog.controller;

import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.LoginResponseDto;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.dto.SignupResponseDto;
import com.sparta.blog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //실패했을 때 예외처리 해야됨(중복되거나 실패하면 500 리턴함)
    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        ResponseEntity<SignupResponseDto> response = ResponseEntity.status(201).body(new SignupResponseDto("회원가입에 성공했습니다.", 201));
        return response;
    }
}

