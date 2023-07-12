package com.cts.movieusermanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
public class UserAndNotificationModelTest {
	Users usersObj = Mockito.mock(Users.class);

	Notification notificationObj = Mockito.mock(Notification.class);
	
	LoginRequest loginRequestObj = Mockito.mock(LoginRequest.class);

	@Test
	public void testUserEmailSuccess() {
		Users user = new Users();
		user.setUserEmail("admin@gmail.com");
		when(usersObj.getUserEmail()).thenReturn("admin@gmail.com");
		assertEquals(user.getUserEmail(), usersObj.getUserEmail());
	}

	@Test
	public void testUserEmailFailure() {
		Users user = new Users();
		user.setUserEmail("admin@gmail.com");
		when(usersObj.getUserEmail()).thenReturn(null);
		assertNotEquals(user.getUserEmail(), usersObj.getUserEmail());
	}

	@Test
	public void testNotificationSuccess() {
		Notification notification = new Notification();
		notification.setMessage("New notification");
		when(notificationObj.getMessage()).thenReturn("New notification");
		assertEquals(notification.getMessage(), notificationObj.getMessage());
	}

	@Test
	public void testNotificationFailure() {
		Notification notification = new Notification();
		notification.setMessage("New notification");
		when(notificationObj.getMessage()).thenReturn(null);
		assertNotEquals(notification.getMessage(), notificationObj.getMessage());
	}
	
	@Test
	public void testRequestUserEmailSuccess() {
		Users user = new Users();
		user.setUserEmail("admin@gmail.com");
		when(loginRequestObj.getUserEmail()).thenReturn("admin@gmail.com");
		assertEquals(user.getUserEmail(), loginRequestObj.getUserEmail());
	}

	@Test
	public void testRequestUserEmailFailure() {
		Users user = new Users();
		user.setUserEmail("admin@gmail.com");
		when(loginRequestObj.getUserEmail()).thenReturn(null);
		assertNotEquals(user.getUserEmail(), loginRequestObj.getUserEmail());
	}
	
}
