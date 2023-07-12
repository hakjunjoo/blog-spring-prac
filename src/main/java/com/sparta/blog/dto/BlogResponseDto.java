package com.sparta.blog.dto;

import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Comment;
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
	private List<CommentResponseDto> comments; // 게시글에 등록된 댓글


	public BlogResponseDto(Blog blog) {
        this.title = blog.getTitle();
        this.author = blog.getAuthor();
        this.contents = blog.getContents();
        this.createdAt = blog.getCreatedAt();
        this.modifiedAt = blog.getModifiedAt();

        //처음 게시글을 생성하는 경우 comment 는 당연히 null 이라서 에러가 발생함
		if(blog.getComments() != null) {
			this.comments = blog.getComments().stream()
					.map(CommentResponseDto::new)
					.sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed()) // 작성날짜 내림차순 정렬
					.toList();
		}

    }

}
