package jettydeploy;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import servlets.RegisterServlet;
import servlets.LoginServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Create Jetty server on port 8080
        Server server = new Server(8080);

        // 2. Set up a Servlet context handler
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/IssuesandDeploymentTracker"); // base path
        server.setHandler(context);

        // 3. Register servlets
        context.addServlet(new ServletHolder(new RegisterServlet()), "/api/auth/register");
        context.addServlet(new ServletHolder(new LoginServlet()), "/api/auth/login");

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
