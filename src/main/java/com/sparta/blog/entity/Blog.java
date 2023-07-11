package com.sparta.blog.entity;

import com.sparta.blog.dto.BlogRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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

	@OneToMany(mappedBy = "blog", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Comment> comments;

    public Blog(BlogRequestDto requestDto, String author) {
        this.title = requestDto.getTitle();
        this.author = author;
        this.contents = requestDto.getContents();
    }

    public void update(BlogRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

}
