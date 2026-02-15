package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.ObjectMapper;

import daoimplementors.UserImplementor;
import daointerfaces.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import models.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDAO userdao = new UserImplementor();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("LoginServlet initialized");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		System.out.println("Login Servlet hit for Request Type: "+request.getMethod());
		try {
			ObjectMapper mapper = new ObjectMapper();
//			mapper.registerModule(new JavaTimeModule());
//			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			User logindata = mapper.readValue(request.getInputStream(), User.class);
			Optional<User> fetcheduserdata =  userdao.findByEmail(logindata.getEmail());
			
			if (!fetcheduserdata.isPresent()) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.setContentType("application/json");
	            response.getWriter().write("{\"message\":\"Invalid email\"}");
	            return;
	        }
			
			User fetcheduser = fetcheduserdata.get();
			
			
			if(!BCrypt.checkpw(logindata.getPassword(), fetcheduser.getPassword() )) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.setContentType("application/json");
	            response.getWriter().write("{\"message\":\"Invalid password\"}");
				return;
			}
			
			
			Properties properties = new Properties();
			try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties") ){
				properties.load(inputStream);
			}
			String SECRET_KEY = properties.getProperty("jwt.SECRET_KEY");
			Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
			
			
			  String jwt = Jwts.builder()
	                  .setSubject(String.valueOf(fetcheduser.getId()))
	                  .claim("fullName", fetcheduser.getFullName())
	                  .claim("email", fetcheduser.getEmail())
	                  .claim("role", fetcheduser.getRole() != null ? fetcheduser.getRole().toString() : "UNASSIGNED")
	                  .setIssuedAt(new Date())
	                  .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24h
	                  .signWith(SignatureAlgorithm.HS256, key)
	                  .compact();
			  
							// create the cookie - use explicit Set-Cookie header so we can include SameSite
							int maxAge = 24 * 60 * 60;
							StringBuilder cookieBuilder = new StringBuilder();
							cookieBuilder.append("token=").append(jwt).append(";");
							cookieBuilder.append(" Path=/;");
							cookieBuilder.append(" HttpOnly;");
							// Mark Secure for production (Render serves HTTPS)
							cookieBuilder.append(" Secure;");
							// Required for cross-site cookies to be sent
							cookieBuilder.append(" SameSite=None;");
							cookieBuilder.append(" Max-Age=").append(maxAge).append(";");

							// Add Set-Cookie header
							response.addHeader("Set-Cookie", cookieBuilder.toString());
			  
			  response.setStatus(HttpServletResponse.SC_OK);
			  response.setContentType("application/json");
			  response.getWriter().write("{\"message\":\"Login successful\",\"fullName\":\"" 
			      + fetcheduser.getFullName() + "\",\"email\":\""
			    		  + fetcheduser.getEmail() + "\",\"role\":\""
			      + (fetcheduser.getRole() != null ? fetcheduser.getRole() : "") + "\"}");
		}
		catch (Exception e) {
			// TODO: handle exception
			 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			 response.setContentType("application/json");	            response.getWriter().write("{\"message\":\"Server error\"}");
	            e.printStackTrace();
		}

	}

}
