package com.salah.springSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.salah.springSecurity.jwt.JWTAuthenticationFilter;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final SecurityUserDetailsService securityUserDetailsService;
	private final JWTAuthenticationFilter authenticationFilter;
	
	   private static final String[] SECURED_URLs = 
		   {
			   "/books/**",
			   "/users/**"
			  
	   };

	    private static final String[] UN_SECURED_URLs = {
	            "/books/all",
	            "/users/add",
	            "/authenticate/**"
	           
	    };
	
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	            .authorizeHttpRequests()
	                .requestMatchers(UN_SECURED_URLs).permitAll()
	                .requestMatchers(SECURED_URLs).hasAuthority("ADMIN")
	                .anyRequest().authenticated()
	            .and()
	            .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()
	            .authenticationProvider(authenticationProvider())
	            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

	    @Bean
	    public AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(securityUserDetailsService);
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }


}
