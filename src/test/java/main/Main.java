package main;

import daoimplementors.*;
import daointerfaces.*;
import models.*;
import utils.HibernateUtil;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		try {
			// --- DAO upcasting ---
			UserDAO userDAO = new UserImplementor();
			ProjectDAO projectDAO = new ProjectImplementor();
			IssueDAO issueDAO = new IssueImplementor();
			CommentDAO commentDAO = new CommentImplementor();

			// ------------------- USER -------------------
//			User user = new User("johndoe12", "john@example.com", "password123", "John Doe", User.Role.DEVELOPER);
//			userDAO.registerUser(user);
//			System.out.println("✅ User created: " + user.getUsername());

			User fetchedUser = userDAO.getUserById(1L);
			System.out.println("🔎 Fetched User: " + fetchedUser);

			fetchedUser.setEmail("newjohn@example.com");
			userDAO.updateProfile(fetchedUser);
			System.out.println("✏️ Updated User Email: " + userDAO.getUserById(1L).getEmail());

//			userDAO.deleteUser(user.getId());
//			System.out.println("🗑️ Deleted User with id: " + user.getId());
//
//			// ------------------- PROJECT -------------------
//			Project project = new Project("Bug Tracker", "A simple issue tracker");
//			projectDAO.createProject(project);
//			System.out.println("✅ Project created: " + project.getName());
//
//			List<Project> projects = projectDAO.getAllProjects();
//			System.out.println("📂 All Projects: " + projects);
//
//			project.setDescription("Issue Tracker Pro");
//			projectDAO.updateProject(project);
//			System.out.println("✏️ Updated Project: " + projectDAO.getProjectById(project.getId()));
//
//			projectDAO.deleteProject(project.getId());
//			System.out.println("🗑️ Deleted Project with id: " + project.getId());
//
//			// ------------------- ISSUE -------------------
//			// Re-create project & user for issue demo
//			userDAO.registerUser(user);
//			projectDAO.createProject(project);
//
//			Issue issue = new Issue("Login bug", Issue.Priority.HIGH, Issue.Status.OPEN, project, user);
//			issueDAO.createIssue(issue);
//			System.out.println("✅ Issue created: " + issue.getTitle());
//
//			Issue fetchedIssue = issueDAO.findIssueById(issue.getId());
//			System.out.println("🔎 Fetched Issue: " + fetchedIssue);
//
//			fetchedIssue.setStatus(Issue.Status.IN_PROGRESS);
//			issueDAO.updateIssue(fetchedIssue);
//			;
//			System.out.println("✏️ Updated Issue Status: " + issueDAO.findIssueById(issue.getId()).getStatus());
//
//			issueDAO.deleteIssue(issue.getId());
//			System.out.println("🗑️ Deleted Issue with id: " + issue.getId());
//
//			// ------------------- COMMENT -------------------
//			// Re-create issue for comment demo
//			Issue issueForComment = new Issue("Signup page crash", Issue.Priority.MEDIUM, Issue.Status.OPEN, project,
//					user);
//			issueDAO.createIssue(issueForComment);
//
//			Comment comment = new Comment("This needs urgent fix!", issueForComment,  user);
//			commentDAO.addComment(comment);
//			System.out.println("✅ Comment added: " + comment.getContent());
//
//			List<Comment> comments = commentDAO.findCommentByIssueId(issueForComment.getId());
//			System.out.println("💬 All Comments for Issue: " + comments);
//
//			commentDAO.deleteComment(comment.getId());
//			System.out.println("🗑️ Deleted Comment with id: " + comment.getId());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.shutdown(); // close SessionFactory
		}
	}
}
