package com.sparta.blog.service;

import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.entity.User;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.exception.DuplicateUsernameException;
import com.sparta.blog.exception.PasswordContainsUsernameException;
import com.sparta.blog.exception.PasswordMismatchException;
import com.sparta.blog.jwt.JwtUtil;
import com.sparta.blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String confirmPassword = requestDto.getConfirmPassword();
		UserRoleEnum role = requestDto.getRole();

        // 비밀번호와 비밀번호 확인이 정확하게 일치하는지 확인
        if(!confirmPassword.equals(password)) {
            throw new PasswordMismatchException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 비밀번호에 username에 들어간 문자열이 포함되는지 확인
        if(password.contains(username)) {
            throw new PasswordContainsUsernameException("비밀번호에 Username이 포함될 수 없습니다.");
        }

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new DuplicateUsernameException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        password = passwordEncoder.encode(password);
        User user = new User(username, password, role);
        userRepository.save(user);
    }

}

