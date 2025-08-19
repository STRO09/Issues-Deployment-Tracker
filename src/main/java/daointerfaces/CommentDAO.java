package daointerfaces;

import java.util.List;

import models.Comment;
import models.Issue;

public interface CommentDAO {

	public void addComment(Comment comment);
	
	public List<Issue> findCommentByIssueId(Long issueid);
	
	public List<Issue> findCommentByUserId(Long userid);
	
	public List<Issue> findCommentByProjectId(Long projectid);
	
	public void deleteComment(Long id);
}
