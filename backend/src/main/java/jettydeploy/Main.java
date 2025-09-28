package jettydeploy;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import filters.AuthFilter;
import filters.CorsFilter;
import servlets.RegisterServlet;
import servlets.SessionValidateServlet;
import servlets.UserServlet;
import servlets.LoginServlet;
import servlets.LogoutServlet;

public class Main {
	public static void main(String[] args) throws Exception {
		// 1. Create Jetty server on port 8080
		Server server = new Server(8080);

		// 2. Set up a Servlet context handler
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/IssuesandDeploymentTracker"); // base path
		server.setHandler(context);

		// 3. Register servlets & Filters
		context.addFilter(new FilterHolder(new CorsFilter()), "/*", null);
		context.addFilter(new FilterHolder(new AuthFilter()), "/*", null);
		context.addServlet(new ServletHolder(new RegisterServlet()), "/api/auth/register");
		context.addServlet(new ServletHolder(new LoginServlet()), "/api/auth/login");
		context.addServlet(new ServletHolder(new UserServlet()), "/api/users/*");
		context.addServlet(new ServletHolder(new SessionValidateServlet()), "/api/auth/validate");
		context.addServlet(new ServletHolder(new LogoutServlet()), "/api/auth/logout");

		// 4. Start the server
		try {
			server.start();
			System.out.println("Server started at http://localhost:8080");
			server.join(); // keep running
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			server.destroy();
		}
	}
}
