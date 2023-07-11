package com.sparta.blog.dto;

import com.sparta.blog.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
	private Long id;
	private String username;
	private String comment;
	public CommentResponseDto(Comment comment) {
		this.id = comment.getBlog().getId();
		this.username = comment.getUsername();
		this.comment = comment.getComment();
	}
}
