package com.cts.moviebookingapp.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviebookingapp.model.Movie;
import com.cts.moviebookingapp.model.Ticket;
import com.cts.moviebookingapp.response.ResponseHandler;
import com.cts.moviebookingapp.service.ConsumerService;
import com.cts.moviebookingapp.service.MovieService;
import com.cts.moviebookingapp.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private ConsumerService consumerService;
	
	@PostMapping("/book/{movieId}")
	public ResponseEntity<?> bookTicket(@RequestHeader("Authorization") String token, @PathVariable("movieId") int movieId, @RequestBody Ticket ticket) {
		if(!consumerService.validateUser(token)) {
			return ResponseHandler.generateResponse("Invalid Token", HttpStatus.UNAUTHORIZED, null);
		}
		Movie existingMovie = movieService.getMovieById(movieId);
		if(existingMovie != null && existingMovie.getSeatsAvailable() >= ticket.getSeatsBooked()) {
			existingMovie.setSeatsAvailable(existingMovie.getSeatsAvailable() - ticket.getSeatsBooked());
			ticket.setAvailableSeats(existingMovie.getSeatsAvailable());
			existingMovie.setSeatsBooked(existingMovie.getSeatsBooked() + ticket.getSeatsBooked());
			if(movieService.updateMovie(existingMovie) && ticketService.bookTicket(ticket)) {
				existingMovie.setTransactions(ticketService.getAllTranscations(movieId));
				movieService.updateTransactionList(existingMovie);
				return ResponseHandler.generateResponse("Tickets Booked successfully", HttpStatus.OK, ticket);
			}
		}
		return ResponseHandler.generateResponse("Ticket Cannot be Booked", HttpStatus.INTERNAL_SERVER_ERROR, null);
	}
	
	@GetMapping("/movie/{movieId}")
	public ResponseEntity<?> getAllTransactions(@RequestHeader("Authorization") String token, @PathVariable("movieId") int movieId) {
		if(!consumerService.validateAdmin(token)) {
			return ResponseHandler.generateResponse("Invalid Token", HttpStatus.UNAUTHORIZED, null);
		}
		List<Ticket> transactionList = ticketService.getAllTranscations(movieId);
		if(transactionList.isEmpty()) {
			return ResponseHandler.generateResponse("No Transactions Done", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
		return ResponseHandler.generateResponse("Transactions", HttpStatus.OK, transactionList);
	}

}
