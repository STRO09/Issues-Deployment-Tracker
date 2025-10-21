package models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // maybe change it back to identity in prod
	private Long id; // Primary Key
//	private int id; // less space and better since its a smaller system

	@Column(unique = true, length = 50)
	private String username;

	@Column(nullable = false, unique = true, length = 254)
	private String email;

	@Column(nullable = false, length = 60)
	private String password; // Store hashed password (BCrypt/Argon2)

	@Column(nullable = false, length = 200)
	private String fullName;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Role role; // ADMIN, DEV, TESTER, PM

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

	// ===== ENUM for Roles =====
	public enum Role {
		ADMIN, DEVELOPER, TESTER, PROJECT_MANAGER
	}

	// ===== Relationships =====
	
	// One Project → Many Issues
	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> projectsCreated;

	// 1. Issues created by this user
	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Issue> issuesCreated;

	// 2. Issues assigned to this user
	@OneToMany(mappedBy = "assignedTo")
	private List<Issue> issuesAssigned;

	// 3. Comments written by this user
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;

	// 4. Project memberships (many-to-many via ProjectMember)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProjectMember> projectMemberships;

	// ===== Lifecycle Callbacks =====
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	// ===== Constructors =====
	public User() {
	}

	public User(String username, String email, String password, String fullName, Role role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
	}

	// ===== Getters & Setters =====
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", fullName=" + fullName + ", role=" + role + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}
}
