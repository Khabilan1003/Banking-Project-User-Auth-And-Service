package com.banking.oracle.dao.userroles;

import java.util.List;

public interface UserRolesDAO {
	List<UserRoles> getByUserId(Integer userId);

	boolean create(List<UserRoles> userRoles);

	boolean deleteByUserId(Integer userId);
}
