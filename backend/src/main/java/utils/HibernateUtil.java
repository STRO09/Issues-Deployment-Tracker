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
			String env = System.getenv("ENVIRONMENT"); // "test" or "prod" or "dev"
			boolean loaded = false;
			if (env == null)
				env = "dev";
			String propsFile;
			switch (env) {
				case "test":
					propsFile = "db-test.properties";
					break;
				case "prod":
					propsFile = null;
					loaded = true;
					break;
				default:
					propsFile = "db.properties";
			}

			Properties properties = new Properties();


			if (propsFile != null) {

				try (InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream(propsFile)) {
					if (inputStream == null) {
						throw new RuntimeException("Unable to find hibernate.properties");
					}
					properties.load(inputStream);
					loaded = true;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("Failed to load db.properties", e);
				}
			} else {
				System.out.println("Using environment variables for DB config");

				properties.setProperty(
						"hibernate.connection.driver_class",
						System.getenv("hibernate.connection.driver_class"));
				properties.setProperty(
						"hibernate.connection.url",
						System.getenv("hibernate.connection.url"));
				properties.setProperty(
						"hibernate.connection.username",
						System.getenv("hibernate.connection.username"));
				properties.setProperty(
						"hibernate.connection.password",
						System.getenv("hibernate.connection.password"));
				properties.setProperty(
						"hibernate.dialect",
						System.getenv("hibernate.dialect"));

				properties.setProperty("hibernate.hbm2ddl.auto", "update");
				properties.setProperty("hibernate.show_sql", "false");
				properties.setProperty("hibernate.format_sql", "false");

			}
			if (!loaded) {
				System.out.println("Falling back to environment variables for DB config...");

				String dbUrl = System.getenv("DB_URL");
				String dbUser = System.getenv("DB_USER");
				String dbPass = System.getenv("DB_PASS");

				if (dbUrl == null || dbUser == null || dbPass == null) {
					throw new RuntimeException("Database environment variables not set (DB_URL, DB_USER, DB_PASS)");
				}

				properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
				properties.setProperty("hibernate.connection.url", dbUrl);
				properties.setProperty("hibernate.connection.username", dbUser);
				properties.setProperty("hibernate.connection.password", dbPass);
				properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");

				// Optional Hibernate tuning
				properties.setProperty("hibernate.show_sql", "true");
				properties.setProperty("hibernate.format_sql", "true");
				properties.setProperty("hibernate.hbm2ddl.auto", "update");
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

		} catch (Throwable ex) {
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
