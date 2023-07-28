package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
	private final CommentRepository commentRepository;
	private final BlogRepository blogRepository;

	//댓글 작성 api
	@Override
	public CommentResponseDto createComment(Long blogId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
		Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NoExistBlogException("해당 블로그가 존재하지 않습니다."));

		Comment comment = new Comment(blog, requestDto, userDetails);
		commentRepository.save(comment);

		return new CommentResponseDto(comment);
	}

	//댓글 수정 API
	@Override
	@Transactional
	public void updateComment(Long blogId, Long commentId,  UserDetailsImpl userDetails, CommentRequestDto requestDto) {
		Comment comment = commentRepository.findByIdAndBlogId(commentId, blogId);
		comment.update(requestDto);
	}

	//댓글 삭제 API
	@Override
	public void deleteComment(Long blogId, Long commentId, UserDetailsImpl userDetails) {
		Comment comment = commentRepository.findByIdAndBlogId(commentId, blogId);
		commentRepository.delete(comment);
	}
}
