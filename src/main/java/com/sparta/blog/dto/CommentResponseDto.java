package com.sparta.blog.dto;

import com.sparta.blog.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto  {
	private Long id;
	private String username;
	private String comment;
	private Long likeCount; // 좋아요 개수
	private LocalDateTime createdAt; //생성 시간
	private LocalDateTime modifiedAt; //수정 시간

	public CommentResponseDto(Comment comment) {
		this.id = comment.getBlog().getId();
		this.username = comment.getUsername();
		this.comment = comment.getComment();
		this.likeCount = comment.getLikeCount();
		this.createdAt = comment.getCreatedAt();
		this.modifiedAt = comment.getModifiedAt();
	}
}
