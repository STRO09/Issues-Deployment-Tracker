package main;

import daoimplementors.*;
import daointerfaces.*;
import models.*;
import utils.HibernateUtil;

import java.util.List;

public class Main {
	public static void main(String[] args) {
//		try {
//			// --- DAO upcasting ---
//			UserDAO userDAO = new UserImplementor();
//			ProjectDAO projectDAO = new ProjectImplementor();
//			IssueDAO issueDAO = new IssueImplementor();
//			CommentDAO commentDAO = new CommentImplementor();
//			DeploymentDAO deployDAO  = new DeploymentImplementor();
//
//			// ------------------- USER -------------------
////			User user = new User("stro12", "stro@example.com", "password123", "JohnDoe", User.Role.ADMIN);
////			userDAO.registerUser(user);
////			
////			User user1 = new User("john", "jpo@example.com", "pard123", "John", User.Role.PROJECT_MANAGER);
////			userDAO.registerUser(user1);
////			System.out.println("✅ User created: " + user.getUsername());
////
////			User fetchedUser = userDAO.getUserById(1L);
////			System.out.println("🔎 Fetched User: " + fetchedUser);
////
////			fetchedUser.setEmail("newjohn@example.com");
////			userDAO.updateProfile(fetchedUser);
////			System.out.println("✏️ Updated User Email: " + userDAO.getUserById(1L).getEmail());
////
////			userDAO.deleteUser(fetchedUser.getId());
////			System.out.println("🗑️ Deleted User with id: " + fetchedUser.getId());
////
//			// ------------------- PROJECT -------------------
////			Project project = new Project("Bug Tracker", "A simple bug tracker");
////			projectDAO.createProject(project);
////			System.out.println("✅ Project created: " + project.getName());
////			
////			Project project1 = new Project("Issue Tracker", "A simple issue tracker");
////			projectDAO.createProject(project1);
////			System.out.println("✅ Project created: " + project1.getName());
//
////			List<Project> projects = projectDAO.getAllProjects();
////			System.out.println("📂 All Projects: " + projects);
////
//			
////			Project project = projectDAO.getProjectById(2L);
////			project.setDescription("Issue Tracker Pro");
////			projectDAO.updateProject(project);
////			System.out.println("✏️ Updated Project: " + projectDAO.getProjectById(project.getId()));
//////
////			projectDAO.deleteProject(1L);
////			System.out.println("🗑️ Deleted Project with id: " + project.getId());
////
////			// ------------------- ISSUE -------------------
////			// Re-create project & user for issue demo
//
////
////			Project project = projectDAO.getProjectById(2L);
////			User user = userDAO.getUserById(1L);
////					
////			Issue issue = new Issue("Login bug","Login button not working on first click", Issue.Priority.HIGH, Issue.Status.OPEN, project, user);
////			issueDAO.createIssue(issue);
////			System.out.println("✅ Issue created: " + issue.getTitle());
//////
//			List<Issue> fetchedIssues = issueDAO.findIssuesByProject(2L);
//			System.out.println("🔎 Fetched Issue1 : ");
//			fetchedIssues.forEach(System.out::println);
//			
//			List<Issue> fetchedIssues1 = issueDAO.findByPriority(Issue.Priority.MEDIUM);
//			System.out.println("🔎 Fetched Issue2: ");
//			fetchedIssues1.forEach(System.out::println);
//			
//			List<Issue> fetchedIssues2 = issueDAO.findByStatus(Issue.Status.OPEN);
//			System.out.println("🔎 Fetched Issue3: ");
//			fetchedIssues2.forEach(System.out::println);
////			System.out.println(issueDAO.assignIssue(2L, 1L));
////			
////			List<Issue> fetchedIssues3 = issueDAO.findIssueByAssignedUser();
////			System.out.println("🔎 Fetched Issue4: ");
////			fetchedIssues.forEach(System.out::println);
////			
////			List<Issue> fetchedIssue4 = issueDAO.findIssueById(1L);
////			System.out.println("🔎 Fetched Issue5: ");
////			fetchedIssues.forEach(System.out::println);
//////
////			fetchedIssue.setStatus(Issue.Status.IN_PROGRESS);
////			issueDAO.updateIssue(fetchedIssue);
//////			;
////			System.out.println("✏️ Updated Issue Status: " + issueDAO.findIssueById(fetchedIssue.getId()).getStatus());
//////
////			issueDAO.deleteIssue(fetchedIssue.getId());
//
////			// ------------------- COMMENT -------------------
////			// Re-create issue for comment demo
////			Issue issueForComment = new Issue("Signup page crash","Signup page crashes when blah blah blah", Issue.Priority.MEDIUM, Issue.Status.OPEN, project,
////					fetchedUser);
////			issueDAO.createIssue(issueForComment);
////
////			Comment comment = new Comment("This needs urgent fix!", issueDAO.findIssueById(2L),  userDAO.getUserById(2L));
////			commentDAO.addComment(comment);
////			System.out.println("✅ Comment added: " + comment.getContent());
//////
////			List<Comment> comments = commentDAO.findCommentByProjectId(2L);
////			List<Comment> comments1 = commentDAO.findCommentByUserId(2L);
////			System.out.println("💬 All Comments for Project: " + comments);
////			System.out.println("💬 All Comments for User: " + comments1);
////
////			commentDAO.deleteComment(1L);
////			System.out.println("🗑️ Deleted Comment with id: " + comment.getId());
////			
////			Deployment deployment = new Deployment("1.0.2", "good deployed hehe", projectDAO.getProjectById(2L));
////			deployDAO.deployProject(deployment);
////			deployDAO.linkIssue(deployment.getId(), 2L);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			HibernateUtil.shutdown(); // close SessionFactory
//		}
	}
}
