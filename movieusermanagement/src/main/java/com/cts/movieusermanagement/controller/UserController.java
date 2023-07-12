package com.cts.movieusermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.movieusermanagement.model.Notification;
import com.cts.movieusermanagement.response.ResponseHandler;
import com.cts.movieusermanagement.service.UserService;

@RestController
@RequestMapping("user/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/validateUser")
	public ResponseEntity<?> validateUser(@RequestHeader("Authorization") String token) {
		boolean isValid = userService.validateToken(token);
		if(!isValid) {
			return ResponseHandler.generateResponse("Token Not Valid", HttpStatus.BAD_REQUEST, token);
		}
		return ResponseHandler.generateResponse("Token Valid", HttpStatus.OK, token);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/validateAdmin")
	public ResponseEntity<?> validateAdmin(@RequestHeader("Authorization") String token) {
		return ResponseHandler.generateResponse("Token Valid", HttpStatus.OK, token);
	}
	
	@GetMapping("/validateToken")
	public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
		boolean isValid = userService.validateToken(token);
		if(!isValid) {
			return ResponseHandler.generateResponse("Token Not Valid", HttpStatus.BAD_REQUEST, token);
		}
		return ResponseHandler.generateResponse("Token Valid", HttpStatus.OK, token);
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/getNotifications")
	public ResponseEntity<?> getNotifications(@RequestHeader("Authorization") String token) {
		boolean isValid = userService.validateToken(token);
		if(!isValid) {
			return ResponseHandler.generateResponse("Token Not Valid", HttpStatus.BAD_REQUEST, token);
		}
		List<Notification> notifications = userService.getNotifications();
		return ResponseHandler.generateResponse("Notifications", HttpStatus.OK, notifications);
	}

}
