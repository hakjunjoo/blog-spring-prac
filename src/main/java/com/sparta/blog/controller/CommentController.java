package com.sparta.blog.controller;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.dto.DeletedResponseDto;
import com.sparta.blog.entity.User;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	//댓글 작성 api
	@PostMapping("/comment/{id}")
	public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto commentResponseDto = commentService.createComment(id, requestDto, userDetails);
		return ResponseEntity.ok().body(commentResponseDto);
	}

	//댓글 수정 api
	@PutMapping("/comment/{id}")
	public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		CommentResponseDto commentResponseDto = commentService.updateComment(id, requestDto, userDetails);
		return ResponseEntity.ok().body(commentResponseDto);
	}

	//댓글 삭제 api
	@DeleteMapping("/comment/{id}")
	public DeletedResponseDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.deleteComment(id, userDetails);
	}
}
