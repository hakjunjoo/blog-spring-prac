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
//    @GetMapping("/user/login-page")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/user/signup")
//    public String signup() {
//        return "signup";
//    }


    //실패했을 때 예외처리 해야됨(중복되거나 실패하면 500 리턴함)
    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid SignupRequestDto requestDto) {
        userService.signup(requestDto);

        ResponseEntity<SignupResponseDto> response = ResponseEntity.status(201).body(new SignupResponseDto("회원가입에 성공했습니다.", 201));

        return response;
    }

    @PostMapping("/user/login") //로그인 요청
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto requestDto, HttpServletResponse res) {
        userService.login(requestDto, res);

        ResponseEntity<LoginResponseDto> response = ResponseEntity.status(200).body(new LoginResponseDto("로그인에 성공했습니다.", 200));

        return response;
    }


}

