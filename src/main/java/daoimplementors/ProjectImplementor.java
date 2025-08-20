package daoimplementors;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import daointerfaces.ProjectDAO;
import models.Project;
import utils.HibernateUtil;

public class ProjectImplementor implements ProjectDAO {

    @Override
    public void createProject(Project project) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.persist(project);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Project getProjectById(Long id) {
        Transaction transaction = null;
        Project project = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            project = session.get(Project.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return project;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Project> getAllProjects() {
        Transaction transaction = null;
        List<Project> projects = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            projects = session.createQuery("FROM Project").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return projects;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Project> getProjectsByUserId(Long userid) {
        Transaction transaction = null;
        List<Project> projects = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            projects = session.createQuery(
                "SELECT pm.project FROM ProjectMember pm WHERE pm.user.id = :userid"
            )
            .setParameter("userid", userid)
            .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public void updateProject(Project project) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.merge(project);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProject(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Project project = session.get(Project.class, id);
            if (project != null) {
                session.delete(project);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
