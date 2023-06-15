package com.sparta.blog.service;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.repository.BlogRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BlogService {
    private final JdbcTemplate jdbcTemplate;

    public BlogService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto) {
        // RequestDto => Entity
        Blog blog = new Blog(requestDto);

        //DB 저장
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);
        Blog saveBlog = blogRepository.save(blog);

        // Entity => ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    public List<BlogResponseDto> getMemos() {
        // DB 조회
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);
        return blogRepository.findAll();
    }

    public Long updateBlog(Long id, BlogRequestDto requestDto) {
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);

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
        BlogRepository blogRepository = new BlogRepository(jdbcTemplate);

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
