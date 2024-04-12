package com.bike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RajputBrothersApplication {

	public static void main(String[] args) {
		SpringApplication.run(RajputBrothersApplication.class, args);
	}

}



/*
step :
	customUserDetails class-->implementS UserDetails
	
	customUserDetailsService class-->implements UserDetailsService
	
	Create Security class-->
	
	1.create BcryptPsswordEncoder Bean
	2.Create DaoProvider Bean-->userDetails obj, Password encoder
	3.Security chain configuration
	
	
	
	UserNamePasswordAuthToken
	 AuthenticationManager
	           |
	        Provider
	   (DaoAuthenticationProvider)
	           |
	      ----------------- 
	      |               |
	UserDetailsService   PasswordEncoder
	      |
	  UserDetails   
	
*/	