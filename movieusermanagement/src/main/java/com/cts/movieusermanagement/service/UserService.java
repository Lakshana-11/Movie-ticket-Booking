package com.cts.movieusermanagement.service;

import java.sql.SQLException;
import java.util.List;

import com.cts.movieusermanagement.model.LoginRequest;
import com.cts.movieusermanagement.model.Notification;
import com.cts.movieusermanagement.model.Users;

public interface UserService {
	
	public Users addUser(Users user) throws SQLException;
	
	public String LoginUser(LoginRequest loginRequest) throws SQLException;
	
	public boolean validateToken(String token);
	
	public String forgotPassword(String userEmail);
	
	public String changePassword(String userEmail, String secretAnswer, String newPassword);
	
	public Users getUser(String userEmail);
	
	public List<Notification> getNotifications();

}
