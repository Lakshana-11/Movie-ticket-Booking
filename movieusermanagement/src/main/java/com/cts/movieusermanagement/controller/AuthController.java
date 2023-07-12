package com.cts.movieusermanagement.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.movieusermanagement.model.LoginRequest;
import com.cts.movieusermanagement.model.Users;
import com.cts.movieusermanagement.response.ResponseHandler;
import com.cts.movieusermanagement.service.UserService;

@RestController
@RequestMapping("auth/v1")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@RequestBody Users user) throws SQLException {
		try {
			userService.addUser(user);
		} catch (Exception exception) {
			return ResponseHandler.generateResponse("User Already Exists", HttpStatus.BAD_REQUEST, user.getUserEmail());
		}
		return ResponseHandler.generateResponse("User Registered Successfully", HttpStatus.CREATED, user.getUserEmail());
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws SQLException {
		String token = null;
		try {
			token = userService.LoginUser(loginRequest);
		} catch(Exception exception) {
			return ResponseHandler.generateResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, loginRequest.getUserEmail());
		}
		Users user = userService.getUser(loginRequest.getUserEmail());
		return ResponseHandler.generateResponse(token, HttpStatus.OK, user.getUserRole());
	}
	
	@GetMapping("/forgotPassword/{emailId}")
	public ResponseEntity<?> forgotPassword(@PathVariable("emailId") String userEmail) {
		String response = userService.forgotPassword(userEmail);
		if(response != null) {
			return ResponseHandler.generateResponse(response, HttpStatus.OK, userEmail);
		}
		return ResponseHandler.generateResponse("Invalid User", HttpStatus.BAD_REQUEST, userEmail);
	}
	
	@PutMapping("/changePassword/{emailId}")
	public ResponseEntity<?> changePassword(@PathVariable("emailId") String userEmail, @RequestBody Map<String, String> details) {
		String response = userService.changePassword(userEmail, details.get("secretAnswer"), details.get("newPassword"));
		if(response != null) {
			return ResponseHandler.generateResponse(response, HttpStatus.OK, userEmail);
		}
		return ResponseHandler.generateResponse("Invalid Secret Answer", HttpStatus.BAD_REQUEST, userEmail);
	}

}
