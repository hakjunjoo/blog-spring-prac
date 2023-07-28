package com.sparta.blog.controller;

import com.sparta.blog.dto.*;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import com.sparta.blog.service.CommentServiceImpl;
import com.sparta.blog.service.LikeService;
import com.sparta.blog.service.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
		return new ResponseEntity<>(
				commentResponseDto,
				HttpStatus.CREATED
		);
	}

	//댓글 수정 api
	@PutMapping("/blog/{blogId}/comment/{commentId}")
	public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long blogId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
		commentService.updateComment(blogId, commentId, userDetails, requestDto);
		ApiResponseDto apiResponseDto = new ApiResponseDto("해당 댓글을 수정했습니다.", HttpStatus.OK.value());
		return new ResponseEntity<>(
				apiResponseDto,
				HttpStatus.OK
		);
	}

	//댓글 삭제 api
	@DeleteMapping("/blog/{blogId}/comment/{commentId}")
	public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long blogId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.deleteComment(blogId, commentId, userDetails);
		ApiResponseDto apiResponseDto = new ApiResponseDto("해당 댓글을 삭제했습니다.", HttpStatus.OK.value());
		return new ResponseEntity<>(
				apiResponseDto,
				HttpStatus.OK
		);
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
