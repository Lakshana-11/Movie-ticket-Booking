package com.cts.moviebookingapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviebookingapp.model.LoginRequest;
import com.cts.moviebookingapp.model.Users;
import com.cts.moviebookingapp.service.ConsumerService;

@CrossOrigin
@RestController
@RequestMapping("consumer/v1")
public class ConsumerController {
	
	@Autowired
	private ConsumerService consumerService;
	
	@PostMapping("/register")
	public ResponseEntity<?> performRegister(@RequestBody Users user) {
		return consumerService.register(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> performLogin(@RequestBody LoginRequest loginRequest) {
		return consumerService.login(loginRequest);
	}
	
	@GetMapping("/validateUser")
	public boolean performValidation(@RequestHeader("Authorization") String token) {
		return consumerService.validateUser(token);
	}
	
	@GetMapping("/validateAdmin")
	public boolean performAdminValidation(@RequestHeader("Authorization") String token) {
		return consumerService.validateAdmin(token);
	}
	
	@GetMapping("/validateToken")
	public boolean performTokenValidation(@RequestHeader("Authorization") String token) {
		return consumerService.validateToken(token);
	}
	
	@GetMapping("/forgotPassword/{emailId}")
	public ResponseEntity<?> forgotPassword(@PathVariable("emailId") String userEmail) {
		return consumerService.forgotPassword(userEmail);
	}
	
	@PutMapping("/changePassword/{emailId}")
	public ResponseEntity<?> changePassword(@PathVariable("emailId") String userEmail, @RequestBody Map<String, String> details) {
		return consumerService.changePassword(userEmail, details.get("secretAnswer"), details.get("newPassword"));
	}
	
	@GetMapping("/getNotifications")
	public ResponseEntity<?> getNotifications(@RequestHeader("Authorization") String token) {
		return consumerService.getNotifications(token);
	}

}
