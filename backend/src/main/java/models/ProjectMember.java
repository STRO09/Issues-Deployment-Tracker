package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.io.Serializable;

@Entity
@Table(name = "project_members", uniqueConstraints = { @UniqueConstraint(columnNames = { "project_id", "user_id" }) })
public class ProjectMember implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Project reference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	// User reference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// Role of user inside this project
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ProjectRole projectRole; // e.g. PROJECT_MANAGER, DEVELOPER, TESTER

	@Column(nullable = false, updatable = false)
	private java.time.LocalDateTime joinedAt;

	@Override
	public String toString() {
		return "ProjectMember [id=" + id + ", project=" + project + ", user=" + user + ", projectRole=" + projectRole
				+ ", joinedAt=" + joinedAt + "]";
	}

	// ===== Lifecycle Callbacks =====
	@PrePersist
	protected void onCreate() {
		joinedAt = java.time.LocalDateTime.now();
	}

	// Getters & Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProjectRole getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(ProjectRole projectRole) {
		this.projectRole = projectRole;
	}

	public java.time.LocalDateTime getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(java.time.LocalDateTime joinedAt) {
		this.joinedAt = joinedAt;
	}
}
