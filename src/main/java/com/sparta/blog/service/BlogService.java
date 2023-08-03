package com.sparta.blog.service;

import com.sparta.blog.dto.BlogListResponseDto;
import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.security.UserDetailsImpl;

import java.util.List;

public interface BlogService {

    /**
     * 게시글 생성 API
     * @param requestDto 게시글 생성 요청 정보
     * @param userDetails 게시글 생성 요청자 정보
     * @return 게시글 생성 결과
     */
    BlogResponseDto createBlog(BlogRequestDto requestDto, UserDetailsImpl userDetails);


    /**
     * 게시글 목록 조회 API
     * @return 목록 조회 결과
     */
    List<BlogListResponseDto> getBlogList();


    /**
     * 선택 게시글 조회 API
     * @param id 선택 게시글 정보
     * @return 게시글 선택 결과
     */
    BlogResponseDto selectBlog(Long id);


    /**
     * 게시글 수정 API
     * @param id 선택 게시글 정보
     * @param userDetails 게시글 수정 요청자 정보
     * @param requestDto 게시글 수정 요청 정보
     */
    void updateBlog(Long id,UserDetailsImpl userDetails, BlogRequestDto requestDto);


    /**
     * 게시글 삭제 API
     * @param id 선택 게시글 정보
     * @param userDetails 게시글 삭제 요청자 정보
     */
    void deleteBlog(Long id, UserDetailsImpl userDetails);

    /**
     * 게시글 검색 API
     * @param param 게시글 검색 정보
     * @return 검색 게시글 조회 결과
     */
    List<BlogResponseDto> searchBlog(String param);
}
