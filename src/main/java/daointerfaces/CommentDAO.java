package daointerfaces;

import java.util.List;

import models.Comment;

public interface CommentDAO {

	public void addComment(Comment comment);

	public List<Comment> findCommentByIssueId(Long issueid);

	public List<Comment> findCommentByUserId(Long userid);

	public List<Comment> findCommentByProjectId(Long projectid);

	public void deleteComment(Long id);
}
