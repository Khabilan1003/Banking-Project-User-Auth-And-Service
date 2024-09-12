package com.banking.oracle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.oracle.service.UserService;
import com.banking.oracle.service.model.UserRequestModel;
import com.banking.oracle.service.model.UserResponseModel;
import com.banking.oracle.service.model.UserUpdateRequestModel;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public ResponseEntity<List<UserResponseModel>> getUsers(
			@RequestParam(name = "branch_code", required = false) Integer branchCode) {
		List<UserResponseModel> users = null;

		if (branchCode == null) {
			users = userService.getAllUsers();
		} else {
			users = userService.getUsersByBranchCode(branchCode);
		}

		if (users == null || users.size() == 0)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseModel> getUserById(@PathVariable Integer userId) {
		UserResponseModel user = userService.getUserById(userId);

		if (user == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Void> getUserById(@RequestBody UserRequestModel userRequest) {
		Integer userId = userService.create(userRequest);

		if (userId == -1)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/")
	public ResponseEntity<Void> updateUser(@RequestBody UserUpdateRequestModel userUpdateRequest) {
		userService.update(userUpdateRequest);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUserByUserId(@PathVariable Integer userId) {
		boolean isDeleted = userService.delete(userId);

		if (!isDeleted)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
