package com.sparta.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 4, max = 10, message = "아이디의 길이는 4자에서 10자 사이여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "아이디는 알파벳 소문자와 숫자로 구성되어야 합니다.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15, message = "비밀번호의 길이는 8자에서 15자 사이여야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "아이디는 알파벳 대소문자와 숫자로 구성되어야 합니다.")
    private String password;
}