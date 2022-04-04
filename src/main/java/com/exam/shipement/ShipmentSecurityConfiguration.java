package com.exam.shipement;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class ShipmentSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.inMemoryAuthentication()
		    .withUser("vishnu")
		    .password("vishnu")
		    .roles("Admin")
		    .and()
		    .withUser("user1")
			.password("user")
	        .roles("user");
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeHttpRequests().antMatchers("/demo/orders","/demo/products").hasAnyRole("Admin","user")
		                            .antMatchers("/demo/**").hasRole("Admin")
		                            .and().formLogin();
	}
	
	
	
	
	  @Bean public PasswordEncoder getPasswordEncoder() { 
		  return NoOpPasswordEncoder.getInstance(); }
	 
	
	
	

}
