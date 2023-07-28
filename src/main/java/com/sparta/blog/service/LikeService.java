package com.sparta.blog.service;

import com.sparta.blog.dto.ApiResponseDto;
import com.sparta.blog.entity.User;

public interface LikeService {
    /**
     * 게시글 좋아요 API
     * @param blogId 게시글 정보
     * @param user 게시글 좋아요 요청자 정보
     * @return 좋아요 결과
     */
    ApiResponseDto likeBlog(Long blogId, User user);

    /**
     * 게시글 좋아요 취소 API
     * @param blogId 게시글 정보
     * @param user 게시글 좋아요 취소 요청자 정보
     * @return 좋아요 취소 결과
     */
    ApiResponseDto deleteLikeBlog(Long blogId, User user);

    /**
     * 댓글 좋아요 API
     * @param blogId 게시글 정보
     * @param commentId 댓글 정보
     * @param user 댓글 좋아요 요청자 정보
     * @return 댓글 좋아요 결과
     */
    ApiResponseDto likeComment(Long blogId, Long commentId, User user);

    /**
     * 댓글 좋아요 취소 API
     * @param blogId 게시글 정보
     * @param commentId 댓글 정보
     * @param user 댓글 좋아요 취소 요청자 정보
     * @return 댓글 좋아요 취소 결과
     */
    ApiResponseDto deleteLikeComment(Long blogId, Long commentId, User user);
}
