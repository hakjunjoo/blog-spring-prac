package com.sparta.blog.service;

import com.sparta.blog.dto.BlogListResponseDto;
import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.exception.blog.NoPermissionException;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {
	private final BlogRepository blogRepository;

	public BlogResponseDto createBlog(BlogRequestDto requestDto, UserDetailsImpl userDetails) {
		// RequestDto => Entity
		Blog blog = new Blog(requestDto, userDetails.getUsername());

		//DB 저장
		blogRepository.save(blog);

		// Entity => ResponseDto
		return new BlogResponseDto(blog);
	}

	// 전체 게시글 목록 조회 API
	public List<BlogListResponseDto> getBlogList() {
		// DB 조회
		return blogRepository.findAllByOrderByCreatedAtDesc().stream().map(BlogListResponseDto::new).collect(Collectors.toList());
	}

	public BlogResponseDto selectBlog(Long id) {
		// 해당 게시글이 존재하는지 확인
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new NoExistBlogException("선택한 게시글은 존재하지 않습니다."));
		return new BlogResponseDto(blog);
	}

	@Transactional
	public void updateBlog(Long id, BlogRequestDto requestDto, UserDetailsImpl userDetails) {
		// 해당 글이 DB에 존재하는지 확인
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new NoExistBlogException("해당 게시글이 존재하지 않습니다"));

		// 회원의 권한이 ADMIN이거나 블로그 글에 등록된 아이디와 일치해야만 게시글 수정이 가능
		if (!(userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN) || blog.getAuthor().equals(userDetails.getUsername()))) {
			throw new NoPermissionException("본인이 작성한 게시글만 수정할 수 있습니다.");
		}

		blog.update(requestDto);
	}

	public void deleteBlog(Long id, UserDetailsImpl userDetails) {
		// 해당 글이 DB에 존재하는지 확인
		Blog blog = blogRepository.findById(id).orElseThrow(() -> new NoExistBlogException("해당 게시글이 존재하지 않습니다"));

		// 회원의 권한이 ADMIN이거나 블로그 글에 등록된 아이디와 일치해야만 게시글 수정이 가능
		if (userDetails.getUser().getRole().equals(UserRoleEnum.ADMIN) || blog.getAuthor().equals(userDetails.getUsername())) {
			blogRepository.delete(blog);
		} else {
			throw new NoPermissionException("본인이 작성한 게시글만 삭제할 수 있습니다.");
		}
	}

}
