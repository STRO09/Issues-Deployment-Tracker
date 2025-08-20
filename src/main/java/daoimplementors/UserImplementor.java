package daoimplementors;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import daointerfaces.UserDAO;
import models.User;
import models.User.Role;
import utils.HibernateUtil;

public class UserImplementor implements UserDAO {

	@Override
	public void registerUser(User user) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			User existingUser = session.createQuery("FROM User u WHERE u.email = :email", User.class)
					.setParameter("email", user.getEmail()).uniqueResult();

			if (existingUser == null) {
				session.persist(user);
				transaction.commit();
				System.out.println("User registered successfully");
			} else {
				System.out.println("User with the same email already exists");
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

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		User user = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			user = session.createQuery("FROM User u where u.email= :email", User.class).setParameter("email", email)
					.uniqueResult();
			transaction.commit();
			System.out.println("User found");
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

		return Optional.ofNullable(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		User user = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			user = session.createQuery("FROM User u where u.username= :username", User.class)
					.setParameter("username", username).uniqueResult();
			transaction.commit();
			System.out.println("USer found");
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

		return Optional.ofNullable(user);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		List<User> users = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			users = session.createQuery("FROM User", User.class).getResultList();
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

		return users;
	}

	@Override
	public List<User> findUsersByRole(Role role) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		List<User> users = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			users = session.createQuery("FROM User u where u.role= :role", User.class).setParameter("role", role)
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
		return users;
	}

	@Override
	public void updateProfile(User user) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		if (getUserById(user.getId()) != null) {
			session.merge(user);
			transaction.commit();
			System.out.println("User data updated successfully");
		} else {
			transaction.rollback();
			System.out.println("No user with the provided email exists");
		}

		session.close();

	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		User user = session.get(User.class, id);
		if (user != null) {
			session.delete(user);
			transaction.commit();
			System.out.println("User deleted successfully");
		} else {
			transaction.rollback();
			System.out.println("No user with the provided id exists");
		}

		session.close();

	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		Transaction transaction = null;
		User user = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			user = session.get(User.class, id);
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
		return user;
	}

}
