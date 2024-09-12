package com.banking.oracle.dao.role;

import java.util.List;

public interface RoleDAO {
	List<Role> get();
	
	Role getByRoleId(Integer roleId);

	boolean update(Role role);

	boolean delete(Integer roleId);

	boolean create(Role role);
}
