package com.sparta.blog.repository;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
@Repository
public class BlogRepository {
    private final JdbcTemplate jdbcTemplate;

    public BlogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Blog save(Blog blog) {
        //DB 저장
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

        //DB Insert 후 받아온 기본 키 확인
        Long id = keyHolder.getKey().longValue();
        blog.setId(id);

        return blog;
    }

    public List<BlogResponseDto> findAll() {
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

    public void update(Long id, BlogRequestDto requestDto) {
        String sql = "UPDATE blog SET title = ?, author = ?, password = ?, contents = ?  WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getAuthor(), requestDto.getPassword(), requestDto.getContents(), id);
    }

    public void delete(Long id, BlogRequestDto requestDto) {
        String sql = "DELETE FROM blog WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Blog findById(Long id) {
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
