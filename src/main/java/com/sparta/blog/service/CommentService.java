package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.exception.blog.NoExistCommentException;
import com.sparta.blog.exception.blog.NoPermissionException;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final BlogRepository blogRepository;

	//댓글 작성 api
	public CommentResponseDto createComment(Long blogId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
		Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NoExistBlogException("해당 블로그가 존재하지 않습니다."));

		Comment comment = new Comment(blog, requestDto, userDetails);
		commentRepository.save(comment);

		return new CommentResponseDto(comment);
	}

	//댓글 수정 API
	@Transactional
	public void updateComment(Long blogId, Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
		Comment comment = checkComment(blogId, commentId, userDetails);
		comment.update(requestDto);
	}

	//댓글 삭제 API
	public void deleteComment(Long blogId, Long commentId, UserDetailsImpl userDetails) {
		Comment comment = checkComment(blogId, commentId, userDetails);
		commentRepository.delete(comment);
	}

	//수정 또는 삭제할 블로그가 존재하는 지 확인하고 게시글을 수정 또는 삭제할 수 있는 회원인지 확인하는 메서드
	private Comment checkComment(Long blogId,  Long commentId, UserDetailsImpl userDetails) {
		Comment comment = commentRepository.findByIdAndBlogId(commentId, blogId);
		if(comment == null) throw new NoExistCommentException("해당 댓글이 존재하지 않습니다.");

		if (!(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN) || comment.getUsername().equals(userDetails.getUsername()))) {
			throw new NoPermissionException("자신이 등록한 댓글만 수정/삭제할 수 있습니다.");
		}

		return comment;
	}
}
