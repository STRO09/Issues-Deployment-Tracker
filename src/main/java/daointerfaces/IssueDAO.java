package daointerfaces;

import java.util.List;

import models.Issue;

public interface IssueDAO {

	public void createIssue(Issue issue);
	
	public Issue findIssueById(Long issueid);
	
	public List<Issue> findIssuesByProject(Long projectid);
	
	public List<Issue> findIssueByAssignedUser(Long userId);
	
	public List<Issue> findByStatus(String status);

	public List<Issue> findByPriority(String priority);
	
	public void updateIssue(Issue issue);
	
	public void deleteIssue(Long issueid);
	
}
