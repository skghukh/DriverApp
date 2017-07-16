package com.rodafleets.app.auth.jwt;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rodafleets.app.dataaccess.DriverRepository;
import com.rodafleets.app.model.Driver;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private DriverRepository driverRepo;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String authToken = request.getHeader(this.tokenHeader);
		
		//API Access Rights
		logger.info("request = " + request.getRequestURI().toString());
		// authToken.startsWith("Bearer ")
		// String authToken = header.substring(7);
		String username = jwtTokenUtil.getDriverFromToken(authToken);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			// It is not compelling necessary to load the use details from the database. You could also store the information
			// in the token and read it from it. It's up to you ;)
			Driver driver = driverRepo.findOneByPhoneNumber(username);

			// For simple validation it is completely sufficient to just check the token integrity. You don't have to call
			// the database compellingly. Again it's up to you ;)
			if (jwtTokenUtil.validateDriverToken(authToken, driver)) {
				logger.info("token validated");
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(driver, null, emptyList());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}