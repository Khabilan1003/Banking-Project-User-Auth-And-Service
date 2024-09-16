package com.banking.oracle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking.oracle.controller.model.response.TokenResponseModel;
import com.banking.oracle.dao.User;
import com.banking.oracle.dao.UserDAO;
import com.banking.oracle.dto.AuthRequest;
import com.banking.oracle.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/token")
	public ResponseEntity<TokenResponseModel> getToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		User user = userDAO.get(authRequest.getUsername());

		if (authenticate.isAuthenticated()) {
			String token = service.generateToken(authRequest.getUsername());
			return new ResponseEntity<>(new TokenResponseModel(token, user.getUserId()), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/validate")
	public ResponseEntity<Void> validateToken(@RequestParam("token") String token) {
		service.validateToken(token);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
