package com.banking.oracle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking.oracle.dao.branch.Branch;
import com.banking.oracle.dao.branch.BranchDAO;
import com.banking.oracle.dao.role.Role;
import com.banking.oracle.dao.role.RoleDAO;
import com.banking.oracle.dao.user.User;
import com.banking.oracle.dao.user.UserDAO;
import com.banking.oracle.dao.userroles.UserRoles;
import com.banking.oracle.dao.userroles.UserRolesDAO;
import com.banking.oracle.service.model.UserRequestModel;
import com.banking.oracle.service.model.UserResponseModel;
import com.banking.oracle.service.model.UserUpdateRequestModel;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private BranchDAO branchDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private UserRolesDAO userRolesDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private UserResponseModel generateUserResponseByUser(User user) {
		if (user == null)
			return null;

		Branch branch = branchDAO.getByBranchCode(user.getBranchCode());
		List<UserRoles> userRoles = userRolesDAO.getByUserId(user.getUserId());
		List<Role> roles = new ArrayList<>();
		for (UserRoles userRole : userRoles) {
			roles.add(roleDAO.getByRoleId(userRole.getRoleId()));
		}

		UserResponseModel userResponse = new UserResponseModel();
		userResponse.setUserId(user.getUserId());
		userResponse.setFirstname(user.getFirstname());
		userResponse.setLastname(user.getLastname());
		userResponse.setUsername(user.getUsername());
		userResponse.setBranch(branch);
		userResponse.setRoles(roles);
		userResponse.setEmail(user.getEmail());
		userResponse.setPhoneNumber(user.getPhoneNumber());

		return userResponse;
	}

	public UserResponseModel getUserById(Integer userId) {
		User user = userDAO.get(userId);
		return generateUserResponseByUser(user);
	}

	public List<UserResponseModel> getUsersByBranchCode(Integer branchCode) {
		List<User> users = userDAO.getByBranchCode(branchCode);
		List<UserResponseModel> userResponses = new ArrayList<>();

		for (User user : users) {
			userResponses.add(generateUserResponseByUser(user));
		}

		return userResponses;
	}

	public List<UserResponseModel> getAllUsers() {
		List<User> users = userDAO.getAll();
		List<UserResponseModel> userResponses = new ArrayList<>();

		for (User user : users) {
			userResponses.add(generateUserResponseByUser(user));
		}

		return userResponses;
	}

	public Integer create(UserRequestModel userRequest) {
		User user = new User(null, userRequest.getFirstname(), userRequest.getLastname(), userRequest.getUsername(),
				passwordEncoder.encode(userRequest.getPassword()), userRequest.getBranch().getBranchCode(),
				userRequest.getPhoneNumber(), userRequest.getEmail());

		Integer userId = userDAO.create(user);

		if (userId == -1)
			return -1;

		List<UserRoles> userRoles = new ArrayList<>();
		for (Role role : userRequest.getRoles()) {
			userRoles.add(new UserRoles(userId, role.getRoleId()));
		}

		userRolesDAO.create(userRoles);

		return userId;
	}

	public void update(UserUpdateRequestModel userUpdateRequest) {
		User user = new User(userUpdateRequest.getUserId(), userUpdateRequest.getFirstname(),
				userUpdateRequest.getLastname(), userUpdateRequest.getUsername(),
				userUpdateRequest.getPassword() != null ? passwordEncoder.encode(userUpdateRequest.getPassword())
						: null,
				userUpdateRequest.getBranch() != null ? userUpdateRequest.getBranch().getBranchCode() : null,
				userUpdateRequest.getPhoneNumber(), userUpdateRequest.getEmail());

		userDAO.update(user);

		if (userUpdateRequest.getRoles() != null) {
			userRolesDAO.deleteByUserId(userUpdateRequest.getUserId());
			List<UserRoles> userRoles = new ArrayList<>();
			for (Role role : userUpdateRequest.getRoles()) {
				userRoles.add(new UserRoles(userUpdateRequest.getUserId(), role.getRoleId()));
			}
			userRolesDAO.create(userRoles);
		}
	}

	public boolean delete(int userId) {
		userRolesDAO.deleteByUserId(userId);
		boolean isUserDeleted = userDAO.deleteById(userId);

		if (isUserDeleted)
			return true;

		return false;
	}
}
