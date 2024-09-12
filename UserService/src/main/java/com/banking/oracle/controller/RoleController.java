package com.banking.oracle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.oracle.dao.role.Role;
import com.banking.oracle.dao.role.RoleDAO;

@RestController
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleDAO roleDAO;

	@GetMapping("/")
	public ResponseEntity<List<Role>> getAllRoles() {
		List<Role> roles = roleDAO.get();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}
}
