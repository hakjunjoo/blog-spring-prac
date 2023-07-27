package com.sparta.blog.dto;

import com.sparta.blog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class BlogListResponseDto {
    private String title; //블로그 제목
    private String author; //블로그 작성자
    private LocalDateTime createdAt; //생성 시간

    public BlogListResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.author = blog.getAuthor();
        this.createdAt = blog.getCreatedAt();
    }
}
