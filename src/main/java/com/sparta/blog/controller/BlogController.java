package com.sparta.blog.controller;

import com.sparta.blog.dto.BlogRequestDto;
import com.sparta.blog.dto.BlogResponseDto;
import com.sparta.blog.entity.Blog;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final Map<Long, Blog> blogMap = new HashMap<>();

    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto) {
        // RequestDto => Entity
        Blog blog = new Blog(requestDto);

        // Blog Max ID check
        Long maxId = blogMap.size() > 0 ? Collections.max(blogMap.keySet()) + 1 : 1;
        blog.setId(maxId);

        // DB 저장
        blogMap.put(blog.getId(), blog);

        // Entity => ResponseDto
        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    @GetMapping("/blog")
    public List<BlogResponseDto> getBlogs() {
        // Map to List
        List<BlogResponseDto> responseList = blogMap.values().stream().map(BlogResponseDto::new).toList();

        return responseList;
    }

    @PutMapping("/blog/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        // 해당 게시글이 DB에 존재하는지 확인 && 비밀번호도 일치하는지 확인
        if (blogMap.containsKey(id) && (blogMap.get(id).getPassword()).equals(requestDto.getPassword())) {
            //해당 게시글 가져오기
            Blog blog = blogMap.get(id);
            // memo 수정
            blog.update(requestDto);
            return blog.getId();
        } else {
            throw new IllegalArgumentException("선택하신 게시글은 존재하지 않거나 입력하신 비밀번호가 틀렸습니다.");
        }
//        boolean exist = false;
//        for( Blog list : blogMap.values()) {
//            if(Objects.equals(list.getId(), id) && requestDto.getPassword().equals(list.getPassword())) { //id와 password가 맞는 경우
//                exist = true;
//                //해당 게시글 가져오기
//                Blog blog = blogMap.get(id);
//                // memo 수정
//                blog.update(requestDto);
//                return blog.getId();
//            }
//        }
//        if(!exist) {
//            throw new IllegalArgumentException("선택하신 게시글은 존재하지 않습니다.");
//        }
    }

    @DeleteMapping("/blog/{id}")
    public Long deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        //해당 게시글이 DB에 존재하는지 확인
        if (blogMap.containsKey(id) && (blogMap.get(id).getPassword()).equals(requestDto.getPassword())) {
            blogMap.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택하신 게시글은 존재하지 않거나 입력하신 비밀번호가 틀렸습니다.");
        }
    }
}
