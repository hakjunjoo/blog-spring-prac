package com.sparta.blog.entity;

import com.sparta.blog.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "blog")
public class Blog extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title; // 블로그 제목

    @Column(name = "author", nullable = false)
    private String author; // 블로그 작성자

    @Column(name = "contents", nullable = false, length = 500)
    private String contents; // 블로그 글

    @Column(name = "likeCount")
    private Long likeCount; // 좋아요 개수

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Comment> comments;

    @OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Like> likes;

    public Blog(BlogRequestDto requestDto, String author) {
        this.title = requestDto.getTitle();
        this.author = author;
        this.contents = requestDto.getContents();
        this.likeCount = 0L;
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

}
