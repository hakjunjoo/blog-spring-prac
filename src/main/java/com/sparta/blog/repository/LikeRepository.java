package com.sparta.blog.repository;

import com.sparta.blog.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByUserIdAndBlogId(Long userId, Long blogId);

    List<Like> findByBlogId(Long blogId);
}
