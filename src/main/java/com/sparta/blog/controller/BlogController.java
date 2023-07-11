package com.sparta.blog.controller;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.dto.DeletedResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/blog")
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BlogResponseDto blogResponseDto = blogService.createBlog(requestDto, userDetails);
        return ResponseEntity.ok().body(blogResponseDto);
    }

    @GetMapping("/blog")
	@Transactional(readOnly = true)
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    @GetMapping("/blog/{id}")
	@Transactional(readOnly = true)
	public BlogResponseDto selectBlog(@PathVariable Long id) {
        return blogService.selectBlog(id);
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity<BlogResponseDto> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        BlogResponseDto blogResponseDto = blogService.updateBlog(id, requestDto, userDetails);
        return ResponseEntity.ok().body(blogResponseDto);
    }

    @DeleteMapping("/blog/{id}")
    public ResponseEntity<DeletedResponseDto> deleteBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        DeletedResponseDto deletedResponseDto = blogService.deleteBlog(id, userDetails);
		return ResponseEntity.ok().body(deletedResponseDto);
    }

}

