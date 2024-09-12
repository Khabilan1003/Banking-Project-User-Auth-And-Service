package com.banking.oracle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.banking.oracle.dao.User;
import com.banking.oracle.dao.UserDAO;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.get(username);
		
		if(user == null)
			throw new UsernameNotFoundException("user not found with name :" + username);
		
		return new CustomUserDetails(user);
	}
}
