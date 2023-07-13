package com.sparta.blog.controller;

import com.sparta.blog.dto.*;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.BlogService;
import com.sparta.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final LikeService likeService;

    // 블로그 글 작성 api
    @PostMapping("/blog")
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BlogResponseDto blogResponseDto = blogService.createBlog(requestDto, userDetails);
        return ResponseEntity.ok().body(blogResponseDto);
    }

    //블로그 글 전체 조회 api
    @GetMapping("/blog")
	@Transactional(readOnly = true)
    public List<BlogResponseDto> getBlogs() {
        return blogService.getBlogs();
    }

    // 선택한 블로그 글 조회 api
    @GetMapping("/blog/{id}")
	@Transactional(readOnly = true)
	public BlogResponseDto selectBlog(@PathVariable Long id) {
        return blogService.selectBlog(id);
    }

    // 선택한 블로그 글 수정 api
    @PutMapping("/blog/{id}")
    public ResponseEntity<ApiResponseDto> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        try {
            blogService.updateBlog(id, requestDto, userDetails);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제/수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResponseDto("해당 게시글을 수정했습니다.", HttpStatus.OK.value()));
    }

    // 선택한 블로그 글 삭제 api
    @DeleteMapping("/blog/{id}")
    public ResponseEntity<ApiResponseDto> deleteBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            blogService.deleteBlog(id, userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제/수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new ApiResponseDto("해당 게시글을 삭제했습니다.", HttpStatus.OK.value()));
    }

    //블로그 게시글 좋아요 기능
    @PostMapping("/blog/{id}/like")
    public ResponseEntity<ApiResponseDto> likeBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.likeBlog(id, userDetails.getUser()));
    }

    //블로그 게시글 좋아요 취소 기능
    @DeleteMapping("/blog/{id}/like")
    public ResponseEntity<ApiResponseDto> deleteLikeBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.deleteLikeBlog(id, userDetails.getUser()));
    }

}

