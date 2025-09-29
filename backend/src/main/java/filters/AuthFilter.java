package filters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {

	public AuthFilter() {
		super();
		System.out.println(">>> AuthFilter constructor loaded, class = " + this.getClass().getName());
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String path = request.getRequestURI();
		System.out.println("AuthFilter hit for: " + request.getRequestURI());
		System.out.println(path);

		// whitelist login & register
		if (path.endsWith("/api/auth/login") || path.endsWith("/api/auth/register")) {
			chain.doFilter(request, response); 
			return;
		}

		try {
			String token = null;
			if (request.getCookies() != null) {
				for (Cookie c : request.getCookies()) {
					if (c.getName().equals("token")) {
						token = c.getValue();
						break;
					}
				}
			}

			if (token == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("{\"message\":\"Unauthorized\"}");
				return;
			}

			Properties properties = new Properties();
			try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties")) {
				properties.load(inputStream);
			}
			String SECRET_KEY = properties.getProperty("jwt.SECRET_KEY");

			System.out.println(">>> Path: " + path);
			System.out.println(">>> Token (first 20 chars): " + token.substring(0, 20));
			System.out.println(">>> SECRET_KEY (length): " + (SECRET_KEY == null ? "null" : SECRET_KEY.length()));
			System.out.println(">>> SECRET_KEY (raw): '" + SECRET_KEY + "'");

			Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
					.build().parseClaimsJws(token).getBody();

			request.setAttribute("id", claims.getSubject());
			request.setAttribute("fullName", (String) claims.get("fullName"));
			request.setAttribute("email", (String) claims.get("email"));
			request.setAttribute("role", (String) claims.get("role"));

			// pass the request along the filter chain
			chain.doFilter(req, resp);

		} catch (Exception e) {
			System.out.print(e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("{\"message\":\"Invalid token\"}");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
