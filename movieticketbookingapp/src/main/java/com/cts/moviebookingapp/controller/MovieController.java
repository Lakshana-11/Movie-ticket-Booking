package com.cts.moviebookingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.moviebookingapp.exceptions.DuplicateMovieIdException;
import com.cts.moviebookingapp.model.Movie;
import com.cts.moviebookingapp.response.ResponseHandler;
import com.cts.moviebookingapp.service.ConsumerService;
import com.cts.moviebookingapp.service.MovieService;
import com.cts.moviebookingapp.service.TicketService;

@CrossOrigin
@RestController
@RequestMapping("api/v1")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ConsumerService consumerService;
	
	@PostMapping("/addMovie")
	public ResponseEntity<?> addMovie(@RequestHeader("Authorization") String token, @RequestBody Movie movie) throws DuplicateMovieIdException {
		if(!consumerService.validateAdmin(token)) {
			return ResponseHandler.generateResponse("Invalid Token", HttpStatus.UNAUTHORIZED, null);
		}
		if(movieService.addMovie(movie) != null) {
			movieService.addNotification(movie.getMovieName() + " is available right Now, Book Tickets Soon ...!!!");
			return ResponseHandler.generateResponse("Movie Added Successfully", HttpStatus.CREATED, movie);
		}
		else {
			return ResponseHandler.generateResponse("Movie is Null", HttpStatus.CONFLICT, movie);
		}
	}
	
	@GetMapping("/getAllMovies")
	public ResponseEntity<?> getAllMovies(@RequestHeader("Authorization") String token) {
		if(!consumerService.validateToken(token)) {
			return ResponseHandler.generateResponse("Invalid Token", HttpStatus.UNAUTHORIZED, null);
		}
		List<Movie> movieList = movieService.getAllMovies();
		if(!movieList.isEmpty()) {
			return ResponseHandler.generateResponse("Movie List", HttpStatus.OK, movieList);
		}
		else {
			return ResponseHandler.generateResponse("No Movies Available", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}
	
	@DeleteMapping("/deleteMovie/{movieId}")
	public ResponseEntity<?> deleteMovieById( @RequestHeader("Authorization") String token, @PathVariable("movieId") int movieId) {
		if(!consumerService.validateAdmin(token)) {
			return ResponseHandler.generateResponse("Invalid Token", HttpStatus.UNAUTHORIZED, null);
		}
		if(movieService.deleteMovieById(movieId) && ticketService.deleteTicket(movieId)) {
			return ResponseHandler.generateResponse("Movie Deleted Successfully", HttpStatus.OK, movieId);
		}
		else {
			return ResponseHandler.generateResponse("Movie Could not be deleted", HttpStatus.INTERNAL_SERVER_ERROR, movieId);
		}
	};

}
