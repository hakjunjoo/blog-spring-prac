package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;

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

    public List<BlogResponseDto> getMemos() {
        // DB 조회
        return blogRepository.findAll();
    }

    public Long updateBlog(Long id, BlogRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Blog blog = blogRepository.findById(id);
        if (blog != null) {
            // blog 내용 수정
            blogRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택하신 게시글은 존재하지 않거나 입력하신 비밀번호가 틀렸습니다.");
        }
    }

    public Long deleteBlog(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id);
        if(blog != null) {
            // blog 삭제
            blogRepository.delete(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택하신 게시글은 존재하지 않거나 입력하신 비밀번호가 틀렸습니다.");
        }
    }


}
