package com.cts.movieusermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.movieusermanagement.model.Notification;
import com.cts.movieusermanagement.model.Users;
import com.cts.movieusermanagement.repository.NotificationRepository;
import com.cts.movieusermanagement.repository.UserRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private NotificationRepository notificationRepository;
	
	@InjectMocks
	private UserServiceImpl userService;
	
	@Test
	public void testAddUserSuccess() throws SQLException {
		Users user = new Users();
		when(userRepository.findByUserEmail(anyString())).thenReturn(null);
		when(userRepository.saveAndFlush(any())).thenReturn(user);
		
		Users response = userService.addUser(user);
		assertEquals(response, user);
	}
	
	@Test
	public void testAddUserFailure() throws SQLException {
		Users user = new Users();
		user.setUserEmail("user@gmail.com");
		user.setSecurityQuestion("Question");
		when(userRepository.findByUserEmail(anyString())).thenReturn(user);
		assertThrows(SQLException.class,()-> userService.addUser(user));
	}
	
	@Test
	public void testSuccessfulForgotPassword() {
		Users user = new Users();
		user.setUserEmail("user@gmail.com");
		user.setSecurityQuestion("Question");
		when(userRepository.findByUserEmail("user@gmail.com")).thenReturn(user);
		String response = userService.forgotPassword("user@gmail.com");
		assertEquals("Question",response);
	}

	@Test
	public void testFailureForgotPassword() {
		Users user = new Users();
		user.setUserEmail("user@gmail.com");
		user.setSecurityQuestion("Question");
		when(userRepository.findByUserEmail("user@gmail.com")).thenReturn(null);
		String response = userService.forgotPassword("user@gmail.com");
		assertNotEquals("Question",response);
	}
	
	@Test
	public void testGetUserSuccess() {
		Users user = new Users();
		when(userRepository.findByUserEmail(any())).thenReturn(user);
		Users response = userService.getUser("");
		assertEquals(user, response);
	}
	
	@Test
	public void testGetUserFailure() {
		Users user = new Users();
		when(userRepository.findByUserEmail(any())).thenReturn(null);
		Users response = userService.getUser("");
		assertNotEquals(user, response);
	}
	
	@Test
	public void testGetNotifications() {
		List<Notification> notificationList = new ArrayList<>();
		when(notificationRepository.findAll()).thenReturn(notificationList);
		List<Notification> response = userService.getNotifications();
		assertEquals(response, notificationList);
	}

}
