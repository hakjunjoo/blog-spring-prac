package com.sparta.blog.dto;

import com.sparta.blog.entity.Blog;
import com.sun.net.httpserver.Authenticator;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
public class BlogResponseDto {
    private String title; //블로그 제목
    private String author; //블로그 작성자
    private String contents; //블로그 글
    private LocalDateTime createdAt; //생성 시간
    private LocalDateTime modifiedAt; //수정 시간
	private List<CommentResponseDto> comments;


	public BlogResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.author = blog.getAuthor();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();

		if(blog.getComments() != null) {
			this.comments = blog.getComments().stream()
					.map(CommentResponseDto::new)
					.sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed()) // 작성날짜 내림차순
					.toList();
		}
    }

}
