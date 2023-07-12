package com.cts.movieusermanagement.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.movieusermanagement.model.LoginRequest;
import com.cts.movieusermanagement.model.Notification;
import com.cts.movieusermanagement.model.Users;
import com.cts.movieusermanagement.repository.NotificationRepository;
import com.cts.movieusermanagement.repository.UserRepository;
import com.cts.movieusermanagement.utility.JwtUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
    private CustomUserDetailsService userDetailsService;

	@Override
	public Users addUser(Users user) throws SQLException {
		if(userRepository.findByUserEmail(user.getUserEmail()) != null) {
			throw new SQLException("User Already Exists");
		}
		return userRepository.saveAndFlush(user);
	}

	@Override
	public String LoginUser(LoginRequest loginRequest) throws SQLException {
		Users user = userRepository.findByUserEmail(loginRequest.getUserEmail());
		if(user == null) {
			throw new SQLException("User Does not Exists");
		}
		 try{
	            authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            loginRequest.getUserEmail(),loginRequest.getPassword()
	                    )
	            );
	        } catch (Exception exception) {
	            throw new UsernameNotFoundException("Please Check the Credentials");
	        }

	        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserEmail());
	        if(userDetails.getPassword().equals(loginRequest.getPassword())) {
	            String token = jwtUtil.generateToken(userDetails);
	            return token;
	        }
	        else 
	            throw new UsernameNotFoundException("Invalid Credentials");
	}

	@Override
	public boolean validateToken(String token) {
		return jwtUtil.validateToken(token);
	}
	
	@KafkaListener(topics = "mtb", groupId = "mygroup")
	public void addNotification(String message) {
		Notification notification = new Notification();
		notification.setMessage(message);
		notificationRepository.saveAndFlush(notification);
	}

	@Override
	public String forgotPassword(String userEmail) {
		Users user = userRepository.findByUserEmail(userEmail);
		if(user != null) {
			return user.getSecurityQuestion();
		}
		return null;
	}

	@Override
	public String changePassword(String userEmail, String secretAnswer, String newPassword) {
		Users user = userRepository.findByUserEmail(userEmail);
		if(user!=null && user.getSecurityAnswer().equals(secretAnswer)) {
			user.setPassword(newPassword);
			userRepository.saveAndFlush(user);
			return "Password Reset Successful";
		}
		return null;
	}

	@Override
	public Users getUser(String userEmail) {
		return userRepository.findByUserEmail(userEmail);
	}

	@Override
	public List<Notification> getNotifications() {
		return notificationRepository.findAll();
	}

}
