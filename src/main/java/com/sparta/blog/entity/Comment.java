package com.sparta.blog.entity;

import com.sparta.blog.dto.CommentRequestDto;
import com.sparta.blog.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
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

	@Column(name = "likeCount")
	private Long likeCount; // 좋아요 개수

	@ManyToOne
	@JoinColumn(name = "blog_id")
	private Blog blog;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Like> likes;

	public Comment(Blog blog, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
		this.username = userDetails.getUsername();
		this.comment = requestDto.getComment();
		this.blog = blog;
		this.user = userDetails.getUser();
		this.likeCount = 0L;
	}

	public void update(CommentRequestDto comment) {
		this.comment = comment.getComment();
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}
}
