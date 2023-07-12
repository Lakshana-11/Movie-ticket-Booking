package com.cts.moviebookingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.moviebookingapp.model.Movie;
import com.cts.moviebookingapp.model.Ticket;
import com.cts.moviebookingapp.repository.MovieRepository;
import com.cts.moviebookingapp.repository.TicketRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class MovieTicketServiceTest {
	
	@Mock
	private MovieRepository movieRepository;
	
	@Mock
	private TicketRepository ticketRepository;
	
	@InjectMocks
	private MovieServiceImpl movieService;
	
	@InjectMocks
	private TicketServiceImpl ticketService;
	
	@Test
	public void testGetMovieByIdSuccess() {
		Movie movie = new Movie();
		movie.setMovieId(1);
		movie.setMovieName("PS1");
		when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
		
		Movie response = movieService.getMovieById(1);
		assertEquals(response, movie);
	}
	
	@Test
	public void testGetMovieByIdFailure() {
		when(movieRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		Movie response = movieService.getMovieById(1);
		assertEquals(response, null);
	}
	
	@Test
	public void testBookTicket() {
		Ticket ticket = new Ticket();
		ticket.setMovie_id_fk(0);
		when(ticketRepository.saveAndFlush(any())).thenReturn(ticket);
		
		boolean response = ticketService.bookTicket(ticket);
		assertEquals(response, true);
	}
	
	@Test
	public void testGetAllTransactions() {
		List<Ticket> transactionList = new ArrayList<>();
		when(ticketRepository.getAllTransactions(anyInt())).thenReturn(transactionList);
		
		List<Ticket> response = ticketService.getAllTranscations(0);
		assertEquals(response, transactionList);
	}
	
	@Test
	public void testGetAllMovies() {
		List<Movie> movieList = new ArrayList<>();
		when(movieRepository.findAll()).thenReturn(movieList);
		
		List<Movie> response = movieRepository.findAll();
		assertEquals(response, movieList);
	}

}
