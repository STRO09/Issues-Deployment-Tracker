package daoimplementors;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.omg.PortableInterceptor.SUCCESSFUL;

import daointerfaces.UserDAO;
import models.User;
import models.User.Role;
import utils.HibernateUtil;

public class UserImplementor implements UserDAO {

	@Override
	public boolean registerUser(User user) {
		// TODO Auto-generated method stub
		boolean success;
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
				success = true;
				System.out.println("User registered successfully");
			} else {
				success = false;
				System.out.println("User with the same email already exists");
			}
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
	public boolean updateProfile(User user) {
		// TODO Auto-generated method stub
		boolean success;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			User existingUser = session.createQuery("FROM User u WHERE u.id = :id", User.class)
					.setParameter("id", user.getId()).uniqueResult();

			if (existingUser != null) {
				session.merge(user);
				transaction.commit();
				success = true;
				System.out.println("User data updated successfully");
			} else {
				success = false;
				System.out.println("No user with the provided id exists");
			}

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
	public boolean deleteUser(Long id) {
		// TODO Auto-generated method stub
		boolean success;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			User user = session.get(User.class, id);
			if (user != null) {
				session.delete(user);
				transaction.commit();
				success = true;
				System.out.println("User deleted successfully");
			} else {
				transaction.rollback();
				success = false;
				System.out.println("No user with the provided id exists");
			}

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

	@Override
	public boolean changeRole(Long id, Role role) {
		// TODO Auto-generated method stub
		boolean success;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			User existingUser = session.createQuery("FROM User u WHERE u.id = :id", User.class).setParameter("id", id)
					.uniqueResult();

			if (existingUser != null) {
				existingUser.setRole(role);
				session.merge(existingUser);
				transaction.commit();
				success = true;
				System.out.println("User data updated successfully");
			} else {
				success = false;
				System.out.println("No user with the provided id exists");
			}

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
