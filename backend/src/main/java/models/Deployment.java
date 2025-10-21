package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "deployments", uniqueConstraints = { @UniqueConstraint(columnNames = { "project_id", "version" }) })
public class Deployment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String version; // e.g. v1.0.1

	@Column(columnDefinition = "TEXT")
	private String description; // release notes or summary

	@Column(columnDefinition = "TEXT")
	private String url;

	@Column(nullable = false, updatable = false)
	private LocalDateTime deployedAt;

	@PrePersist
	protected void onDeploy() {
		deployedAt = LocalDateTime.now();
	}

	// ---------------- RELATIONSHIPS ----------------

	// Deployment belongs to one project
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	// Deployment can fix/contain many issues
	@ManyToMany(mappedBy = "deployments")
	private Set<Issue> issues = new HashSet<>();

	// ---------------- CONSTRUCTORS ----------------

	public Deployment() {
	}

	public Deployment(String version, String description, String url, Project project) {
		this.version = version;
		this.description = description;
		this.url = url;
		this.project = project;
	}

	// ---------------- GETTERS & SETTERS ----------------

	public Long getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getDeployedAt() {
		return deployedAt;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Set<Issue> getIssues() {
		return issues;
	}

	public void setIssues(Set<Issue> issues) {
		this.issues = issues;
	}

	@Override
	public String toString() {
		return "Deployment [id=" + id + ", version=" + version + ", description=" + description + ", deployedAt="
				+ deployedAt + ", project=" + project.getId() + "]";
	}
}
