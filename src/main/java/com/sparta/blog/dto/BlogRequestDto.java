package com.sparta.blog.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {
    private String title;
    private String author;
    private String contents;
    private String password; //블로그 글 비밀번호
}
