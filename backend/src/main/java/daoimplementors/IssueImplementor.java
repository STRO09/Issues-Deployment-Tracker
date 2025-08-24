package daoimplementors;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import daointerfaces.IssueDAO;
import models.Issue;
import models.Issue.Priority;
import models.Issue.Status;
import utils.HibernateUtil;

public class IssueImplementor implements IssueDAO {

    @Override
    public void createIssue(Issue issue) {
        Transaction transaction = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.persist(issue);
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
    }

    @Override
    public Issue findIssueById(Long issueid) {
        Transaction transaction = null;
        Issue issue = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            issue = session.get(Issue.class, issueid);
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
        return issue;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Issue> findIssuesByProject(Long projectid) {
        Transaction transaction = null;
        List<Issue> issues = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            issues = session.createQuery("FROM Issue i WHERE i.project.id = :projectid")
                    .setParameter("projectid", projectid)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
        return issues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Issue> findIssueByAssignedUser(Long userId) {
        Transaction transaction = null;
        List<Issue> issues = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            issues = session.createQuery("FROM Issue i WHERE i.assignedTo.id = :userId")
                    .setParameter("userId", userId)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
        return issues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Issue> findByStatus(Status status) {
        Transaction transaction = null;
        List<Issue> issues = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            issues = session.createQuery("FROM Issue i WHERE i.status = :status")
                    .setParameter("status", status)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
        return issues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Issue> findByPriority(Priority priority) {
        Transaction transaction = null;
        List<Issue> issues = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            issues = session.createQuery("FROM Issue i WHERE i.priority = :priority")
                    .setParameter("priority", priority)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
        return issues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Issue> viewIssuesPerVersion(Long deployId) {
        Transaction transaction = null;
        List<Issue> issues = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            issues = session.createQuery("SELECT i FROM Issue i JOIN i.deployments d WHERE d.id = :deployId")
                    .setParameter("deployId", deployId)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
        return issues;
    }

    @Override
    public void updateIssue(Issue issue) {
        Transaction transaction = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            session.merge(issue);
            transaction.commit();
        } catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
    }

    @Override
    public void deleteIssue(Long issueid) {
        Transaction transaction = null;
        Session session = null;
		try {
			session = HibernateUtil.getSession();
            transaction = session.beginTransaction();
            Issue issue = session.get(Issue.class, issueid);
            if (issue != null) {
                session.delete(issue);
            }
            transaction.commit();
        }catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
    }
}
