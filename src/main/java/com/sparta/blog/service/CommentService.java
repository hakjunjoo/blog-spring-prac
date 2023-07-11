package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.dto.DeletedResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final BlogRepository blogRepository;

	public CommentResponseDto createComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new NullPointerException("Could Not Found Blog"));
		Comment comment = new Comment(blog, requestDto, userDetails);
		commentRepository.save(comment);

		return new CommentResponseDto(comment);
	}

	@Transactional
	public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
		Comment comment = checkComment(id, userDetails);

		comment.update(requestDto);
		return new CommentResponseDto(comment);
	}

	public DeletedResponseDto deleteComment(Long id, UserDetailsImpl userDetails) {
		Comment comment = checkComment(id, userDetails);

		boolean success = comment.getUsername().equals(userDetails.getUsername());
		if (success) commentRepository.delete(comment);

		return new DeletedResponseDto("댓글 삭제에 성공했습니다.", HttpStatus.OK);
	}

	private Comment checkComment(Long id, UserDetailsImpl userDetails) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new NullPointerException("Could Not found Comment"));
		if (!(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN) || comment.getUsername().equals(userDetails.getUsername()))) {
			throw new IllegalArgumentException("자신이 등록한 댓글만 수정 또는 삭제할 수 있습니다.");
		}
		return comment;
	}
}
