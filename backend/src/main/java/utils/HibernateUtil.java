package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import models.Comment;
import models.Deployment;
import models.Issue;
import models.Project;
import models.ProjectMember;
import models.User;


public class HibernateUtil {

	
	private static final SessionFactory factory;
	
	static {
		
		try {
			String env = System.getenv("ENVIRONMENT"); // "test" or "prod"
			String propsFile = "test".equals(env) ? "db-test.properties" : "db.properties";

		Properties properties = new Properties();
		try(InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream(propsFile)){
			 if (inputStream == null) {
                 throw new RuntimeException("Unable to find hibernate.properties");
             }
			 properties.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Failed to load db.properties", e);
		}
		
		
		Configuration configuration = new Configuration();
		configuration.setProperties(properties);
		
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Deployment.class);
		configuration.addAnnotatedClass(Issue.class);
		configuration.addAnnotatedClass(Project.class);
		configuration.addAnnotatedClass(ProjectMember.class);
		configuration.addAnnotatedClass(Comment.class);
		
		factory = configuration.buildSessionFactory();	
		
		}
		catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}

	
	public static Session getSession() {
		return factory.openSession();
	}
	
	public static void shutdown() {
		factory.close();
	}

	
	
}
