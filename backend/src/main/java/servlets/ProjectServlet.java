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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import daoimplementors.ProjectImplementor;
import daointerfaces.ProjectDAO;
import dtos.ProjectDTO;
import models.Project;

/**
 * Servlet implementation class ProjectServlet
 */
@WebServlet("/api/projects/*")
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProjectDAO projectdao = new ProjectImplementor();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectServlet() {
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
		// TODO Auto-generated method stub
		String path = request.getRequestURI();
		System.out.println("Project Servlet hit for: " + request.getRequestURI());
		System.out.println(path);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		if (path == null || path.equals("/IssuesandDeploymentTracker/api/projects")) {
			List<Project> projects = projectdao.getAllProjects();
			List<ProjectDTO> projectDtos = projects.stream()
					.map(p -> new ProjectDTO(p.getId(), p.getName(),
							(p.getDescription() != null) ? p.getDescription() : "", p.getCreatedAt(), p.getUpdatedAt()))
					.collect(Collectors.toList());

			response.setContentType("application/json");
			mapper.writeValue(response.getWriter(), projectDtos);
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

}
