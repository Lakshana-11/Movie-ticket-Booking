package com.cts.moviebookingapp.service;

import java.util.List;

import com.cts.moviebookingapp.exceptions.DuplicateMovieIdException;
import com.cts.moviebookingapp.model.Movie;

public interface MovieService {
	
	public Movie addMovie(Movie movie) throws DuplicateMovieIdException;
	
	public List<Movie> getAllMovies();
	
	public boolean deleteMovieById(int movieId);
	
	public Movie getMovieById(int movieId);
	
	public boolean updateMovie(Movie movie);
	
	public void updateTransactionList(Movie movie);
	
	public void addNotification(String message);

}
