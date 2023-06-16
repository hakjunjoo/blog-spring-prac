package com.sparta.blog.repository;

import com.sparta.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findByPasswordAndId(String password, Long id); //id를 찾는 것과 동시에 해당 id 객체에서 password도 비교
    List<Blog> findAllByOrderByModifiedAtDesc(); //작성 날짜 기준 내림차순 정렬
}
