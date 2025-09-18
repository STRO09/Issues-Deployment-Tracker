package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.ObjectMapper;

import daoimplementors.UserImplementor;
import daointerfaces.UserDAO;
import models.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/api/auth/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userdao = new UserImplementor();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String origin = request.getHeader("Origin");
		if (origin != null && origin.endsWith(".vercel.app")) {
		    response.setHeader("Access-Control-Allow-Origin", origin);
		} else {
		    response.setHeader("Access-Control-Allow-Origin", "https://issues-deployment-tracker.vercel.app");
		}
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

		// Handle preflight immediately
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
		    response.setStatus(HttpServletResponse.SC_OK);
		    return;
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		String origin = request.getHeader("Origin");
		if (origin != null && origin.endsWith(".vercel.app")) {
		    response.setHeader("Access-Control-Allow-Origin", origin);
		} else {
		    response.setHeader("Access-Control-Allow-Origin", "https://issues-deployment-tracker.vercel.app");
		}
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

		// Handle preflight immediately
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
		    response.setStatus(HttpServletResponse.SC_OK);
		    return;
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			User incominguser = mapper.readValue(request.getInputStream(), User.class);
			String hashed = BCrypt.hashpw(incominguser.getPassword(), BCrypt.gensalt(12));
			incominguser.setPassword(hashed);

			boolean status = userdao.registerUser(incominguser);

			if (status) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("{\"message\":\"User registered successfully\"}");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("{\"message\":\"Registration failed\"}");
			}

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"message\":\"Server error\"}");
			e.printStackTrace();
		}
	}

}
