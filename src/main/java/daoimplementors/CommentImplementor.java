package daoimplementors;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import daointerfaces.CommentDAO;
import models.Comment;
import models.Issue;
import models.Project;
import models.User;
import utils.HibernateUtil;

public class CommentImplementor implements CommentDAO {

	@Override
	public void addComment(Comment comment) {
		Transaction transaction = null;

		// TODO Auto-generated method stub
		try (Session session = HibernateUtil.getSession()) {
			transaction = session.beginTransaction();
			if (comment != null) {
				session.persist(comment);
				transaction.commit();
			} else {
				transaction.rollback();
				System.out.println("Comment is empty");
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findCommentByIssueId(Long issueid) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		List<Comment> comments = null;

		// TODO Auto-generated method stub
		try (Session session = HibernateUtil.getSession()) {
			transaction = session.beginTransaction();
			if (session.get(Issue.class, issueid) != null) {

				comments = session.createQuery("FROM Comment c where c.issue.id = :issueid")
						.setParameter("issueid", issueid).getResultList();
				transaction.commit();
			} else {
				transaction.rollback();
				System.out.println("No comments for the given issue");
			}

		} catch (Exception e) {
			// TODO: handle exception
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();

		}

		return comments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findCommentByUserId(Long userid) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		List<Comment> comments = null;

		// TODO Auto-generated method stub
		try (Session session = HibernateUtil.getSession()) {
			transaction = session.beginTransaction();
			if (session.get(User.class, userid) != null) {

				comments = session.createQuery("FROM Comment c where c.author.id = :userid")
						.setParameter("userid", userid).getResultList();
				transaction.commit();
			} else {
				transaction.rollback();
				System.out.println("No Comments by the given user");
			}

		} catch (Exception e) {
			// TODO: handle exception
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();

		}

		return comments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findCommentByProjectId(Long projectid) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		List<Comment> comments = null;

		// TODO Auto-generated method stub
		try (Session session = HibernateUtil.getSession()) {
			transaction = session.beginTransaction();
			if (session.get(Project.class, projectid) != null) {

				comments = session
						.createQuery("SELECT c FROM Comment c JOIN c.issue i JOIN i.project p where p.id = :projectid")
						.setParameter("projectid", projectid).getResultList();
				transaction.commit();
			} else {
				transaction.rollback();
				System.out.println("No Comments for the given project");
			}

		} catch (Exception e) {
			// TODO: handle exception
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();

		}

		return comments;
	}

	@Override
	public void deleteComment(Long id) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		Comment comment = null;
		try (Session session = HibernateUtil.getSession()) {
			transaction = session.beginTransaction();
			comment = session.get(Comment.class, id);
			if (comment != null) {
				session.delete(comment);
				System.out.println("Comment deleted successfully");
				transaction.commit();
			} else {
				transaction.rollback();
				System.out.println("No such comment by the given id");
			}
		} catch (Exception e) {
			// TODO: handle exception
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();

		}

	}

}
