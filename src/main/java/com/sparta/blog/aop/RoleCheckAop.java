package com.sparta.blog.aop;

import com.sparta.blog.entity.Blog;
import com.sparta.blog.entity.Comment;
import com.sparta.blog.entity.User;
import com.sparta.blog.entity.UserRoleEnum;
import com.sparta.blog.exception.blog.NoExistBlogException;
import com.sparta.blog.exception.blog.NoExistCommentException;
import com.sparta.blog.exception.blog.NoPermissionException;
import com.sparta.blog.repository.BlogRepository;
import com.sparta.blog.repository.CommentRepository;
import com.sparta.blog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RoleCheckAop {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    @Pointcut("execution(* com.sparta.blog.service.BlogService.updateBlog(..))")
    private void updateBlog() {}

    @Pointcut("execution(* com.sparta.blog.service.BlogService.deleteBlog(..))")
    private void deleteBlog() {}

    @Pointcut("execution(* com.sparta.blog.service.CommentService.updateComment(..))")
    private void updateComment() {}

    @Pointcut("execution(* com.sparta.blog.service.CommentService.deleteComment(..))")
    private void deleteComment() {}

    @Around("updateBlog() || deleteBlog()")
    public Object blogRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        // 게시글 정보 받아오기
        Long blogId = (Long) joinPoint.getArgs()[0];
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new NoExistBlogException("해당 게시글이 존재하지 않습니다."));

        // 로그인된 유저 정보 받아오기
        UserDetailsImpl userDetails = (UserDetailsImpl) joinPoint.getArgs()[1];
        User user = userDetails.getUser();

        if (!user.getRole().equals(UserRoleEnum.ADMIN) || blog.getAuthor().equals(user.getUsername())) {
            throw new NoPermissionException("본인이 작성한 게시글만 수정/삭제할 수 있습니다.");
        }

        return joinPoint.proceed();
    }

    @Around("updateComment() || deleteComment()")
    public Object commentRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        // 댓글 정보 받아오기
        Long blogId = (Long) joinPoint.getArgs()[0];
        Long commentId = (Long) joinPoint.getArgs()[1];
        Comment comment = commentRepository.findByIdAndBlogId(commentId, blogId);

        // 로그인된 유저 정보 받아오기
        UserDetailsImpl userDetails = (UserDetailsImpl) joinPoint.getArgs()[2];
        User user = userDetails.getUser();

        if(comment == null) throw new NoExistCommentException("해당 댓글이 존재하지 않습니다.");

        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || comment.getUsername().equals(user.getUsername()))) {
            throw new NoPermissionException("자신이 등록한 댓글만 수정/삭제할 수 있습니다.");
        }

        return joinPoint.proceed();
    }
}
