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
	public boolean addMember(ProjectMember member) {
    	boolean success ;
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(member);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			success = false;
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
		
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return success;
	}

	@Override
	public boolean removeMember(Long projectmemberid) {
    	boolean success ;
		Transaction transaction = null;
		ProjectMember member = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			member = session.get(ProjectMember.class, projectmemberid);
			session.delete(member);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			success = false;
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return success;
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
	public boolean updateProjectRole(ProjectMember member) {
    	boolean success ;
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(member);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			success = false;
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return success;
	}
}
