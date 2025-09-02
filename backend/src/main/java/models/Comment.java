package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 150)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	// Relationships -----------------

	// Each comment belongs to ONE issue
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "issue_id", nullable = false)
	private Issue issue;

	// Each comment has ONE author (user)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable = false)
	private User author;

	// Constructors -----------------
	public Comment() {
	}

	public Comment(String title, String content, Issue issue, User author) {
		this.title = title;
		this.content = content;
		this.issue = issue;
		this.author = author;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", createdAt=" + createdAt + ", issue=" + issue.getId()
				+ ", author=" + author.getId() + "]";
	}

	// Lifecycle -----------------
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	// Getters & Setters -----------------

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Issue getIssue() {
		return issue;
	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
