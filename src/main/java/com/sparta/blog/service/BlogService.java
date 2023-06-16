package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.dto.DeletedResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        // RequestDto => Entity
        Blog blog = new Blog(requestDto);

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
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Blog blog = blogRepository.findByPasswordAndId(requestDto.getPassword(), id);

        // blog 내용 수정
        if(blog != null) {
            blog.update(requestDto);
        } else {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않거나 비밀번호가 틀렸습니다.");
        }

        return new BlogResponseDto(blog);
    }

    public DeletedResponseDto deleteBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findByPasswordAndId(requestDto.getPassword(), id);
        boolean success;
        if(blog != null) {
            // blog 삭제
            blogRepository.delete(blog);
            success = true;
        } else {
            success = false;
        }

        return new DeletedResponseDto(success);
        //status code를 반환하는 것이 일반적인 경우가 많다.
    }

}
