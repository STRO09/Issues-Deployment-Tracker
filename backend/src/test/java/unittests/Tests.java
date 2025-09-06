package unittests;

import org.hibernate.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import daoimplementors.UserImplementor;
import daointerfaces.UserDAO;
import models.User;
import utils.HibernateUtil;

public class Tests {

	Session session;
	UserDAO dao = new UserImplementor();

	@BeforeEach
	void open() {
		session = HibernateUtil.getSession();
	}

	@AfterEach
	void close() {
		if (session != null)
			session.close();

	}
	
	@Test
	void saveUser() {
		Transaction transaction = session.beginTransaction();
		User u = new User("testuser", "tu@example.com", "hash", "Test User", User.Role.DEVELOPER);
        boolean status = dao.registerUser(u);
        assertTrue(status, "User Registration Test passed");
        
        transaction.commit();
	}
	
	@Test 
	void fetchUser() {
		User fetcheduser = session.get(User.class, 1L);
		assertEquals("testuser", fetcheduser.getFullName());
		
		
	}
}
