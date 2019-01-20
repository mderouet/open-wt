package application.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import com.google.common.base.CharMatcher;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean {

	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final String authHeader = request.getHeader("authorization");

		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);

			chain.doFilter(req, res);
		} else {

			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				throw new ServletException("Missing or invalid Authorization header");
			}

			final String token = authHeader.substring(7);
			final Claims claims;
			try {
				claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
				request.setAttribute("claims", claims);
			} catch (final SignatureException e) {
				throw new ServletException("Invalid token");
			}

			// The whole logic up there is checking for header and whether the token is
			// valid or not, since we want to provide authorization, we will use the id of
			// our Claims object and the request to see whether our client is authorized..
			// OR NOT

			// Consider this more as a "proof of concept" more than an implementation of
			// security since it's not maintainable.

			// Example restriction : api/contact route, PUT http method, a contact can only
			// change HIS informations
			if (request.getRequestURI().contains("api/contact") && request.getMethod().contains("PUT")) {
				// Extracting id of request
				String idParamRequest = CharMatcher.digit().retainFrom(request.getRequestURI());

				// comparing token id with request parameter (should be the same)
				if (idParamRequest != claims.get("id")) {
					throw new ServletException(
							"Your token is valid : " + claims.getSubject() + " with id " + claims.get("id")
									+ " but you can't change informations of the contact id : " + idParamRequest);
				}
			}
			chain.doFilter(req, res);
		}
	}
}
