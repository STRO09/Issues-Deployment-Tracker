package daoimplementors;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import daointerfaces.DeploymentDAO;
import models.Deployment;
import models.Issue;
import utils.HibernateUtil;

public class DeploymentImplementor implements DeploymentDAO {

	@Override
	public void deployProject(Deployment deployment) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.persist(deployment);
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
	public Deployment findDeploymentById(Long deployid) {
		// TODO Auto-generated method stub
		Deployment deployment = null;
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			deployment = session.get(Deployment.class, deployid);
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

		return deployment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Deployment> findDeploymentsByProjectId(Long projectid) {
		// TODO Auto-generated method stub
		List<Deployment> deployments = null;
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			deployments = session.createQuery("FROM Deployment d where project.id = :projectid")
					.setParameter("projectid", projectid).getResultList();

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

		return deployments;
	}

	@Override
	public void updateDeployment(Deployment deployment) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(deployment);
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
	public void linkIssue(Long deployId, Long issueId) {
		// TODO Auto-generated method stub
		Transaction transaction = null;

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			// Load deployment and issue
			Deployment deployment = session.get(Deployment.class, deployId);
			Issue issue = session.get(Issue.class, issueId);

			if (deployment == null || issue == null) {
				throw new IllegalArgumentException("Deployment or Issue not found!");
			}

			// Add issue to deployment
			deployment.getIssues().add(issue);

			// Save update (since cascade is likely not set)
			session.merge(deployment);

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
	public void deleteDeployment(Long deployid) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		Deployment deployment = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			deployment = session.get(Deployment.class, deployid);
			if (deployment != null) {
				session.delete(deployment);
				transaction.commit();
			} else {
				transaction.rollback();
				System.out.println("Deployment with the given id not found");
			}
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
