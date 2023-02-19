package com.application.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class AppConfig {
	@Bean
	public SecurityFilterChain springSecurityConfiguration(HttpSecurity http)throws Exception {
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.csrf().disable()
		.authorizeHttpRequests()
		
		.requestMatchers(HttpMethod.POST, "/user/register").permitAll()
		.requestMatchers("/v3/api-docs").permitAll()
		.requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN","USER")
		.requestMatchers(HttpMethod.GET, "/placeOrder/**").hasAnyRole("ADMIN","USER")
		.requestMatchers(HttpMethod.GET, "/wishList/**").hasAnyRole("ADMIN","USER")
		.requestMatchers(HttpMethod.GET, "/cart/**").hasAnyRole("ADMIN","USER")
         .requestMatchers(HttpMethod.GET, "/product/name/**").hasAnyRole("ADMIN","USER")
		.requestMatchers(HttpMethod.PUT, "/user/update/**").hasAnyRole("ADMIN","USER")
		.requestMatchers( "/category/**").hasRole("ADMIN")
		.requestMatchers( "/product/**" ).hasRole("ADMIN")
		.requestMatchers("/user/**").hasRole("ADMIN")
		.anyRequest().authenticated().and()
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.formLogin()
		.and()
		.httpBasic();
		
		return http.build();
		
		 
		 
		
		
		 
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
