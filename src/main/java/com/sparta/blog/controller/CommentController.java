package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.dto.DeletedResponseDto;
import com.sparta.blog.dto.LikeResponseDto;
import com.sparta.blog.entity.User;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import com.sparta.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	private final LikeService likeService;

	//댓글 작성 api
	@PostMapping("/blog/{blogId}/comment")
	public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long blogId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto commentResponseDto = commentService.createComment(blogId, requestDto, userDetails);
		return ResponseEntity.ok().body(commentResponseDto);
	}

	//댓글 수정 api
	@PutMapping("/blog/{blogId}/comment")
	public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long blogId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto commentResponseDto = commentService.updateComment(blogId, requestDto, userDetails);
		return ResponseEntity.ok().body(commentResponseDto);
	}

	//댓글 삭제 api
	@DeleteMapping("/blog/{blogId}/comment")
	public DeletedResponseDto deleteComment(@PathVariable Long blogId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.deleteComment(blogId, userDetails);
	}

	// 댓글 좋아요 기능
	@PostMapping("/blog/{blogId}/comment/{commentId}/like")
	public ResponseEntity<LikeResponseDto> likeComment(@PathVariable Long blogId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(likeService.likeComment(blogId, commentId, userDetails.getUser()));
	}

	// 댓글 좋아요 취소 기능
	@DeleteMapping("/blog/{blogId}/comment/{commentId}/like")
	public ResponseEntity<LikeResponseDto> deleteLikeComment(@PathVariable Long blogId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(likeService.deleteLikeComment(blogId, commentId, userDetails.getUser()));
	}
}
