package com.sparta.blog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.blog.entity.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.blog.entity.QBlog.blog;

@Repository
@RequiredArgsConstructor
public class BlogRepositoryImpl implements BlogRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Blog> searchBlog(String param) {
        return jpaQueryFactory.select(blog)
                .from(blog)
                .where(blogEq(param))
                .fetch();
    }

    private BooleanExpression blogEq(String param) {
        return param != null ? blog.contents.contains(param) : null;
    }
}
