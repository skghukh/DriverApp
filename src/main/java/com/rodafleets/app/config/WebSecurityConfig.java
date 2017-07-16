package com.rodafleets.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rodafleets.app.auth.jwt.JwtAuthenticationEntryPoint;
import com.rodafleets.app.auth.jwt.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${api.version}")
    private String apiVersion;
	
	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		// we don't need CSRF because our token is invulnerable
		.csrf().disable()

		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

		// don't create session
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

		.authorizeRequests()
		//.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

		// allow anonymous resource requests
		.antMatchers(
				HttpMethod.GET,
				"/",
				"/*.html",
				"/favicon.ico",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js"
				).permitAll()
		.antMatchers("/" + apiVersion + "/drivers/login").permitAll()
		.antMatchers("/" + apiVersion + "/customers/login").permitAll()
		.antMatchers("/" + apiVersion + "/admin/login").permitAll()
		
		.antMatchers(HttpMethod.POST, "/drivers/{^[\\d]$}").authenticated()
		.antMatchers(HttpMethod.POST, "/drivers/**").permitAll()
		//allow generic api
		.antMatchers("/" + apiVersion + "/vehicle/types").permitAll();
		
		//for now commenting to check local requests
		

		// Custom JWT based security filter
		http
		.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		// disable page caching
		http.headers().cacheControl();
	}
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		// Create a default account
//		auth.inMemoryAuthentication()
//		.withUser("admin")
//		.password("password")
//		.roles("ADMIN");
//	}
}