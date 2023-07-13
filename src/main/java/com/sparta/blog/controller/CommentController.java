package com.sparta.blog.controller;

import com.sparta.blog.dto.*;
import com.sparta.blog.entity.User;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import com.sparta.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

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
	@PutMapping("/blog/{blogId}/comment/{commentId}")
	public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long blogId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			commentService.updateComment(blogId, commentId, requestDto, userDetails);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제/수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
		}
		return ResponseEntity.ok().body(new ApiResponseDto("해당 댓글을 수정했습니다.", HttpStatus.OK.value()));
	}

	//댓글 삭제 api
	@DeleteMapping("/blog/{blogId}/comment/{commentId}")
	public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long blogId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			commentService.deleteComment(blogId, commentId, userDetails);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제/수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
		}
		return ResponseEntity.ok().body(new ApiResponseDto("해당 댓글을 수정했습니다.", HttpStatus.OK.value()));
	}

	// 댓글 좋아요 기능
	@PostMapping("/blog/{blogId}/comment/{commentId}/like")
	public ResponseEntity<ApiResponseDto> likeComment(@PathVariable Long blogId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(likeService.likeComment(blogId, commentId, userDetails.getUser()));
	}

	// 댓글 좋아요 취소 기능
	@DeleteMapping("/blog/{blogId}/comment/{commentId}/like")
	public ResponseEntity<ApiResponseDto> deleteLikeComment(@PathVariable Long blogId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(likeService.deleteLikeComment(blogId, commentId, userDetails.getUser()));
	}
}
