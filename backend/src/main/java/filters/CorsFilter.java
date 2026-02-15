package filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

	private Set<String> allowedOrigins = Collections.emptySet();

	public CorsFilter() {
		super();
		System.out.println(">>> CorsFilter constructor loaded, class = " + this.getClass().getName());
	}

	public void destroy() {
		// no-op
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpServletRequest request = (HttpServletRequest) req;

		String origin = request.getHeader("Origin");
		System.out.println("CorsFilter hit for: " + request.getRequestURI() + " origin=" + origin);

		// If the request Origin is in our whitelist, echo it back (required when credentials are used)
		if (origin != null && allowedOrigins.contains(origin)) {
			response.setHeader("Access-Control-Allow-Origin", origin);
			response.setHeader("Vary", "Origin");
			response.setHeader("Access-Control-Allow-Credentials", "true");
		}

		// Allow commonly requested methods and headers. If preflight requests ask for
		// specific headers we echo those back as well.
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");

		String reqHeaders = request.getHeader("Access-Control-Request-Headers");
		if (reqHeaders != null && !reqHeaders.isEmpty()) {
			response.setHeader("Access-Control-Allow-Headers", reqHeaders);
		} else {
			response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
		}

		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");

		// Handle preflight immediately
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(req, resp);
	}

	/**
	 * Read a comma-separated init-param named "allowedOrigins" from web.xml.
	 * Fallback to the environment variable "ALLOWED_ORIGINS" if not present.
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		String init = fConfig.getInitParameter("allowedOrigins");
		if (init == null || init.trim().isEmpty()) {
			String env = System.getenv("ALLOWED_ORIGINS");
			init = env;
		}

		if (init != null && !init.trim().isEmpty()) {
			String[] parts = init.split(",");
			Set<String> set = new HashSet<>();
			for (String p : parts) {
				String t = p.trim();
				if (!t.isEmpty()) set.add(t);
			}
			allowedOrigins = Collections.unmodifiableSet(set);
		} else {
			// sensible defaults for local dev
			allowedOrigins = new HashSet<>(Arrays.asList("http://localhost:3000", "http://127.0.0.1:3000"));
		}

		System.out.println("CorsFilter allowedOrigins=" + allowedOrigins);
	}

}
