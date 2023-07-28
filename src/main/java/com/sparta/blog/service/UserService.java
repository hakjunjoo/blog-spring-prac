package com.sparta.blog.service;

import com.sparta.blog.dto.SignupRequestDto;

public interface UserService {
    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 정보
     */
    void signup(SignupRequestDto requestDto);
}
