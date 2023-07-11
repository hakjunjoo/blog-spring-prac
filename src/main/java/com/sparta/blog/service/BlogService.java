package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.dto.DeletedResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
public class BlogService {
	private final BlogRepository blogRepository;

	public BlogService(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}

	public BlogResponseDto createBlog(BlogRequestDto requestDto, UserDetailsImpl userDetails) {
		// RequestDto => Entity
		Blog blog = new Blog(requestDto, userDetails.getUsername());

		//DB 저장
		blogRepository.save(blog);

		// Entity => ResponseDto
		return new BlogResponseDto(blog);
	}

	public List<BlogResponseDto> getBlogs() {
		// DB 조회
		return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).collect(Collectors.toList());
	}

	public BlogResponseDto selectBlog(Long id) {
		// 해당 게시글이 존재하는지 확인
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));

		return new BlogResponseDto(blog);
	}

	@Transactional
	public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, UserDetailsImpl userDetails) {
		// 해당 글이 DB에 존재하는지 확인
		Optional<Blog> blog = blogRepository.findById(id);

		if (blog.isEmpty()) {
			throw new NoSuchElementException("해당 게시글이 존재하지 않습니다");
		}

		if (!(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN) || blog.get().getAuthor().equals(userDetails.getUsername()))) {
			throw new RejectedExecutionException();
		}

		blog.get().update(requestDto);
		return new BlogResponseDto(blog.get());
	}

	public DeletedResponseDto deleteBlog(Long id, UserDetailsImpl userDetails) {
		// 해당 글이 DB에 존재하는지 확인
		Optional<Blog> blog = blogRepository.findById(id);

		if (blog.isEmpty()) {
			throw new NoSuchElementException("해당 게시글이 존재하지 않습니다");
		}

		if (!(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN) || blog.get().getAuthor().equals(userDetails.getUsername()))) {
			throw new RejectedExecutionException();
		} else {
			blogRepository.delete(blog.get());
		}

		return new DeletedResponseDto("게시글 삭제에 성공했습니다.", HttpStatus.OK);
	}

}
