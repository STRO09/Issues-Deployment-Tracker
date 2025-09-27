package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtos.UserDTO;

/**
 * Servlet implementation class SessionValidateServlet
 */
@WebServlet("/api/auth/validate")
public class SessionValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionValidateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
        UserDTO userInfo = new UserDTO();
        userInfo.setId(Integer.parseInt((String) request.getAttribute("id")));
        userInfo.setFullname((String) request.getAttribute("fullName"));
        userInfo.setEmail((String) request.getAttribute("email")); // add if available
        userInfo.setRole((String) request.getAttribute("role"));

        response.setContentType("application/json");
        mapper.writeValue(response.getWriter(), userInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
