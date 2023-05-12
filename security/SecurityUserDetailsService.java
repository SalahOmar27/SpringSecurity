package com.salah.springSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.salah.springSecurity.repository.UserRepository;

@Component
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).map(SecurityUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("No user found"));
	}
	
//	 @Override
//	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	        Optional<User> user = userRepository.findByEmail(username);
//	        if (user.isEmpty()) {
//	            throw new UsernameNotFoundException("User not found with username: " + username);
//	        }
//
//	        return (UserDetails) User.builder()
//	        		.email(user.get().getEmail())
//	        		.password(user.get().getPassword())
//	        		.role(user.get().getRole())
//	        		.build();
//	    }

}
