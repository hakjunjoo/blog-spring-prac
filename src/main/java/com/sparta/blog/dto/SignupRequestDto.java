package com.sparta.blog.dto;

import com.sparta.blog.entity.UserRoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 3, message = "아이디의 길이는 3자 이상이어야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "아이디는 알파벳 소문자와 숫자로 구성되어야 합니다.")
    private String username;

    @NotBlank
    @Size(min = 4, max = 15, message = "비밀번호의 길이는 4자 이상이어야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "비밀번호는 알파벳 대소문자와 숫자로 구성되어야 합니다.")
    private String password;

    @NotBlank
    private String confirmPassword;

	@Enumerated(value = EnumType.STRING)
	private UserRoleEnum role; // USER or ADMIN
}
