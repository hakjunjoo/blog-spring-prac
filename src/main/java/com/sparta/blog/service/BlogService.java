package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.dto.DeletedResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto, UserDetailsImpl userDetails) {
        // RequestDto => Entity
        Blog blog = new Blog(requestDto);
        blog.setAuthor(userDetails.getUsername());
        blog.setPassword(userDetails.getPassword());

        //DB 저장
        Blog saveBlog = blogRepository.save(blog);

        // Entity => ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    public List<BlogResponseDto> getBlogs() {
        // DB 조회
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    public BlogResponseDto selectBlog(Long id) {
        // 해당 게시글이 존재하는지 확인
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, UserDetailsImpl userDetails) {
        // 해당 글이 DB에 존재하는지 확인
        Optional<Blog> blog = blogRepository.findById(id);

        if(blog.isEmpty()) {
            throw new NoSuchElementException("해당 게시글이 존재하지 않습니다");
        }

        if(blog.get().getAuthor().equals(userDetails.getUsername())) {
            blog.get().setTitle(requestDto.getTitle());
            blog.get().setContents(requestDto.getContents());
        }

        return new BlogResponseDto(blog.get());
    }

    public DeletedResponseDto deleteBlog(Long id, BlogRequestDto requestDto, UserDetailsImpl userDetails) {
        // 해당 글이 DB에 존재하는지 확인
        Optional<Blog> blog = blogRepository.findById(id);

        if(blog.isEmpty()) {
            throw new NoSuchElementException("해당 게시글이 존재하지 않습니다");
        }

        boolean success;
        if(blog.get().getAuthor().equals(userDetails.getUsername())) {
            // blog 삭제
            blogRepository.delete(blog.get());
            success = true;
        } else {
            success = false;
        }

        return new DeletedResponseDto(success);
        //status code를 반환하는 것이 일반적
    }

}
