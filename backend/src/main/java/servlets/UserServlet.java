package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import daoimplementors.UserImplementor;
import daointerfaces.UserDAO;
import dtos.UserDTO;
import models.User;
import models.User.Role;

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
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    if ("PATCH".equalsIgnoreCase(req.getMethod())) {
	        doPatch(req, resp);
	    } else {
	        super.service(req, resp);
	    }
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
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		if (path == null || path.equals("/IssuesandDeploymentTracker/api/users")) {
			List<User> users = userdao.findAll();
			List<UserDTO> userDtos = users.stream()
					.map(u -> new UserDTO(u.getId(), u.getFullName(), u.getEmail(),
							(u.getRole() != null) ? u.getRole().toString() : "NONE", u.getCreatedAt(),
							u.getUpdatedAt()))
					.collect(Collectors.toList());
			response.setContentType("application/json");
			mapper.writeValue(response.getWriter(), userDtos);
			return;

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

	protected void doPatch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getRequestURI();
		System.out.println("UserServlet hit for: " + request.getRequestURI());
		System.out.println(path);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		if (path.equals("/IssuesandDeploymentTracker/api/users/changeRole")) {

			if ("PATCH".equalsIgnoreCase(request.getMethod())) {
				try {
					// Read JSON body

					Map<String, Object> body = mapper.readValue(request.getReader(), Map.class);
					
					Long id = Long.valueOf(body.get("id").toString());
					Object roleObj = body.get("role");
					Role role = null;
					if (roleObj != null && !"NONE".equals(roleObj.toString())) {
					    role = Role.valueOf(roleObj.toString());
					}


					boolean success = userdao.changeRole(id, role);

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");

					if (success) {
						response.setStatus(HttpServletResponse.SC_OK);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("{\"message\":\"Role updated successfully\"}");
					} else {
						response.setStatus(HttpServletResponse.SC_NOT_FOUND);
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write("{\"message\":\"User not found or some other error\"}");
					}

				} catch (Exception e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().write("{\"error\":\"Server error\"}");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		}
	}

}
