package com.banking.oracle.service.model;

import java.util.List;

import com.banking.oracle.dao.branch.Branch;
import com.banking.oracle.dao.role.Role;

public class UserUpdateRequestModel {
	Integer userId;
	String firstname;
	String lastname;
	String username;
	String password;
	Branch branch;
	List<Role> roles;
	String phoneNumber;
	String email;

	public UserUpdateRequestModel() {
	}

	public UserUpdateRequestModel(Integer userId, String firstname, String lastname, String username, String password,
			Branch branch, List<Role> roles, String phoneNumber, String email) {
		super();
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.branch = branch;
		this.roles = roles;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserUpdateRequestModel [userId=" + userId + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", username=" + username + ", password=" + password + ", branch=" + branch + ", roles=" + roles
				+ ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
	}

	
}
