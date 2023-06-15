package com.sparta.blog.controller;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@RestController
@RequestMapping("/api")
public class BlogController {
    private final JdbcTemplate jdbcTemplate;

    public BlogController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        // RequestDto => Entity
        Blog blog = new Blog(requestDto);

        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO blog (title, author, password, contents) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, blog.getTitle());
            preparedStatement.setString(2, blog.getAuthor());
            preparedStatement.setString(3, blog.getPassword());
            preparedStatement.setString(4, blog.getContents());
            return preparedStatement;
        }, keyHolder);

        // Entity => ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    @GetMapping("/blog")
    public List<BlogResponseDto> getBlogs() {
        // DB 조회
        String sql = "SELECT * FROM blog";

        return jdbcTemplate.query(sql, new RowMapper<BlogResponseDto>() {
            @Override
            public BlogResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Blog 데이터들을 BlogResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String contents = rs.getString("contents");
                return new BlogResponseDto(id, title, author, contents);
            }
        });
    }

    @PutMapping("/blog/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        Blog blog = findById(id);
        if (blog != null) {
            // blog 내용 수정
            String sql = "UPDATE blog SET title = ?, author = ?, password = ?, contents = ?  WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getAuthor(), requestDto.getPassword(), requestDto.getContents(), id);
            return id;
        } else {
            throw new IllegalArgumentException("선택하신 게시글은 존재하지 않거나 입력하신 비밀번호가 틀렸습니다.");
        }
    }

    @DeleteMapping("/blog/{id}")
    public Long deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        Blog blog = findById(id);
        if(blog != null) {
            // blog 삭제
            String sql = "DELETE FROM blog WHERE id = ?";
            jdbcTemplate.update(sql, id);
            return id;
        } else {
            throw new IllegalArgumentException("선택하신 게시글은 존재하지 않거나 입력하신 비밀번호가 틀렸습니다.");
        }
    }

    private Blog findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM blog WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Blog blog = new Blog();
                blog.setTitle(resultSet.getString("title"));
                blog.setTitle(resultSet.getString("author"));
                blog.setTitle(resultSet.getString("password"));
                blog.setContents(resultSet.getString("contents"));
                return blog;
            } else {
                return null;
            }
        }, id);
    }
}

