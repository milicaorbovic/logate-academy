package com.logate.lacademy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.logate.lacademy.security.components.UserDetailsComponent;
import com.logate.lacademy.security.exceptions.HttpAuthenticationPoint;
import com.logate.lacademy.security.jwt.JWTConfigurer;
import com.logate.lacademy.security.jwt.TokenProvider;

@Description(value = "Spring Security Configuration.")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private HttpAuthenticationPoint authenticationProvider;
	
	@Autowired
	private UserDetailsComponent userDetailsComponent;
	
	@Autowired
    private TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception 
    {
	    	authBuilder
	            .userDetailsService(userDetailsComponent)
	            .passwordEncoder(passwordEncoder());
    }


	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http
			.exceptionHandling().authenticationEntryPoint(authenticationProvider)
			.and()
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/employees/validator").permitAll()
            .antMatchers("/api/users/**").hasAnyRole("ADMIN", "DEVELOPER")
            .antMatchers("/api/employees/**").permitAll()
            .antMatchers("/auth/**").permitAll()
            .antMatchers("/api/file/upload/article/**").permitAll()
            .antMatchers("/api/file/upload/encoded/**").permitAll()
            .antMatchers("/api/file/download/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/articles").hasRole("DEVELOPER")
            .antMatchers(HttpMethod.PUT, "/api/articles").hasAnyRole("ADMIN", "DEVELOPER")
            .antMatchers(HttpMethod.DELETE, "/api/articles").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/api/articles").hasRole("USER")
            .antMatchers(HttpMethod.GET, "/api/articles/{id}").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/api/comments").authenticated()
            .antMatchers(HttpMethod.DELETE, "/api/comments/{id}").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/articles/{id}/comments").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/comments/{id}/likes").permitAll()
            .antMatchers(HttpMethod.PUT, "/api/comments/{id}/dislikes").permitAll()
            .antMatchers(HttpMethod.POST, "/api/roles/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .apply(securityConfigurerAdapter());
	}
	
	private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
