package com.sparta.blog.service;

import com.sparta.blog.dto.LikeResponseDto;
import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Like;
import com.sparta.blog.entity.User;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;

    @Transactional
    public LikeResponseDto likeBlog(Long blogId, User user) {
        //리턴 메세지
        String message;

        //해당 게시글이 존재하는지 확인
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NullPointerException("Could Not found blog"));

        //해당 게시글이 좋아요를 누른 아이디인지 체크
        Like checkLike = likeRepository.findByUserIdAndBlogId(user.getId(), blogId);

        // 해당 게시글에 좋아요를 누르지 않은 아이디이면 좋아요 처리
        if(checkLike == null) {
            Like like = new Like(user, blog);
            likeRepository.save(like);
            message = "좋아요를 눌렀습니다.";
        } else { // 이미 해당 게시글에 좋아요를 누른 아이디이면 좋아요를 취소
            likeRepository.delete(checkLike);
            message = "좋아요를 취소했습니다.";
        }

        // 블로그의 좋아요 개수 처리
        blog.setLikeCount((long) likeRepository.findByBlogId(blogId).size());

        return new LikeResponseDto(message, HttpStatus.OK.value());
    }
}
