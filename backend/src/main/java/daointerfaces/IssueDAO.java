package daointerfaces;

import java.util.List;

import models.Issue;
import models.Issue.Priority;
import models.Issue.Status;

public interface IssueDAO {

	public boolean createIssue(Issue issue);
	
	public boolean assignIssue(Long issueid,Long userId);
	
	public Issue findIssueById(Long issueid);
	
	public List<Issue> findIssuesByProject(Long projectid);
	
	public List<Issue> findIssueByAssignedUser(Long userId);
	
	public List<Issue> findByStatus(Status status);

	public List<Issue> findByPriority(Priority priority);
	
	public List<Issue> viewIssuesPerVersion(Long deployId);
	
	public boolean updateIssue(Issue issue);
	
	public boolean deleteIssue(Long issueid);
	
}
