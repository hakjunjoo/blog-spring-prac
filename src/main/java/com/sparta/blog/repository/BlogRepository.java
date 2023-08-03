package com.sparta.blog.repository;

import com.sparta.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, BlogRepositoryCustom {
    List<Blog> findAllByOrderByCreatedAtDesc(); //작성 날짜 기준 내림차순 정렬
}
