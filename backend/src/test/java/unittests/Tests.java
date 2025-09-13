package unittests;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import daoimplementors.UserImplementor;
import daointerfaces.UserDAO;
import models.User;


public class Tests {
//
//	Session session;
//	UserDAO dao = new UserImplementor();
//
////	@BeforeEach
////	void open() {
////		session = HibernateUtil.getSession();
////	}
////
////	@AfterEach
////	void close() {
////		if (session != null)
////			session.close();
////
////	}
////	
//	@Test
//	void save_fetch_User() {
//
//		User u = new User("testuser", "tu@example.com", "hash", "Test User", User.Role.DEVELOPER);
//        boolean status = dao.registerUser(u);
//        assertTrue(status, "User Registration Test passed");
//        
//		User fetcheduser = dao.getUserById(u.getId());
//		assertEquals("Test User", fetcheduser.getFullName());
//        
//	}

}
