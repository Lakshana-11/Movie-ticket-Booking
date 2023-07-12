package com.cts.moviebookingapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;



@AutoConfigureMockMvc
@SpringBootTest
public class MovieTicketModelTest {
	Movie movieObj = Mockito.mock(Movie.class);

	Ticket ticketObj = Mockito.mock(Ticket.class);
	
	Notification notificationObj = Mockito.mock(Notification.class);

	@Test
	public void testMovieNameSuccess() {
		Movie movie = new Movie();
		movie.setMovieName("PS1");
		when(movieObj.getMovieName()).thenReturn("PS1");
		assertEquals(movie.getMovieName(), movieObj.getMovieName());
	}

	@Test
	public void testMovieNameFailure() {
		Movie movie = new Movie();
		movie.setMovieName("PS1");
		when(movieObj.getMovieName()).thenReturn(null);
		assertNotEquals(movie.getMovieName(), movieObj.getMovieName());
	}

	@Test
	public void testTicketSeatsBookedSuccess() {
		Ticket ticket = new Ticket();
		ticket.setSeatsBooked(4);
		when(ticketObj.getSeatsBooked()).thenReturn(4);
		assertEquals(ticket.getSeatsBooked(), ticketObj.getSeatsBooked());
	}

	@Test
	public void testTicketSeatsBookedFailure() {
		Ticket ticket = new Ticket();
		ticket.setSeatsBooked(4);
		when(ticketObj.getSeatsBooked()).thenReturn(0);
		assertNotEquals(ticket.getSeatsBooked(), ticketObj.getSeatsBooked());
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
}
