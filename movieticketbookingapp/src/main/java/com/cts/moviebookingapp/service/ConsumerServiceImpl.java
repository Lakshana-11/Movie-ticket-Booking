package com.cts.moviebookingapp.service;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.moviebookingapp.model.LoginRequest;
import com.cts.moviebookingapp.model.Users;
import com.cts.moviebookingapp.response.ResponseHandler;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest) {
		String baseUrl = "http://localhost:8091/auth/v1/login";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.exchange(baseUrl, HttpMethod.POST,
					new HttpEntity<LoginRequest>(loginRequest, headers), new ParameterizedTypeReference<Map<String,String>>() {});
		} catch(Exception exception) {
			return ResponseHandler.generateResponse("Invalid Credentials", HttpStatus.BAD_REQUEST, loginRequest.getUserEmail());
		}
	}

	@Override
	public boolean validateUser(String token) {
		String baseUrl = "http://localhost:8091/user/v1/validateUser";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map<String, String>> response = null;
		try {
			response =  restTemplate.exchange(baseUrl, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<Map<String,String>>() {});
		} catch(Exception exception) {
			return false;
		}
		return response.getStatusCode() == HttpStatus.OK;
	}

	@Override
	public boolean validateAdmin(String token) {
		String baseUrl = "http://localhost:8091/user/v1/validateAdmin";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map<String, String>> response = null;
		try {
			response =  restTemplate.exchange(baseUrl, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<Map<String,String>>() {});
		} catch(Exception exception) {
			return false;
		}
		return response.getStatusCode() == HttpStatus.OK;
	}

	@Override
	public boolean validateToken(String token) {
		String baseUrl = "http://localhost:8091/user/v1/validateToken";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map<String, String>> response = null;
		try {
			response =  restTemplate.exchange(baseUrl, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<Map<String,String>>() {});
		} catch(Exception exception) {
			return false;
		}
		return response.getStatusCode() == HttpStatus.OK;
	}

	@Override
	public ResponseEntity<?> register(Users user) {
		String baseUrl = "http://localhost:8091/auth/v1/addUser";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.exchange(baseUrl, HttpMethod.POST,
					new HttpEntity<Users>(user, headers), new ParameterizedTypeReference<Map<String,String>>() {});
		} catch(Exception exception) {
			return ResponseHandler.generateResponse("User Already Exists", HttpStatus.BAD_REQUEST, user.getUserEmail());
		}
	}

	@Override
	public ResponseEntity<?> forgotPassword(String userEmail) {
		String baseUrl = "http://localhost:8091/auth/v1/forgotPassword/"+userEmail;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.exchange(baseUrl, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<Map<String,String>>() {});
		} catch(Exception exception) {
			return ResponseHandler.generateResponse("Invalid User", HttpStatus.BAD_REQUEST, userEmail);
		}
	}

	@Override
	public ResponseEntity<?> changePassword(String userEmail, String secretAnswer, String newPassword) {
		String baseUrl = "http://localhost:8091/auth/v1/changePassword/"+userEmail;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		try {
			return restTemplate.exchange(baseUrl, HttpMethod.PUT,
					new HttpEntity<Map<String,String>>(Map.of("secretAnswer", secretAnswer, "newPassword", newPassword), headers), new ParameterizedTypeReference<Map<String,String>>() {});
		} catch(Exception exception) {
			return ResponseHandler.generateResponse("Invalid Secret Answer", HttpStatus.BAD_REQUEST, userEmail);
		}
	}

	@Override
	public ResponseEntity<?> getNotifications(String token) {
		String baseUrl = "http://localhost:8091/user/v1/getNotifications";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map<String, Object>> response = null;
		try {
			response =  restTemplate.exchange(baseUrl, HttpMethod.GET,
					new HttpEntity<>(headers), new ParameterizedTypeReference<Map<String,Object>>() {});
		} catch(Exception exception) {
			return ResponseHandler.generateResponse("Invalid", HttpStatus.BAD_REQUEST, exception.getMessage());
		}
		return response;
	}

}
