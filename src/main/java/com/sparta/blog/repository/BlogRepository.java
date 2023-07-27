package com.sparta.blog.repository;

import com.sparta.blog.dto.BlogListResponseDto;
import com.sparta.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByCreatedAtDesc(); //작성 날짜 기준 내림차순 정렬
}
