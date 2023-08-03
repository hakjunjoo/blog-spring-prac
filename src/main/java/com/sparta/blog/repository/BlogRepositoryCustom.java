package com.sparta.blog.repository;

import com.sparta.blog.entity.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepositoryCustom {

    /**
     * 블로그 글 검색 API
     * @param param 글 검색 키워드 정보
     * @return 검색 블로그 글 리스트
     */
    List<Blog> searchBlog(String param);
}
