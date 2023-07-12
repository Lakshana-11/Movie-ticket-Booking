package com.cts.moviebookingapp.service;

import org.springframework.http.ResponseEntity;

import com.cts.moviebookingapp.model.LoginRequest;
import com.cts.moviebookingapp.model.Users;

public interface ConsumerService {
	
	public ResponseEntity<?> register(Users user);
	
	public ResponseEntity<?> login(LoginRequest loginRequest);
	
	public boolean validateUser(String token);
	
	public boolean validateAdmin(String token);
	
	public boolean validateToken(String token);
	
	public ResponseEntity<?> forgotPassword(String userEmail);
	
	public ResponseEntity<?> changePassword(String userEmail, String secretAnswer, String newPassword);
	
	public ResponseEntity<?> getNotifications(String token);

}
