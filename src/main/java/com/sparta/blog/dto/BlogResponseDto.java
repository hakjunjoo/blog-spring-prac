package com.sparta.blog.dto;

import com.sparta.blog.entity.Blog;
import lombok.Getter;

@Getter
public class BlogResponseDto {
    private Long id;
    private String title; //블로그 제목
    private String author; //블로그 작성자
    private String contents; //블로그 글

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.author = blog.getAuthor();
        this.contents = blog.getContents();
    }
}
