package com.banking.oracle.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.banking.oracle.dao.User;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	public CustomUserDetails(User userCredential) {
		this.username = userCredential.getUsername();
		this.password = userCredential.getPassword();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
}
