package com.cts.movieusermanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.movieusermanagement.model.Users;
import com.cts.movieusermanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class UserAndAuthControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@InjectMocks
	private AuthController authController;

	@Autowired
	private MockMvc mockMvcUser;
	
	@Autowired
	private MockMvc mockMvcAuth;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvcAuth = MockMvcBuilders.standaloneSetup(this.authController).build();
		mockMvcUser = MockMvcBuilders.standaloneSetup(this.userController).build();
	}

	@SuppressWarnings("null")
	@Test
	public void testAddUserSuccess() throws Exception {
		Users user = new Users();
		user.setUserEmail("user@mail.com");
		when(userService.addUser(any())).thenReturn(user);

		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) authController
				.addUser(user);
		assertEquals("User Registered Successfully", response.getBody().get("Message"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		
		mockMvcAuth.perform(MockMvcRequestBuilders.post("/auth/v1/addUser").headers(headers)
				.content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isCreated());

	}
	
	@SuppressWarnings("null")
	@Test
	public void testAddUserFailure() throws Exception {
		Users user = new Users();
		user.setUserEmail("user@mail.com");
		when(userService.addUser(any())).thenThrow(new SQLException());

		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) authController
				.addUser(user);
		assertEquals("User Already Exists", response.getBody().get("Message"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		
		mockMvcAuth.perform(MockMvcRequestBuilders.post("/auth/v1/addUser").headers(headers)
				.content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	
	@SuppressWarnings("null")
	@Test
	void testValidateTokenSuccess() throws Exception {
		when(userService.validateToken("token")).thenReturn(true);
		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) userController
				.validateToken("token");
		assertEquals("token", response.getBody().get("Payload"));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token");
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		mockMvcUser.perform(MockMvcRequestBuilders.get("/user/v1/validateToken").headers(headers))
		.andExpect(status().isOk());
		
	}
	
	@SuppressWarnings("null")
	@Test
	void testValidateTokenFailure() throws Exception {
		when(userService.validateToken("token")).thenReturn(true);
		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) userController
				.validateToken("token1");
		assertNotEquals("token", response.getBody().get("Payload"));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token1");
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		mockMvcUser.perform(MockMvcRequestBuilders.get("/user/v1/validateToken").headers(headers))
		.andExpect(status().isBadRequest());
		
	}

}
