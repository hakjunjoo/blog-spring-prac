package com.sparta.blog.controller;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final Map<Long, Blog> blogMap = new HashMap<>();

    @PostMapping("/blogs")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        // RequestDto => Entity
        Blog blog = new Blog(requestDto);

        // Blog Max ID check
        Long maxId = blogMap.size() > 0 ? Collections.max(blogMap.keySet()) + 1 : 1;
        blog.setId(maxId);

        // DB 저장
        blogMap.put(blog.getId(), blog);

        // Entity => ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    @GetMapping("/blogs")
    public List<BlogResponseDto> getBlogs() {
        // Map to List
        List<BlogResponseDto> responseList = blogMap.values().stream().map(BlogResponseDto::new).toList();

        return responseList;
    }
}
