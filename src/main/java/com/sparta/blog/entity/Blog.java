package com.sparta.blog.entity;

import com.sparta.blog.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Blog {
    private Long id;
    private String title; //블로그 제목
    private String author; //블로그 작성자
    private String password; //블로그 글 비밀번호
    private String contents; //블로그 글

    public Blog(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }
}
