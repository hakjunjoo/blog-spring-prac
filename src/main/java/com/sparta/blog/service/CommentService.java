package com.sparta.blog.service;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.dto.CommentResponseDto;
import com.sparta.blog.security.UserDetailsImpl;

public interface CommentService {
    /**
     * 댓글 작성 API
     * @param blogId 게시글 정보
     * @param requestDto 댓글 작성 요청 정보
     * @param userDetails 댓글 작성 요청자 정보
     * @return 댓글 작성 결과
     */
    CommentResponseDto createComment(Long blogId, CommentRequestDto requestDto, UserDetailsImpl userDetails);

    /**
     * 댓글 수정 API
     * @param blogId 게시글 정보
     * @param commentId 댓글 정보
     * @param requestDto 댓글 수정 요청 정보
     * @param userDetails 댓글 수정 요청자 정보
     */
    void updateComment(Long blogId, Long commentId,  UserDetailsImpl userDetails, CommentRequestDto requestDto);


    /**
     * 댓글 삭제 API
     * @param blogId 게시글 정보
     * @param commentId 댓글 정보
     * @param userDetails 댓글 삭제 요청자 정보
     */
    void deleteComment(Long blogId, Long commentId, UserDetailsImpl userDetails);

}