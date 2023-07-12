package com.cts.moviebookingapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.moviebookingapp.model.Movie;
import com.cts.moviebookingapp.service.ConsumerService;
import com.cts.moviebookingapp.service.MovieService;
import com.cts.moviebookingapp.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class MovieTicketControllerTest {

	@Mock
	private MovieService movieService;
	
	@Mock
	private TicketService ticketService;
	
	@Mock
	private ConsumerService consumerService;
	
	@InjectMocks
	private MovieController movieController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.movieController).build();
	}
	
	@SuppressWarnings("null")
	@Test
	public void testAddMovieSuccess() throws Exception {
		when(consumerService.validateAdmin("token")).thenReturn(true);
		Movie movie = new Movie();
		movie.setMovieId(2);
		movie.setMovieName("PS2");
		movie.setSeatsAvailable(100);
		movie.setSeatsBooked(0);
		movie.setTheaterName("Mall");
		movie.setTransactions(null);
		doNothing().when(movieService).addNotification("notify");
		when(movieService.addMovie(any())).thenReturn(movie);
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) movieController.addMovie("token", movie);
		assertEquals(movie, response.getBody().get("Payload"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token");
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/addMovie").headers(headers)
				.content(new ObjectMapper().writeValueAsString(movie))).andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	public void testAddMovieFailure() throws Exception {
		when(consumerService.validateAdmin("token")).thenReturn(false);
		Movie movie = new Movie();
		movie.setMovieId(2);
		movie.setMovieName("PS2");
		movie.setSeatsAvailable(100);
		movie.setSeatsBooked(0);
		movie.setTheaterName("Mall");
		movie.setTransactions(null);
		doNothing().when(movieService).addNotification("notify");
		when(movieService.addMovie(any())).thenReturn(null);
		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) movieController.addMovie("token", null);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token");
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/addMovie").headers(headers)
				.content(new ObjectMapper().writeValueAsString(movie))).andExpect(MockMvcResultMatchers.status().isUnauthorized());
		
	}
	
	@SuppressWarnings("null")
	@Test
	public void testGetAllMoviesSuccess() throws Exception {
		when(consumerService.validateToken("token")).thenReturn(true);
		Movie movie = new Movie();
		movie.setMovieId(1);
		movie.setMovieName("PS1");
		List<Movie> movieList = List.of(movie);
		when(movieService.getAllMovies()).thenReturn(movieList);
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) movieController.getAllMovies("token");
		assertEquals(response.getBody().get("Payload"), movieList);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token");
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		
		this.mockMvc
	      .perform(MockMvcRequestBuilders.get("/api/v1/getAllMovies").headers(headers))
	      .andExpect(status().isOk());
		
	}
	
	@SuppressWarnings("null")
	@Test
	public void testGetAllMovieFailure() throws Exception {
		when(consumerService.validateToken("token")).thenReturn(true);
		List<Movie> movieList = new ArrayList<>();
		when(movieService.getAllMovies()).thenReturn(movieList);
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>) movieController.getAllMovies("token");
		assertEquals(response.getBody().get("Payload"), null);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token");
		headers.set("Content-Type", "application/json");
		headers.set("Accept", "*/*");
		
		this.mockMvc
	      .perform(MockMvcRequestBuilders.get("/api/v1/getAllMovies").headers(headers))
	      .andExpect(status().isInternalServerError());
	}
	
}
