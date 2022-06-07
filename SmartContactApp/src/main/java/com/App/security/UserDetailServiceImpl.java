package com.App.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.App.Dao.UserRepository;
import com.App.model.User;

public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 User user = userRepository.getUserByUserName(username);
		
		 if (user==null) {
			throw new UsernameNotFoundException("username is null");
		}
		 
		 CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return customUserDetails;
	}

}
