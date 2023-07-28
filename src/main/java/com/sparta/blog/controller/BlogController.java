package com.sparta.blog.controller;

import com.sparta.blog.dto.ApiResponseDto;
import com.sparta.blog.dto.BlogListResponseDto;
import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.security.UserDetailsImpl;
import com.sparta.blog.service.BlogServiceImpl;
import com.sparta.blog.service.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogServiceImpl blogServiceImpl;
    private final LikeServiceImpl likeServiceImpl;

    // 블로그 글 작성 api
    @PostMapping("/blog")
    public ResponseEntity<BlogResponseDto> createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BlogResponseDto blogResponseDto = blogServiceImpl.createBlog(requestDto, userDetails);

        return new ResponseEntity<>(
                blogResponseDto,
                HttpStatus.CREATED
        );
    }


    //블로그 글 목록 조회 api
    @GetMapping("/blog-list")
    @Transactional(readOnly = true)
    public List<BlogListResponseDto> getBlogList() {
        return blogServiceImpl.getBlogList();
    }


    // 선택한 블로그 글 조회 api
    @GetMapping("/blog/{id}")
    @Transactional(readOnly = true)
    public BlogResponseDto selectBlog(@PathVariable Long id) {
        return blogServiceImpl.selectBlog(id);
    }


    // 선택한 블로그 글 수정 api
    @PutMapping("/blog/{id}")
    public ResponseEntity<ApiResponseDto> updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        blogServiceImpl.updateBlog(id, requestDto, userDetails);

        ApiResponseDto apiResponseDto = new ApiResponseDto("해당 게시글을 수정했습니다.", HttpStatus.OK.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.OK
        );
    }


    // 선택한 블로그 글 삭제 api
    @DeleteMapping("/blog/{id}")
    public ResponseEntity<ApiResponseDto> deleteBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        blogServiceImpl.deleteBlog(id, userDetails);

        ApiResponseDto apiResponseDto = new ApiResponseDto("해당 게시글을 삭제했습니다.", HttpStatus.OK.value());
        return new ResponseEntity<>(
                apiResponseDto,
                HttpStatus.OK
        );
    }


    //블로그 게시글 좋아요 기능
    @PostMapping("/blog/{id}/like")
    public ResponseEntity<ApiResponseDto> likeBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeServiceImpl.likeBlog(id, userDetails.getUser()));
    }


    //블로그 게시글 좋아요 취소 기능
    @DeleteMapping("/blog/{id}/like")
    public ResponseEntity<ApiResponseDto> deleteLikeBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeServiceImpl.deleteLikeBlog(id, userDetails.getUser()));
    }

}

