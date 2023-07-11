package com.sparta.blog.entity;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Comment")
public class Comment extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String comment;

	@ManyToOne
	@JoinColumn(name = "blog_id")
	private Blog blog;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Comment(Blog blog, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
		this.username = userDetails.getUsername();
		this.comment = requestDto.getComment();
		this.blog = blog;
		this.user = userDetails.getUser();
	}

	public void update(CommentRequestDto comment) {
		this.comment = comment.getComment();
	}
}
