package servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import daoimplementors.UserImplementor;
import daointerfaces.UserDAO;
import dtos.UserDTO;
import models.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/api/users/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userdao = new UserImplementor();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
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
		String path = request.getRequestURI();
		System.out.println("UserServlet hit for: " + request.getRequestURI());
		System.out.println(path);
		ObjectMapper mapper = new ObjectMapper();

		if (path == null || path.equals("/IssuesandDeploymentTracker/api/users")) {
			List<User> users = userdao.findAll();
			List<UserDTO> userDtos = users.stream()
					.map(u -> new UserDTO(u.getId(), u.getFullName(), u.getEmail(),
							(u.getRole() != null) ? u.getRole().toString() : "NONE"))
					.collect(Collectors.toList());
			response.setContentType("application/json");
			mapper.writeValue(response.getWriter(), userDtos);
			return;

		} else {

		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.getWriter().write("{\"message\":\"Endpoint not found\"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
