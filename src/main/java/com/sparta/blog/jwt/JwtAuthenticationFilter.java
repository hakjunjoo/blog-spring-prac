package com.sparta.blog.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.blog.dto.ApiResponseDto;
import com.sparta.blog.dto.LoginRequestDto;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
					)
            );

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //로그인이 성공하는 경우 호출되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
		UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

		String token = jwtUtil.createToken(username, role);
        jwtUtil.addJwtToCookie(token, response);

        response.sendRedirect("/api/user/login/successful"); // 로그인이 성공했다는 것을 반환하기 위해서 사용
    }

    //로그인이 실패하는 경우 호출되는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendRedirect("/api/user/login/fail"); // 로그인이 실패했다는 것을 반환하기 위해서 사용
    }
}