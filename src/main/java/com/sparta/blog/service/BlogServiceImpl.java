package com.sparta.blog.service;

import com.sparta.blog.dto.BlogListResponseDto;
import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService{
	private final BlogRepository blogRepository;

	// 게시글 작성 API
	@Override
	public BlogResponseDto createBlog(BlogRequestDto requestDto, UserDetailsImpl userDetails) {
		Blog blog = new Blog(requestDto, userDetails.getUsername());
		blogRepository.save(blog);

		return new BlogResponseDto(blog);
	}

	// 전체 게시글 목록 조회 API
	@Override
	public List<BlogListResponseDto> getBlogList() {
		// DB 조회
		return blogRepository.findAllByOrderByCreatedAtDesc().stream().map(BlogListResponseDto::new).collect(Collectors.toList());
	}

	// 게시글 선택 조회 API
	@Override
	public BlogResponseDto selectBlog(Long id) {
		// 해당 게시글이 존재하는지 확인
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new NoExistBlogException("선택한 게시글은 존재하지 않습니다."));
		return new BlogResponseDto(blog);
	}

	// 게시글 수정 API
	@Override
	@Transactional
	public void updateBlog(Long id, UserDetailsImpl userDetails, BlogRequestDto requestDto) {
		// 해당 글이 DB에 존재하는지 확인
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new NoExistBlogException("해당 게시글이 존재하지 않습니다"));
		blog.update(requestDto);
	}

	//게시글 삭제 API
	@Override
	public void deleteBlog(Long id, UserDetailsImpl userDetails) {
		// 해당 글이 DB에 존재하는지 확인
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new NoExistBlogException("해당 게시글이 존재하지 않습니다"));
		blogRepository.delete(blog);
	}

}
