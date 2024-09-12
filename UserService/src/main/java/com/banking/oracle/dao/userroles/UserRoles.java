package com.banking.oracle.dao.userroles;

public class UserRoles {
	int userId;	
	int roleId;
	
	public UserRoles() {}

	public UserRoles(int userId, int roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserRoles [userId=" + userId + ", roleId=" + roleId + "]";
	}
}
