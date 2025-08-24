package daointerfaces;

import java.util.List;

import models.Issue;
import models.Issue.Priority;
import models.Issue.Status;

public interface IssueDAO {

	public void createIssue(Issue issue);
	
	public Issue findIssueById(Long issueid);
	
	public List<Issue> findIssuesByProject(Long projectid);
	
	public List<Issue> findIssueByAssignedUser(Long userId);
	
	public List<Issue> findByStatus(Status status);

	public List<Issue> findByPriority(Priority priority);
	
	public List<Issue> viewIssuesPerVersion(Long deployId);
	
	public void updateIssue(Issue issue);
	
	public void deleteIssue(Long issueid);
	
}
