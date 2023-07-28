package com.sparta.blog.service;

import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.Like;
import com.sparta.blog.entity.User;
import com.sparta.blog.exception.blog.LikeException;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.exception.blog.NoExistCommentException;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{
    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    // 블로그 게시글 좋아요 api
    @Transactional
    public void likeBlog(Long blogId, User user) {
        //해당 게시글이 존재하는지 확인
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NoExistBlogException("해당 게시글이 존재하지 않습니다."));

        //해당 게시글에 좋아요를 누른 아이디인지 체크
        Like checkLike = likeRepository.findByUserIdAndBlogId(user.getId(), blogId);
        if (checkLike != null) {
            throw new LikeException("이미 좋아요를 눌렀습니다.");
        } else { // 해당 게시글에 좋아요를 누르지 않은 아이디이면 좋아요 처리
            Like like = new Like(user, blog);
            likeRepository.save(like);
        }

        // 블로그 게시글의 좋아요 개수 처리
        blog.setLikeCount((long) likeRepository.findByBlogIdAndCommentId(blogId, null).size());
    }

    // 블로그 게시글 좋아요 취소 api
    @Transactional
    public void deleteLikeBlog(Long blogId, User user) {
        //해당 게시글이 존재하는지 확인
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NoExistBlogException("해당 게시글이 존재하지 않습니다."));

        //해당 게시글에 좋아요를 누른 아이디인지 체크
        Like checkLike = likeRepository.findByUserIdAndBlogId(user.getId(), blogId);
        if(checkLike != null) {
            likeRepository.delete(checkLike);
        } else { // 해당 게시글에 좋아요를 누르지 않은 아이디이면 좋아요 처리
            throw new LikeException("아직 좋아요를 누르지 않았습니다.");
        }

        // 블로그 게시글의 좋아요 개수 처리
        blog.setLikeCount((long) likeRepository.findByBlogIdAndCommentId(blogId, null).size());
    }

    // 댓글 좋아요 api
    @Transactional
    public void likeComment(Long blogId, Long commentId, User user) {
        //해당 게시글, 댓글이 존재하는지 확인
        Comment comment = commentRepository.findByIdAndBlogId(commentId, blogId);
        if(comment == null) {
            throw new NoExistCommentException("해당 댓글이 존재하지 않습니다.");
        }

        // 해당 댓글에 좋아요를 누른 아이디인지 확인
        Like checkLike = likeRepository.findByUserIdAndCommentId(user.getId(), commentId);
        if(checkLike != null) {
            throw new LikeException("이미 좋아요를 눌렀습니다.");
        } else { // 해당 댓글에 좋아요를 누르지 않은 아이디면 좋아요 처리
            Like like = new Like(user, comment);
            likeRepository.save(like);
        }

        // 댓글 좋아요 개수 처리
        comment.setLikeCount((long) likeRepository.findByCommentId(commentId).size());
    }

    // 댓글 좋아요 취소 api
    @Transactional
    public void deleteLikeComment(Long blogId, Long commentId, User user) {
        //해당 게시글, 댓글이 존재하는지 확인
        Comment comment = commentRepository.findByIdAndBlogId(commentId, blogId);
        if(comment == null) {
            throw new NoExistCommentException("해당 댓글이 존재하지 않습니다.");
        }

        // 해당 댓글에 좋아요를 누른 아이디인지 확인
        Like checkLike = likeRepository.findByUserIdAndCommentId(user.getId(), commentId);
        if(checkLike != null) {
            likeRepository.delete(checkLike);
        } else {
            throw new LikeException("아직 좋아요를 누르지 않았습니다.");
        }

        // 댓글 좋아요 개수 처리
        comment.setLikeCount((long) likeRepository.findByCommentId(commentId).size());
    }
}
