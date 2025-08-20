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
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.save(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeMember(Long projectmemberid) {
        Transaction transaction = null;
        ProjectMember member = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            member = session.get(ProjectMember.class, projectmemberid);
            session.delete(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<ProjectMember> findMembersByProject(Long projectId) {
        try (Session session = HibernateUtil.getSession()) {
            Query<ProjectMember> query = session.createQuery(
                "FROM ProjectMember pm JOIN FETCH pm.user WHERE pm.project.id = :projectId", 
                ProjectMember.class
            );
            query.setParameter("projectId", projectId);
            return query.getResultList();
        }
    }

    @Override
    public void updateProjectRole(ProjectMember member) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.update(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
