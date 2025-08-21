package daoimplementors;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import daointerfaces.ProjectMemberDAO;
import models.ProjectMember;
import utils.HibernateUtil;

public class ProjectMemberImplementor implements ProjectMemberDAO {

	@Override
	public void addMember(ProjectMember member) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(member);
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
	public void removeMember(Long projectmemberid) {
		Transaction transaction = null;
		ProjectMember member = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			member = session.get(ProjectMember.class, projectmemberid);
			session.delete(member);
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
	public List<ProjectMember> findMembersByProject(Long projectId) {
		Session session = null;
		Transaction transaction = null;
		List<ProjectMember> projectmembers = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			projectmembers = session
					.createQuery("FROM ProjectMember pm JOIN FETCH pm.user WHERE pm.project.id = :projectId",
							ProjectMember.class)
					.setParameter("projectId", projectId).getResultList();

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

		return projectmembers;

	}

	@Override
	public void updateProjectRole(ProjectMember member) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(member);
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
}
